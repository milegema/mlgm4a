package com.github.milegema.mlgm4a.security;

public class SecretKeyConfig {

    private String algorithm;
    private int size;
    private SecretKeyStore store;

    public SecretKeyConfig() {
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public SecretKeyStore getStore() {
        return store;
    }

    public void setStore(SecretKeyStore store) {
        this.store = store;
    }
}
