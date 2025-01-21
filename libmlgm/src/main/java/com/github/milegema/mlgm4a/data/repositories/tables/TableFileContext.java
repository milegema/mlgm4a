package com.github.milegema.mlgm4a.data.repositories.tables;

import java.nio.file.Path;

public class TableFileContext {

    private Table table;
    private Path file;
    private Class<?> entityClass;
    private EntityAdapter entityAdapter;
    private IdentityGenerator identityGenerator;

    public TableFileContext() {
    }


    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Path getFile() {
        return file;
    }

    public void setFile(Path file) {
        this.file = file;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public EntityAdapter getEntityAdapter() {
        return entityAdapter;
    }

    public void setEntityAdapter(EntityAdapter entityAdapter) {
        this.entityAdapter = entityAdapter;
    }

    public IdentityGenerator getIdentityGenerator() {
        return identityGenerator;
    }

    public void setIdentityGenerator(IdentityGenerator identityGenerator) {
        this.identityGenerator = identityGenerator;
    }
}
