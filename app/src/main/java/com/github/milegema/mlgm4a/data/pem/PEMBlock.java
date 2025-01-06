package com.github.milegema.mlgm4a.data.pem;

public class PEMBlock {

    private String type;
    private byte[] data;

    public PEMBlock() {
    }

    public byte[] getData() {
        byte[] res = this.data;
        if (res == null) {
            res = new byte[0];
        }
        return res;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getType() {
        String res = this.type;
        if (res == null) {
            res = "no type";
        }
        return res.toUpperCase();
    }

    public void setType(String type) {
        this.type = type;
    }
}
