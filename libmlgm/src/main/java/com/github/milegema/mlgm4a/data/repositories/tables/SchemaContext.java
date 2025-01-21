package com.github.milegema.mlgm4a.data.repositories.tables;

import java.nio.file.Path;
import java.util.Map;

public class SchemaContext {

    private final Schema facade;
    private final SchemaTablesCache tableCache;
    private final TableEntityMapper mapper;

    private SchemaName name;
    private Table[] tables; // children


    public SchemaContext() {
        this.facade = new SchemaFacade(this);
        this.tableCache = new SchemaTablesCache(this);
        this.mapper = new TableEntityMapper(this);
    }

    public SchemaContext(SchemaContext src) {
        if (src != null) {
            this.name = src.name;
            this.tables = src.tables;
        }
        this.mapper = new TableEntityMapper(this);
        this.facade = new SchemaFacade(this);
        this.tableCache = new SchemaTablesCache(this);
    }

    public TableEntityMapper getMapper() {
        return mapper;
    }

    public SchemaName getName() {
        return name;
    }

    public void setName(SchemaName name) {
        this.name = name;
    }

    public Schema getFacade() {
        return facade;
    }

    public SchemaTablesCache getTableCache() {
        return tableCache;
    }

    public Table[] getTables() {
        return tables;
    }

    public void setTables(Table[] tables) {
        this.tables = tables;
    }
}
