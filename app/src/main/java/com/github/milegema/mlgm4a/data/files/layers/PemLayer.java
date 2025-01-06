package com.github.milegema.mlgm4a.data.files.layers;

import com.github.milegema.mlgm4a.data.files.FileAccessLayer;

public class PemLayer extends FileAccessLayer {

    private String type; // the PEM-block-type

    public PemLayer() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
