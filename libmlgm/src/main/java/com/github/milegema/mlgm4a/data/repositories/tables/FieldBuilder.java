package com.github.milegema.mlgm4a.data.repositories.tables;

public class FieldBuilder {

    private FieldContext context;

    public FieldBuilder() {
        this.reset();
    }

    public void reset() {
        this.context = new FieldContext(new FieldName("undefined"), null);
    }

    public FieldBuilder setOwnerTable(Table owner) {
        this.context.setOwner(owner);
        return this;
    }

    public FieldBuilder setType(FieldType t) {
        this.context.setType(t);
        return this;
    }

    public FieldBuilder setUnique(boolean unique) {
        this.context.setUnique(unique);
        return this;
    }

    public FieldBuilder setNullable(boolean nullable) {
        this.context.setNullable(nullable);
        return this;
    }

    public FieldBuilder setName(String name) {
        this.context.setName(new FieldName(name));
        return this;
    }

    public FieldBuilder setName(FieldName name) {
        this.context.setName(name);
        return this;
    }

    public Field field() {
        return this.context.getFacade();
    }

    public Field create() {
        FieldContext ctx = this.context;
        this.reset();
        return ctx.getFacade();
    }
}
