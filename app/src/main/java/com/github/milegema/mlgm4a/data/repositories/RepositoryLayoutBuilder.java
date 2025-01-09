package com.github.milegema.mlgm4a.data.repositories;

import java.nio.file.Path;

public class RepositoryLayoutBuilder {

    public Path key;// the 'key' file
    // public Path keySignature;// the 'key.signature' file
    public Path config;// the 'config' file
    public Path refs;// the 'refs' folder
    public Path objects;// the 'objects' folder
    public Path tables;// the 'tables' folder
    public Path repository; // the 'repository' folder
    public Path working; // the 'working' folder

    public RepositoryLayoutBuilder() {
    }

    public RepositoryLayout create() {
        final Path repo = this.repository;

        this.config = repo.resolve("config");
        this.key = repo.resolve("key");
     //   this.keySignature = repo.resolve("key.signature");
        this.objects = repo.resolve("objects");
        this.refs = repo.resolve("refs");
        this.tables = repo.resolve("tables");
        this.working = repo.getParent();

        return new RepositoryLayout(this);
    }
}
