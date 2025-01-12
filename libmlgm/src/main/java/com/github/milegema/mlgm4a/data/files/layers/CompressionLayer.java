package com.github.milegema.mlgm4a.data.files.layers;

import com.github.milegema.mlgm4a.data.files.FileAccessLayer;

public class CompressionLayer extends FileAccessLayer {

    private int inflatedSize; // bigger
    private int deflatedSize; // smaller


    public CompressionLayer() {
    }

    public int getInflatedSize() {
        return inflatedSize;
    }

    public void setInflatedSize(int inflatedSize) {
        this.inflatedSize = inflatedSize;
    }

    public int getDeflatedSize() {
        return deflatedSize;
    }

    public void setDeflatedSize(int deflatedSize) {
        this.deflatedSize = deflatedSize;
    }
}
