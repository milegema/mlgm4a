package com.github.milegema.mlgm4a.data.files.layers;

import com.github.milegema.mlgm4a.data.files.FileAccessKeyRef;
import com.github.milegema.mlgm4a.data.files.FileAccessLayer;
import com.github.milegema.mlgm4a.security.CipherMode;
import com.github.milegema.mlgm4a.security.CipherPadding;

public class EncryptionLayer extends FileAccessLayer {

    private String algorithm;
    private byte[] iv;
    private CipherMode mode;
    private CipherPadding padding;
    private FileAccessKeyRef accessKey; // 用于加密/解密的密钥 : 它可能是 public|private|secret

    public EncryptionLayer() {
    }

    public FileAccessKeyRef getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(FileAccessKeyRef accessKey) {
        this.accessKey = accessKey;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }


    public CipherPadding getPadding() {
        return padding;
    }

    public void setPadding(CipherPadding padding) {
        this.padding = padding;
    }

    public CipherMode getMode() {
        return mode;
    }

    public void setMode(CipherMode mode) {
        this.mode = mode;
    }
}
