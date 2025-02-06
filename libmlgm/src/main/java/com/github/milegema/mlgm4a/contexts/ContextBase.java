package com.github.milegema.mlgm4a.contexts;

import com.github.milegema.mlgm4a.data.repositories.Repository;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public abstract class ContextBase implements BlockContext {

    private ContextScope scope;
    private Repository repository;
    private KeyPair contextKeyPair;
    private PublicKey contextPublicKey;
    private PrivateKey contextPrivateKey;

    public ContextBase() {
    }

    public KeyPair getContextKeyPair() {
        return contextKeyPair;
    }

    public void setContextKeyPair(KeyPair contextKeyPair) {
        this.contextKeyPair = contextKeyPair;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public PublicKey getContextPublicKey() {
        return contextPublicKey;
    }

    public void setContextPublicKey(PublicKey contextPublicKey) {
        this.contextPublicKey = contextPublicKey;
    }

    public PrivateKey getContextPrivateKey() {
        return contextPrivateKey;
    }

    public void setContextPrivateKey(PrivateKey contextPrivateKey) {
        this.contextPrivateKey = contextPrivateKey;
    }

    public ContextScope getScope() {
        return scope;
    }

    public void setScope(ContextScope scope) {
        this.scope = scope;
    }
}
