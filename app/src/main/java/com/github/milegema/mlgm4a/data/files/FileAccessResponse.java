package com.github.milegema.mlgm4a.data.files;

import java.util.List;

public class FileAccessResponse {

    private FileAccessContext context;
    private FileAccessRequest request;
    private List<FileAccessBlock> blocks;

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
}
