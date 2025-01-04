package com.github.milegema.mlgm4a.data.repositories;

import java.nio.file.Path;

public class RepositoryLayout {

    private Path config;// the 'config' file
    private Path refs;// the 'refs' folder
    private Path objects;// the 'objects' folder
    private Path repository; // the 'repository' folder
    private Path working; // the 'working' folder

    public RepositoryLayout() {
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
