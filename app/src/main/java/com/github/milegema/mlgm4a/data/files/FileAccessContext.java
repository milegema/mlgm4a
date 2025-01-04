package com.github.milegema.mlgm4a.data.files;

import java.nio.file.Path;

import javax.crypto.SecretKey;

public class FileAccessContext {

    private final FileRepositoryContext parent;

    private SecretKey key;
    private Path file;

    public FileAccessContext(FileRepositoryContext _parent) {
        this.parent = _parent;
    }

    public FileRepositoryContext getParent() {
        return parent;
    }

    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }

    public Path getFile() {
        return file;
    }

    public void setFile(Path file) {
        this.file = file;
    }
}
