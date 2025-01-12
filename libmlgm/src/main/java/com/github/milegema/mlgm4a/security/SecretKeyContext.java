package com.github.milegema.mlgm4a.security;

import com.github.milegema.mlgm4a.data.files.layers.ContentLayer;

import javax.crypto.SecretKey;

public class SecretKeyContext {

    private SecretKey key;
    private SecretKeyAlias alias;
    private SecretKeyHolder holder;
    private SecretKeyStore store;
    private SecretKeyConfig config;
    private SecretKeyManager manager;

    private ContentLayer content;


    public SecretKeyContext() {
    }

    public ContentLayer getContent() {
        return content;
    }

    public void setContent(ContentLayer content) {
        this.content = content;
    }

    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }

    public SecretKeyAlias getAlias() {
        return alias;
    }

    public void setAlias(SecretKeyAlias alias) {
        this.alias = alias;
    }

    public SecretKeyHolder getHolder() {
        return holder;
    }

    public void setHolder(SecretKeyHolder holder) {
        this.holder = holder;
    }

    public SecretKeyStore getStore() {
        return store;
    }

    public void setStore(SecretKeyStore store) {
        this.store = store;
    }

    public SecretKeyConfig getConfig() {
        return config;
    }

    public void setConfig(SecretKeyConfig config) {
        this.config = config;
    }

    public SecretKeyManager getManager() {
        return manager;
    }

    public void setManager(SecretKeyManager manager) {
        this.manager = manager;
    }
}
