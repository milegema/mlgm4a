package com.github.milegema.mlgm4a.data.repositories.tables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Override
    public List<Table> listTables() {
        Table[] src = this.context.getTables();
        return new ArrayList<>(Arrays.asList(src));
    }

    @Override
    public Table findTableByEntityClass(Class<?> t) {
        return this.context.getMapper().findTable(t);
    }

    @Override
    public Table findTableByEntityInstance(Object inst) {
        if (inst == null) {
            throw new TableException("entity instance object is null");
        }
        return this.findTableByEntityClass(inst.getClass());
    }
}
