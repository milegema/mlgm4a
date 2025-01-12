package com.github.milegema.mlgm4a.data.repositories.refs;

import com.github.milegema.mlgm4a.data.files.FileAccessAction;
import com.github.milegema.mlgm4a.data.files.RepositoryFileReader;
import com.github.milegema.mlgm4a.data.files.RepositoryFileWriter;
import com.github.milegema.mlgm4a.data.repositories.RepositoryContext;
import com.github.milegema.mlgm4a.data.repositories.RepositoryException;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockID;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockType;
import com.github.milegema.mlgm4a.network.web.ContentTypes;
import com.github.milegema.mlgm4a.utils.Hex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DefaultRefManager implements Refs {

    private final RepositoryContext mContext;

    public DefaultRefManager(RepositoryContext ctx) {
        this.mContext = ctx;
    }

    private static class MyRefBuilder {
        Path file;
        RefName name;
        RepositoryContext context;

        Ref create() {
            final String prefix = "refs/";
            final String path = String.valueOf(this.name);
            Path dir = this.context.getLayout().getRefs().getParent();
            if (!path.startsWith(prefix)) {
                throw new RepositoryException("bad ref name: " + name);
            }
            this.file = dir.resolve(path);
            return new MyRef(this);
        }
    }


    private static class MyRef implements Ref {

        private final Path mFile;
        private final RefName mName;
        private final RepositoryContext mContext;

        MyRef(MyRefBuilder b) {
            this.mContext = b.context;
            this.mName = b.name;
            this.mFile = b.file;
        }

        @Override
        public RefName name() {
            return this.mName;
        }

        @Override
        public boolean exists() {
            return Files.isRegularFile(this.mFile);
        }

        @Override
        public BlockID read() throws IOException {
            RepositoryFileReader reader = new RepositoryFileReader(this.mContext);
            String text = reader.readText(this.mFile);
            return new BlockID(text.trim());
        }

        @Override
        public void write(BlockID value) throws IOException {
            String text = Hex.stringify(value.toByteArray());
            RepositoryFileWriter writer = new RepositoryFileWriter(this.mContext);
            Path file = this.mFile;
            if (Files.exists(file)) {
                writer.setAction(FileAccessAction.REWRITE);
            } else {
                writer.setAction(FileAccessAction.CREATE);
            }
            writer.setBlockType(BlockType.Ref);
            writer.setContentType(ContentTypes.object_ref);
            writer.write(text, file);
        }
    }

    @Override
    public Ref get(RefName name) {
        MyRefBuilder builder = new MyRefBuilder();
        builder.context = this.mContext;
        builder.name = name;
        return builder.create();
    }
}
