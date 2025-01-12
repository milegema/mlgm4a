package com.github.milegema.mlgm4a.data.repositories.objects;

import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockID;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockType;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.io.IOException;

public final class ObjectBuilder {

    private final Objects manager;

    private PropertyTable head;
    private ByteSlice body;

    private BlockType blockType;
    private String contentType;

    public ObjectBuilder(Objects om) {
        this.manager = om;
    }

    public ObjectHolder create() throws IOException {
        return this.manager.createObject(this);
    }

    public Objects getManager() {
        return this.manager;
    }

    public PropertyTable getHead() {
        return head;
    }

    public void setHead(PropertyTable head) {
        this.head = head;
    }

    public ByteSlice getBody() {
        return body;
    }

    public void setBody(ByteSlice body) {
        this.body = body;
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
}
