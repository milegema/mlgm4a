package com.github.milegema.mlgm4a.data.files;

import com.github.milegema.mlgm4a.utils.ByteSlice;

public class FileAccessDataEncoding {

    private FileAccessContext context;
    private FileAccessRequest request;
    private FileAccessResponse response;
    private FileAccessBlock block;
    private FileAccessLayerPack pack;
    private ByteSlice encoded;

    public FileAccessDataEncoding() {
    }

    public FileAccessContext getContext() {
        return context;
    }

    public void setContext(FileAccessContext context) {
        this.context = context;
    }

    public FileAccessLayerPack getPack() {
        return pack;
    }

    public void setPack(FileAccessLayerPack pack) {
        this.pack = pack;
    }

    public ByteSlice getEncoded() {
        return encoded;
    }

    public void setEncoded(ByteSlice encoded) {
        this.encoded = encoded;
    }

    public FileAccessBlock getBlock() {
        return block;
    }

    public void setBlock(FileAccessBlock block) {
        this.block = block;
    }

    public FileAccessRequest getRequest() {
        return request;
    }

    public void setRequest(FileAccessRequest request) {
        this.request = request;
    }

    public FileAccessResponse getResponse() {
        return response;
    }

    public void setResponse(FileAccessResponse response) {
        this.response = response;
    }
}
