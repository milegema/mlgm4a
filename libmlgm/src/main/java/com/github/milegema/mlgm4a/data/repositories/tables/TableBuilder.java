package com.github.milegema.mlgm4a.data.repositories.tables;

import com.github.milegema.mlgm4a.logs.Logs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TableBuilder {

    private TableContext context;
    private List<FieldInfoHolder> mFieldInfoList;

    // private FieldName mPrimaryKeyName;

    public TableBuilder() {
        this.reset();
    }

    private static class FieldInfoHolder {
        Field field;
        FieldBuilder fieldBuilder;

        FieldInfoHolder(Field f) {
            this.field = f;
        }

        FieldInfoHolder(FieldBuilder fb) {
            this.fieldBuilder = fb;
        }

        Field complete(Table parent) {
            Field f = this.field;
            FieldBuilder fb = this.fieldBuilder;
            if (f == null) {
                if (fb == null) {
                    throw new TableException("field info is empty");
                }
                fb.setOwnerTable(parent);
                f = fb.create();
                this.field = f;
            }
            return f;
        }
    }

    public void reset() {
        this.context = new TableContext();
        this.mFieldInfoList = new ArrayList<>();
        this.context.setIdentityGenerator(new DefaultIdentityGenerator());
    }

    public TableBuilder setOwnerSchema(Schema owner) {
        this.context.setOwner(owner);
        return this;
    }

    public TableBuilder setEntityInfo(Class<?> entity_class) {
        this.context.setEntityClass(entity_class);
        return this;
    }

    public TableBuilder setEntityInfo(EntityAdapter adapter) {
        this.context.setEntityAdapter(adapter);
        return this;
    }

    public TableBuilder setEntityInfo(IdentityGenerator idg) {
        this.context.setIdentityGenerator(idg);
        return this;
    }

    public TableBuilder setEntityInfo(Class<?> entity_class, EntityAdapter adapter) {
        this.context.setEntityAdapter(adapter);
        this.context.setEntityClass(entity_class);
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
        this.mFieldInfoList.add(new FieldInfoHolder(field));
        return this;
    }

    public TableBuilder addPrimaryKey(Field pk) {
        this.context.setPrimary(pk.name());
        this.mFieldInfoList.add(new FieldInfoHolder(pk));
        return this;
    }

    public FieldBuilder addPrimaryKey(String name, FieldType type) {
        FieldBuilder fb = this.newFieldBuilder();
        fb.setName(name);
        fb.setType(type);
        fb.setUnique(true);
        FieldInfoHolder holder = new FieldInfoHolder(fb);
        this.context.setPrimary(new FieldName(name));
        this.mFieldInfoList.add(holder);
        return fb;
    }


    public FieldBuilder newFieldBuilder() {
        FieldBuilder builder = new FieldBuilder();
        builder.setOwnerTable(this.table());
        return builder;
    }

    public FieldBuilder addField(String name, FieldType type) {
        FieldBuilder builder = new FieldBuilder();
        builder.setOwnerTable(this.table());
        builder.setName(name);
        builder.setType(type);
        this.mFieldInfoList.add(new FieldInfoHolder(builder));
        return builder;
    }


    public Table table() {
        return this.context.getFacade();
    }

    private Field[] inner_create_fields(Table parent) {
        List<FieldInfoHolder> src = this.mFieldInfoList;
        List<Field> dst = new ArrayList<>();
        if (src == null) {
            return new Field[0];
        }
        Set<FieldName> names = new HashSet<>(); // 用于排重
        for (FieldInfoHolder h : src) {
            Field field = h.complete(parent);
            FieldName f_name = field.name();
            if (names.contains(f_name)) {
                TableName table_name = this.context.getName();
                String msg = "field name is duplicate, table:" + table_name + " field:" + f_name;
                Logs.warn(msg);
                throw new TableException(msg);
            }
            names.add(f_name);
            dst.add(field);
        }
        return dst.toArray(new Field[0]);
    }

    public Table create() {
        TableContext ctx = new TableContext(this.context);
        Field[] fields = inner_create_fields(ctx.getFacade());
        ctx.setFields(fields);
        this.reset();
        return ctx.getFacade();
    }
}
