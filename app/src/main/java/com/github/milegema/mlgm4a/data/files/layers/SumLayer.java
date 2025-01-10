package com.github.milegema.mlgm4a.data.files.layers;

import com.github.milegema.mlgm4a.data.files.FileAccessLayer;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockID;
import com.github.milegema.mlgm4a.utils.ByteSlice;

public class SumLayer extends FileAccessLayer {

    private BlockID id;

    // private ByteSlice entity;

    public SumLayer() {
    }

    public BlockID getId() {
        return id;
    }

    public void setId(BlockID id) {
        this.id = id;
    }

    public ByteSlice getEntity() {
        return this.getPack().getBody();
    }

    public void setEntity(ByteSlice entity) {
        this.getPack().setBody(entity);
    }
}
