package com.github.milegema.mlgm4a.data.files.layers;

import com.github.milegema.mlgm4a.data.files.FileAccessLayer;
import com.github.milegema.mlgm4a.security.CipherMode;
import com.github.milegema.mlgm4a.security.CipherPadding;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SecretKey;

public class EncryptionLayer extends FileAccessLayer {


    private byte[] iv;
    private SecretKey secretKey;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private CipherPadding padding;
    private CipherMode mode;
    private String algorithm;


    public EncryptionLayer() {
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

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
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
