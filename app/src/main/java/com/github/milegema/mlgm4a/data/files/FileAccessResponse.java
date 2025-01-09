package com.github.milegema.mlgm4a.data.files;

import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.util.List;

public class FileAccessResponse {

    private FileAccessContext context;
    private FileAccessRequest request;
    private List<FileAccessBlock> blocks;
    private ByteSlice raw; // 直接从文件读写的原始数据

    public FileAccessResponse() {
    }

    public FileAccessContext getContext() {
        return context;
    }

    public void setContext(FileAccessContext context) {
        this.context = context;
    }

    public FileAccessRequest getRequest() {
        return request;
    }

    public void setRequest(FileAccessRequest request) {
        this.request = request;
    }

    public List<FileAccessBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<FileAccessBlock> blocks) {
        this.blocks = blocks;
    }

    public ByteSlice getRaw() {
        return raw;
    }

    public void setRaw(ByteSlice raw) {
        this.raw = raw;
    }
}
