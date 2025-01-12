package com.github.milegema.mlgm4a.security.aes;

import com.github.milegema.mlgm4a.security.SecretKeyInstance;

import javax.crypto.SecretKey;

final class AesKeyContext {

    private final AesAlgorithmContext parent;

    private SecretKey key;
    private SecretKeyInstance instance;

    public AesKeyContext(AesAlgorithmContext p) {
        this.parent = p;
    }

    public AesAlgorithmContext getParent() {
        return parent;
    }

    public SecretKeyInstance getInstance() {
        return instance;
    }

    public void setInstance(SecretKeyInstance instance) {
        this.instance = instance;
    }

    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }
}
