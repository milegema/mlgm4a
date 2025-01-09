package com.github.milegema.mlgm4a.data.files.layers;

import com.github.milegema.mlgm4a.data.files.FileAccessLayer;
import com.github.milegema.mlgm4a.utils.ByteSlice;

public class PemLayer extends FileAccessLayer {

    private String type; // the PEM-block-type
    private ByteSlice data;

    public PemLayer() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ByteSlice getData() {
        return data;
    }

    public void setData(ByteSlice data) {
        this.data = data;
    }
}
