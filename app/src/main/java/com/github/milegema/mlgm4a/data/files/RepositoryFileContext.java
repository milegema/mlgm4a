package com.github.milegema.mlgm4a.data.files;

import java.nio.file.Path;
import java.security.KeyPair;

import javax.crypto.SecretKey;

public final class RepositoryFileContext {

    private Path folder;

    private KeyPair keyPair;
    private SecretKey secretKey;
    private FileAccessFilterChain chain;

    public RepositoryFileContext() {
    }

    public Path getFolder() {
        return folder;
    }

    public void setFolder(Path folder) {
        this.folder = folder;
    }

    public FileAccessFilterChain getChain() {
        return chain;
    }

    public void setChain(FileAccessFilterChain chain) {
        this.chain = chain;
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

    public Path nextTemporaryFile() {
        Path dir = this.folder.resolve("tmp");
        return RepositoryTemporaryFileManager.getInstance().nextTempFile(dir);
    }
}
