package com.github.milegema.mlgm4a.data.repositories.objects;

import com.github.milegema.mlgm4a.data.repositories.blocks.BlockID;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockType;

public class ObjectMeta {

    private BlockID id;
    private BlockType blockType;
    private String contentType;
    private int contentLength;
    private long createdAt;

    public ObjectMeta() {
    }

    public ObjectMeta(ObjectMeta src) {
        if (src == null) {
            return;
        }
        this.id = src.id;
        this.blockType = src.blockType;
        this.contentType = src.contentType;
        this.contentLength = src.contentLength;
        this.createdAt = src.createdAt;
    }

    public BlockID getId() {
        return id;
    }

    public void setId(BlockID id) {
        this.id = id;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
