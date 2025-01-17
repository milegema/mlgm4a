package com.github.milegema.mlgm4a.data.repositories.tables;

public class SchemaFacade implements Schema {

    private final SchemaContext context;

    public SchemaFacade(SchemaContext ctx) {
        this.context = ctx;
    }

    @Override
    public SchemaName name() {
        return this.context.getName();
    }

    @Override
    public Table getTable(TableName name) {
        SchemaTablesCache cache = this.context.getTableCache();
        return cache.find(name, true);
    }
}
