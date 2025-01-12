package com.github.milegema.mlgm4a.data.repositories;

import java.nio.file.Path;

public final class RepositoryLayout {

    private Path key;// the 'key' file
    // private Path keySignature; // the 'key.signature' file [废弃]
    private Path config;// the 'config' file
    private Path refs;// the 'refs' folder
    private Path objects;// the 'objects' folder
    private Path repository; // the repository '.milegema' folder
    private Path tables; // the 'tables' folder
    private Path working; // the repository-working folder

    public RepositoryLayout() {
    }


    RepositoryLayout(RepositoryLayoutBuilder builder) {
        this.config = builder.config;
        this.key = builder.key;
        // this.keySignature = builder.keySignature;
        this.objects = builder.objects;
        this.refs = builder.refs;
        this.repository = builder.repository;
        this.tables = builder.tables;
        this.working = builder.working;
    }

    public Path getTables() {
        return tables;
    }

    public void setTables(Path tables) {
        this.tables = tables;
    }

    public Path getKey() {
        return key;
    }

    public void setKey(Path key) {
        this.key = key;
    }

    public Path getConfig() {
        return config;
    }

    public void setConfig(Path config) {
        this.config = config;
    }

    public Path getRefs() {
        return refs;
    }

    public void setRefs(Path refs) {
        this.refs = refs;
    }

    public Path getObjects() {
        return objects;
    }

    public void setObjects(Path objects) {
        this.objects = objects;
    }

    public Path getRepository() {
        return repository;
    }

    public void setRepository(Path repository) {
        this.repository = repository;
    }

    public Path getWorking() {
        return working;
    }

    public void setWorking(Path working) {
        this.working = working;
    }
}
