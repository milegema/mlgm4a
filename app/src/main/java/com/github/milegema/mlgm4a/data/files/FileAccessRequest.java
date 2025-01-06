package com.github.milegema.mlgm4a.data.files;

import java.util.List;

public class FileAccessRequest {

    private FileAccessAction action;
    private FileAccessContext context;
    private List<FileAccessBlock> blocks;

    public FileAccessRequest() {
    }

    public FileAccessAction getAction() {
        return action;
    }

    public void setAction(FileAccessAction action) {
        this.action = action;
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
