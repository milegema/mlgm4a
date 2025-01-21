package com.github.milegema.mlgm4a.data.repositories.tables;

import com.github.milegema.mlgm4a.data.repositories.RepositoryContext;

import java.nio.file.Path;

public class DatabaseContext {

    private final RepositoryContext parent;

    private Schema schema;
    private Path tablesFolder; // the '{repo}/tables' folder
    private TableFileLocator tableFileLocator;
    private DataPropertyTableIO io;
    private boolean includeDeleted;

    public DatabaseContext(RepositoryContext _parent) {
        this.parent = _parent;
    }

    public DatabaseContext(DatabaseContext init) {
        this.io = init.io;
        this.parent = init.parent;
        this.schema = init.schema;
        this.tableFileLocator = init.tableFileLocator;
        this.tablesFolder = init.tablesFolder;
        this.includeDeleted = init.includeDeleted;
    }

    public DataPropertyTableIO getIo() {
        return io;
    }

    public void setIo(DataPropertyTableIO io) {
        this.io = io;
    }

    public TableFileLocator getTableFileLocator() {
        return tableFileLocator;
    }

    public void setTableFileLocator(TableFileLocator tableFileLocator) {
        this.tableFileLocator = tableFileLocator;
    }

    public RepositoryContext getParent() {
        return parent;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public Path getTablesFolder() {
        return tablesFolder;
    }

    public void setTablesFolder(Path tablesFolder) {
        this.tablesFolder = tablesFolder;
    }


    public boolean isIncludeDeleted() {
        return includeDeleted;
    }

    public void setIncludeDeleted(boolean includeDeleted) {
        this.includeDeleted = includeDeleted;
    }

    public DB toDB() {
        return new DefaultRepositoryDatabase(this);
    }
}
