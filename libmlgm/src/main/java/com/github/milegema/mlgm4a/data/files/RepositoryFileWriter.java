package com.github.milegema.mlgm4a.data.files;

import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.data.repositories.RepositoryContext;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockType;
import com.github.milegema.mlgm4a.network.web.ContentTypes;
import com.github.milegema.mlgm4a.security.CipherMode;
import com.github.milegema.mlgm4a.security.CipherPadding;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;

public final class RepositoryFileWriter {

    private RepositoryContext context;
    private RepositoryFileCallback callback;

    private BlockType blockType;
    private String contentType;
    private FileAccessAction action;
    private PropertyTable head;

    public RepositoryFileWriter(RepositoryContext ctx) {
        this.context = ctx;
    }

    // setter & getter

    public RepositoryContext getContext() {
        return context;
    }

    public void setContext(RepositoryContext context) {
        this.context = context;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public FileAccessAction getAction() {
        return action;
    }

    public void setAction(FileAccessAction action) {
        this.action = action;
    }

    public PropertyTable getHead() {
        return head;
    }

    public void setHead(PropertyTable head) {
        this.head = head;
    }

    public RepositoryFileCallback getCallback() {
        return callback;
    }

    public void setCallback(RepositoryFileCallback callback) {
        this.callback = callback;
    }

    // writer

    public void write(String text, Path dst_file) throws IOException {
        if (text == null) {
            text = "";
        }
        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        this.inner_write_bin(new ByteSlice(data), dst_file);
    }

    public void write(byte[] data, Path dst_file) throws IOException {
        this.inner_write_bin(new ByteSlice(data), dst_file);
    }

    public void write(ByteSlice data, Path dst_file) throws IOException {
        this.inner_write_bin(data, dst_file);
    }

    // inner

    private void inner_prepare_default_params() {

        if (action == null) {
            action = FileAccessAction.REWRITE;
        }

        if (head == null) {
            head = PropertyTable.Factory.create();
        }

        if (blockType == null) {
            blockType = BlockType.BLOB;
        }

        if (contentType == null) {
            contentType = ContentTypes.application_octet_stream;
        }
    }

    private void inner_write_bin(ByteSlice data, Path dst) throws IOException {

        if (data == null || dst == null) {
            throw new FileAccessException("param is null @" + this);
        }

        this.inner_prepare_default_params();

        FileAccessContext ctx = new FileAccessContext();
        FileAccessRequest req = new FileAccessRequest();
        SecretKey sk = context.getSecretKey();
        List<FileAccessBlock> block_list = new ArrayList<>();
        FileAccessBlock block = new FileAccessBlock();

        // context
        ctx.setChain(context.getRfc().getChain());
        ctx.setFile(dst);
        ctx.setKeyPair(context.getKeyPair());
        ctx.setSecretKey(sk);
        ctx.setAccessKey(new FileAccessKeyProxy(sk));
        ctx.setDataStateListener(this.callback);

        // block
        block.getContentLayer().setBody(data);
        block.getContentLayer().setHead(this.head);
        block.getContentLayer().setContentType(this.contentType);

        block.getEncryptionLayer().setAccessKey(null);
        block.getEncryptionLayer().setMode(CipherMode.CBC);
        block.getEncryptionLayer().setPadding(CipherPadding.PKCS7Padding);

        block.setType(this.blockType);

        block_list.add(block);

        // request
        req.setAction(action);
        req.setOptions(FileAccessOptions.forRegularBlock());
        req.setContext(ctx);
        req.setBlocks(block_list);

        // response
        ctx.getChain().access(req);
    }
}
