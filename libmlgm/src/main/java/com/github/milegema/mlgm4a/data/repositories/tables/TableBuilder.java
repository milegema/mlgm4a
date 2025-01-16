package com.github.milegema.mlgm4a.data.repositories.tables;

import java.util.ArrayList;
import java.util.List;

public class TableBuilder {

    private TableContext context;
    private List<Field> mFieldList;

    public TableBuilder() {
        this.reset();
    }

    public void reset() {
        this.context = new TableContext(new TableName("undefined"), null);
        this.mFieldList = new ArrayList<>();
    }

    public TableBuilder setOwnerSchema(Schema owner) {
        this.context.setOwner(owner);
        return this;
    }

    public TableBuilder setName(String name) {
        this.context.setName(new TableName(name));
        return this;
    }

    public TableBuilder setName(TableName name) {
        this.context.setName(name);
        return this;
    }


    public TableBuilder addField(Field field) {
        // check owner
        Table t1 = field.owner();
        Table t2 = this.table();
        if (t1 == null || t2 == null) {
            throw new TableException("owner is null");
        }
        if (!t1.equals(t2)) {
            throw new TableException("different owner");
        }
        // done
        this.mFieldList.add(field);
        return this;
    }

    public TableBuilder setPrimaryKey(Field pk) {
        this.context.setPrimary(pk);
        return this;
    }

    public FieldBuilder newFieldBuilder() {
        FieldBuilder builder = new FieldBuilder();
        builder.setOwnerTable(this.table());
        return builder;
    }

    public Table table() {
        return this.context.getFacade();
    }

    private Field[] inner_create_fields() {
        List<Field> src = this.mFieldList;
        if (src == null) {
            return new Field[0];
        }
        return src.toArray(new Field[0]);
    }

    public Table create() {
        TableContext ctx = this.context;
        ctx.setFields(inner_create_fields());
        this.reset();
        return ctx.getFacade();
    }
}
