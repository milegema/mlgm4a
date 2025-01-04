package com.github.milegema.mlgm4a.data.files;

import java.nio.file.Path;
import java.security.KeyPair;

public class FileRepositoryContext {

    private KeyPair keyPair;
    private FileAccessFilterChain chain;
    private Path folder;

    public FileRepositoryContext() {
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

}
