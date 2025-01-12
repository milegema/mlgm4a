package com.github.milegema.mlgm4a.security.rsa;

import com.github.milegema.mlgm4a.security.KeyPairAlias;
import com.github.milegema.mlgm4a.security.PrivateKeyInstance;
import com.github.milegema.mlgm4a.security.PublicKeyInstance;

import java.security.KeyPair;
import java.security.PublicKey;

public class RsaKeyContext {

    private final RsaAlgorithmContext parent;

    private PrivateKeyInstance pairInstance;
    private PublicKeyInstance publicInstance;

    private PublicKey publicKey;
    private KeyPair keyPair;

    private KeyPairAlias alias;


    public RsaKeyContext(RsaAlgorithmContext p) {
        this.parent = p;
    }


    public RsaAlgorithmContext getParent() {
        return parent;
    }

    public KeyPairAlias getAlias() {
        return alias;
    }

    public void setAlias(KeyPairAlias alias) {
        this.alias = alias;
    }

    public PrivateKeyInstance getPairInstance() {
        return pairInstance;
    }

    public void setPairInstance(PrivateKeyInstance pairInstance) {
        this.pairInstance = pairInstance;
    }

    public PublicKeyInstance getPublicInstance() {
        return publicInstance;
    }

    public void setPublicInstance(PublicKeyInstance publicInstance) {
        this.publicInstance = publicInstance;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }
}
