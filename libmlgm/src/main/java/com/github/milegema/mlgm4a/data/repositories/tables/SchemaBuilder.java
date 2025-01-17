package com.github.milegema.mlgm4a.data.repositories.tables;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SchemaBuilder {

    private SchemaContext context;

    private List<TableInfoHolder> mTableInfoList;

    public SchemaBuilder() {
        this.reset();
    }

    private static class TableInfoHolder {

        Table table;
        TableBuilder tableBuilder;

        TableInfoHolder(Table t) {
            this.table = t;
        }

        TableInfoHolder(TableBuilder tb) {
            this.tableBuilder = tb;
        }

        public Table complete(Schema parent) {
            Table t = this.table;
            TableBuilder tb = this.tableBuilder;
            if (t == null) {
                if (tb == null) {
                    throw new TableException("table info holder is empty");
                }
                tb.setOwnerSchema(parent);
                t = tb.create();
                this.table = t;
            }
            return t;
        }
    }


    public void reset() {
        this.context = new SchemaContext();
        this.mTableInfoList = new ArrayList<>();
    }

    public TableBuilder newTableBuilder() {
        TableBuilder builder = new TableBuilder();
        builder.setOwnerSchema(this.schema());
        return builder;
    }

    public SchemaBuilder addTable(Table table) {
        // check owner
        Schema s1 = table.owner();
        Schema s2 = this.schema();
        if (s1 == null || s2 == null) {
            throw new TableException("owner is null");
        }
        if (!s1.equals(s2)) {
            throw new TableException("different owner");
        }
        // done
        this.mTableInfoList.add(new TableInfoHolder(table));
        return this;
    }

    public TableBuilder addTable(String name) {
        TableBuilder tb = this.newTableBuilder();
        tb.setName(name);
        // done
        this.mTableInfoList.add(new TableInfoHolder(tb));
        return tb;
    }

    public SchemaBuilder setName(String name) {
        this.context.setName(new SchemaName(name));
        return this;
    }

    public SchemaBuilder setName(SchemaName name) {
        this.context.setName(name);
        return this;
    }

    public Schema schema() {
        return this.context.getFacade();
    }

    private Table[] inner_create_tables(Schema parent) {
        List<TableInfoHolder> src = this.mTableInfoList;
        if (src == null) {
            return new Table[0];
        }
        List<Table> dst = new ArrayList<>();
        Set<TableName> names = new HashSet<>(); // 用于排重
        for (TableInfoHolder h : src) {
            Table t = h.complete(parent);
            TableName name = t.name();
            if (names.contains(name)) {
                throw new TableException("table name is duplicate: " + name);
            }
            names.add(name);
            dst.add(t);
        }
        return dst.toArray(new Table[0]);
    }

    public Schema create() {
        SchemaContext ctx = new SchemaContext(this.context);
        ctx.setTables(inner_create_tables(ctx.getFacade()));
        this.reset();
        return ctx.getFacade();
    }
}
