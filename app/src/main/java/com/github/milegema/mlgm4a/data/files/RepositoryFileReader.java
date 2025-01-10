package com.github.milegema.mlgm4a.data.files;

import com.github.milegema.mlgm4a.data.repositories.RepositoryContext;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

public final class RepositoryFileReader {

    private RepositoryContext context;
    private RepositoryFileCallback callback;

    public RepositoryFileReader(RepositoryContext ctx) {
        this.context = ctx;
    }

    // setter & getter

    public RepositoryContext getContext() {
        return context;
    }

    public void setContext(RepositoryContext context) {
        this.context = context;
    }

    public RepositoryFileCallback getCallback() {
        return callback;
    }

    public void setCallback(RepositoryFileCallback callback) {
        this.callback = callback;
    }

    // reader

    public String readText(Path file) throws IOException {
        ByteSlice src = this.inner_read_bin(file);
        return new String(src.getData(), src.getOffset(), src.getLength(), StandardCharsets.UTF_8);
    }

    public ByteSlice readBinary(Path file) throws IOException {
        return this.inner_read_bin(file);
    }

    private ByteSlice inner_read_bin(Path file) throws IOException {

        FileAccessContext ctx = new FileAccessContext();
        FileAccessRequest req = new FileAccessRequest();

        ctx.setChain(context.getRfc().getChain());
        ctx.setFile(file);
        ctx.setKeyPair(context.getKeyPair());
        ctx.setSecretKey(context.getSecretKey());
        ctx.setDataStateListener(this.callback);

        // request
        req.setAction(FileAccessAction.READ_ALL);
        req.setOptions(FileAccessOptions.forRegularBlock());
        req.setContext(ctx);

        // response
        FileAccessResponse resp = ctx.getChain().access(req);
        List<FileAccessBlock> block_list = resp.getBlocks();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        for (FileAccessBlock block : block_list) {
            if (block == null) {
                continue;
            }
            ByteSlice src = block.getContentLayer().getBody();
            if (src == null) {
                continue;
            }
            buffer.write(src.getData(), src.getOffset(), src.getLength());
        }
        return new ByteSlice(buffer.toByteArray());
    }
}
