package com.github.milegema.mlgm4a.data.files;

import java.nio.file.Path;
import java.security.KeyPair;

import javax.crypto.SecretKey;

public final class FileAccessContext {

    private Path file;

    private KeyPair keyPair;
    private SecretKey secretKey;
    private FileAccessKeyRef accessKey; // 用于加密/解密的密钥 : 它可能是 public|private|secret

    private FileAccessFilterChain chain;
    private FileAccessDataCodecGroup codecGroup;
    private FileAccessDataStateListener dataStateListener;


    public FileAccessContext() {
    }

    public FileAccessDataStateListener getDataStateListener() {
        return dataStateListener;
    }

    public void setDataStateListener(FileAccessDataStateListener dataStateListener) {
        this.dataStateListener = dataStateListener;
    }

    public FileAccessKeyRef getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(FileAccessKeyRef accessKey) {
        this.accessKey = accessKey;
    }

    public FileAccessDataCodecGroup getCodecGroup() {
        return codecGroup;
    }

    public void setCodecGroup(FileAccessDataCodecGroup codecGroup) {
        this.codecGroup = codecGroup;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public FileAccessFilterChain getChain() {
        return chain;
    }

    public void setChain(FileAccessFilterChain chain) {
        this.chain = chain;
    }

    public Path getFile() {
        return file;
    }

    public void setFile(Path file) {
        this.file = file;
    }
}
