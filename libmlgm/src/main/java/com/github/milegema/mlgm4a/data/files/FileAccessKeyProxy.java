package com.github.milegema.mlgm4a.data.files;

import java.security.Key;

public class FileAccessKeyProxy implements FileAccessKeyRef {

    private final Key mKey;
    private final FileAccessKeyRef mParent;

    public FileAccessKeyProxy(Key k) {
        this.mKey = k;
        this.mParent = null;
    }

    public FileAccessKeyProxy(Key k, FileAccessKeyRef parent) {
        this.mKey = k;
        this.mParent = parent;
    }

    @Override
    public Key key() {
        if (mKey != null) {
            return mKey;
        }
        if (mParent != null) {
            return mParent.key();
        }
        return null;
    }
}
