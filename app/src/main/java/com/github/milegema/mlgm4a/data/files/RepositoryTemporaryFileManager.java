package com.github.milegema.mlgm4a.data.files;

import java.nio.file.Path;

public final class RepositoryTemporaryFileManager {

    private final long t0;
    private int index;

    private RepositoryTemporaryFileManager() {
        this.t0 = System.currentTimeMillis();
        this.index = 10000;
    }

    private static RepositoryTemporaryFileManager inst;

    public static RepositoryTemporaryFileManager getInstance() {
        RepositoryTemporaryFileManager i = inst;
        if (i == null) {
            i = new RepositoryTemporaryFileManager();
            inst = i;
        }
        return i;
    }

    public synchronized Path nextTempFile(Path dir) {
        final long t1 = System.currentTimeMillis();
        final int idx = this.index++;
        return dir.resolve("tmp" + t0 + '-' + t1 + '-' + idx + ".tmp~");
    }
}
