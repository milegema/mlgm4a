package com.github.milegema.mlgm4a.data.repositories.tables;

public class FieldFacade implements Field {

    private final FieldContext context;

    public FieldFacade(FieldContext ctx) {
        this.context = ctx;
    }


    @Override
    public FieldName name() {
        return this.context.getName();
    }

    @Override
    public Table owner() {
        return this.context.getOwner();
    }

    @Override
    public FieldType type() {
        return this.context.getType();
    }
}
