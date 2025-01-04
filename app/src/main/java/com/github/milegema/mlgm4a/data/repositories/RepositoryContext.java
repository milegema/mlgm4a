package com.github.milegema.mlgm4a.data.repositories;

public class RepositoryContext {

    private RepositoryLayout layout;
    private RepositoryConfig config;

    public RepositoryContext() {
    }

    public RepositoryLayout getLayout() {
        return layout;
    }

    public void setLayout(RepositoryLayout layout) {
        this.layout = layout;
    }
}
