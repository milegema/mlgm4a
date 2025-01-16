package com.github.milegema.mlgm4a.data.repositories.tables;

public class TableFacade implements Table {

    private final TableContext context;

    public TableFacade(TableContext ctx) {
        this.context = ctx;
    }

    @Override
    public TableName name() {
        return this.context.getName();
    }

    @Override
    public Schema owner() {
        return this.context.getOwner();
    }
}
