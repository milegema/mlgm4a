package com.github.milegema.mlgm4a.security;

import com.github.milegema.mlgm4a.utils.ByteSlice;

import javax.crypto.SecretKey;

public class SecretKeyEncoded {

    private String algorithm;
    private String format;
    private ByteSlice encoded;

    public SecretKeyEncoded() {
    }

    public SecretKeyEncoded(SecretKey sk) {
        this.algorithm = sk.getAlgorithm();
        this.format = sk.getFormat();
        this.encoded = new ByteSlice(sk.getEncoded());
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
