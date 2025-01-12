package com.github.milegema.mlgm4a.data.repositories.objects;

import com.github.milegema.mlgm4a.data.files.FileAccessAction;
import com.github.milegema.mlgm4a.data.files.FileAccessBlock;
import com.github.milegema.mlgm4a.data.files.FileAccessDataEncoding;
import com.github.milegema.mlgm4a.data.files.FileAccessDataState;
import com.github.milegema.mlgm4a.data.files.FileAccessException;
import com.github.milegema.mlgm4a.data.files.FileAccessLayerClass;
import com.github.milegema.mlgm4a.data.files.FileAccessLayerPack;
import com.github.milegema.mlgm4a.data.files.RepositoryFileCallback;
import com.github.milegema.mlgm4a.data.files.RepositoryFileWriter;
import com.github.milegema.mlgm4a.data.files.layers.SumLayer;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.data.repositories.RepositoryContext;
import com.github.milegema.mlgm4a.data.repositories.RepositoryException;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockID;
import com.github.milegema.mlgm4a.utils.ByteSlice;
import com.github.milegema.mlgm4a.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

final class ObjectMaker {

    private Path tmp;
    private RepositoryContext repoContext;

    ObjectMaker() {
    }


    private static class MyImportObjectHandler extends RepositoryFileCallback {

        EncodedObject encodedObject; // input
        BlockID result; // output

        public void init(EncodedObject eo) {
            eo.verify();
            this.encodedObject = eo;
        }

        @Override
        public void onEncodeLayerBegin(FileAccessDataState state, FileAccessDataEncoding encoding) {
            FileAccessLayerClass lc = encoding.getLayerClass();
            if (!FileAccessLayerClass.SUM.equals(lc)) {
                return;
            }

            BlockID id = this.encodedObject.getId();
            FileAccessLayerPack pack = new FileAccessLayerPack();
            FileAccessBlock block = encoding.getBlock();
            SumLayer sum_layer = block.getSumLayer();
            ByteSlice body = this.encodedObject.getEncoded();
            PropertyTable head = PropertyTable.Factory.create();

            block.setId(id);
            encoding.setPack(pack);
            pack.setHead(head);
            pack.setBody(body);
            sum_layer.setEntity(body);
            sum_layer.setId(id);

            super.onEncodeLayerBegin(state, encoding);
        }

        @Override
        public void onEncodeBlockEnd(FileAccessDataState state, FileAccessDataEncoding encoding) {
            FileAccessBlock block = encoding.getBlock();
            this.result = block.getId();
            super.onEncodeBlockEnd(state, encoding);
        }
    }

    private static class MyCreateObjectHandler extends RepositoryFileCallback {
        BlockID result;

        public void init(ObjectBuilder builder) {
            // NOP
        }

        @Override
        public void onEncodeBlockEnd(FileAccessDataState state, FileAccessDataEncoding encoding) {
            if (result == null) {
                this.result = encoding.getBlock().getSumLayer().getId();
            } else {
                throw new RepositoryException("a object file can contain only one block");
            }
            // super.onEncodeBlockEnd(state, encoding);
        }
    }

    public void init(RepositoryContext context) {
        this.tmp = context.getRfc().nextTemporaryFile();
        this.repoContext = context;
    }

    public BlockID doImport(EncodedObject eo) throws IOException {

        RepositoryFileWriter writer = new RepositoryFileWriter(this.repoContext);
        MyImportObjectHandler handler = new MyImportObjectHandler();

        handler.init(eo);

        writer.setAction(FileAccessAction.CREATE);
        writer.setBlockType(null);
        writer.setCallback(handler);
        writer.setContentType(null);
        writer.setContext(this.repoContext);
        writer.setHead(null);

        writer.write("", this.tmp);
        return handler.result;
    }

    public BlockID doCreate(ObjectBuilder builder) throws IOException {

        MyCreateObjectHandler handler = new MyCreateObjectHandler();
        RepositoryFileWriter writer = new RepositoryFileWriter(this.repoContext);

        handler.init(builder);

        writer.setAction(FileAccessAction.CREATE);
        writer.setBlockType(builder.getBlockType());
        writer.setCallback(handler);
        writer.setContentType(builder.getContentType());
        writer.setContext(this.repoContext);
        writer.setHead(builder.getHead());

        writer.write(builder.getBody(), this.tmp);
        return handler.result;
    }

    // 操作成功, 保存数据
    public void complete(Path dst) throws IOException {
        if (tmp == null || dst == null) {
            return;
        }
        if (Files.exists(dst)) {
            return;
        }
        FileUtils.mkdirsForFile(dst);
        Files.move(tmp, dst);
    }

    // 操作结束, 清理临时文件
    public void finish() {
        if (tmp != null) {
            try {
                Files.deleteIfExists(tmp);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
