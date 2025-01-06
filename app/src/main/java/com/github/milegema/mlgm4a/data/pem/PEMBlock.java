package com.github.milegema.mlgm4a.data.pem;

import com.github.milegema.mlgm4a.utils.ByteSlice;

public class PEMBlock {

    private String type;
    private ByteSlice data;

    public PEMBlock() {
        this.type = "NONE";
        this.data = new ByteSlice();
    }

    private static String normalizeType(String t) {
        if (t == null) {
            t = "NONE";
        }
        return t.trim().toUpperCase();
    }

    private static ByteSlice normalizeData(ByteSlice src) {
        if (src == null) {
            src = new ByteSlice();
        }
        return src;
    }


    public ByteSlice getData() {
        return data;
    }

    public void setData(ByteSlice _data) {
        this.data = normalizeData(_data);
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = normalizeType(type);
    }
}
