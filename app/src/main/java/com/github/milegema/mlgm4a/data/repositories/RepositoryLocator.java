package com.github.milegema.mlgm4a.data.repositories;

import java.nio.file.Files;
import java.nio.file.Path;

public final class RepositoryLocator {

    private final static String REGULAR_FOLDER_NAME = ".milegema";
    private int depthLimit;


    public RepositoryLocator() {
        this.depthLimit = 3;
    }

    public int getDepthLimit() {
        return depthLimit;
    }

    public void setDepthLimit(int depthLimit) {
        this.depthLimit = depthLimit;
    }

    public RepositoryLayout locate(final Path pwd) {
        Path p = pwd;
        for (int depth = 0; depth < this.depthLimit; depth++) {
            if (p == null) {
                break;
            }
            Path repo_dir = p.resolve(REGULAR_FOLDER_NAME);
            if (Files.isDirectory(repo_dir)) {
                return inner_load(repo_dir);
            }
            p = p.getParent();
        }
        throw new RepositoryException("cannot find repository at location: " + pwd);
    }

    private RepositoryLayout inner_load(Path repo_dir) {
        RepositoryLayoutBuilder b = new RepositoryLayoutBuilder();
        b.repository = repo_dir;
        return b.create();
    }


    public RepositoryLayout init(Path pwd, String name) {
        RepositoryLayoutBuilder b = new RepositoryLayoutBuilder();
        b.repository = pwd.resolve(name + "/" + REGULAR_FOLDER_NAME);
        return b.create();
    }
}
