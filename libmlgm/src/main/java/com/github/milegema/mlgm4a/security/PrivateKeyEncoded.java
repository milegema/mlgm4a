package com.github.milegema.mlgm4a.security;

import com.github.milegema.mlgm4a.utils.ByteSlice;

public class PrivateKeyEncoded {

    private String algorithm;
    private String format;
    private ByteSlice encoded;

    public PrivateKeyEncoded() {
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public ByteSlice getEncoded() {
        return encoded;
    }

    public void setEncoded(ByteSlice encoded) {
        this.encoded = encoded;
    }
}
