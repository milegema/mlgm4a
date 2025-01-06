package com.github.milegema.mlgm4a.data.files.layers;

import com.github.milegema.mlgm4a.data.files.FileAccessBlock;
import com.github.milegema.mlgm4a.data.files.FileAccessLayer;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.io.IOException;

public final class LayerGlue {

    private FileAccessBlock block;
    private FileAccessLayer currentLayer;
    private FileAccessLayer nextLayer;

    public LayerGlue(FileAccessBlock _block) {
        this.block = _block;
    }

    public FileAccessBlock getBlock() {
        return block;
    }

    public void setBlock(FileAccessBlock block) {
        this.block = block;
    }

    public FileAccessLayer getCurrentLayer() {
        return currentLayer;
    }

    public void setCurrentLayer(FileAccessLayer currentLayer) {
        this.currentLayer = currentLayer;
    }

    public FileAccessLayer getNextLayer() {
        return nextLayer;
    }

    public void setNextLayer(FileAccessLayer nextLayer) {
        this.nextLayer = nextLayer;
    }

    public void encode() throws IOException {
        this.currentLayer.encode();
        ByteSlice buffer = this.currentLayer.getEncoded();
        this.nextLayer.setBody(new ByteSlice(buffer));
    }

    public void decode() throws IOException {
        ByteSlice buffer = this.nextLayer.getBody();
        this.currentLayer.setEncoded(new ByteSlice(buffer));
        this.currentLayer.decode();
    }
}
