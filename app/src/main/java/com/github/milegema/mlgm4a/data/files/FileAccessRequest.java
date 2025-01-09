package com.github.milegema.mlgm4a.data.files;

import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.util.List;

public class FileAccessRequest {

    private FileAccessAction action;
    private FileAccessContext context;
    private List<FileAccessBlock> blocks;
    private FileAccessOptions options;
    private ByteSlice raw; // 直接从文件读写的原始数据
    private FileAccessKeyRef accessKey; // 用于加密/解密的密钥 : 它可能是 public|private|secret

    public FileAccessRequest() {
    }

    public FileAccessKeyRef getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(FileAccessKeyRef accessKey) {
        this.accessKey = accessKey;
    }

    public FileAccessAction getAction() {
        return action;
    }

    public void setAction(FileAccessAction action) {
        this.action = action;
    }

    public FileAccessOptions getOptions() {
        return options;
    }

    public void setOptions(FileAccessOptions options) {
        this.options = options;
    }

    public FileAccessContext getContext() {
        return context;
    }

    public void setContext(FileAccessContext context) {
        this.context = context;
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

    // 判断:是否为'读'请求
    public static boolean isRead(FileAccessRequest req) {
        if (req == null) {
            return false;
        }
        return FileAccessAction.isRead(req.getAction());
    }

    // 判断:是否为'写'请求
    public static boolean isWrite(FileAccessRequest req) {
        if (req == null) {
            return false;
        }
        return FileAccessAction.isWrite(req.getAction());
    }
}
