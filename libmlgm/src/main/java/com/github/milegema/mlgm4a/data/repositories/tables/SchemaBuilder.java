package com.github.milegema.mlgm4a.data.repositories.tables;

import com.github.milegema.mlgm4a.data.ids.Alias;

import java.util.ArrayList;
import java.util.List;

public class SchemaBuilder {

    private SchemaContext context;

    private List<Table> mTableList;

    public SchemaBuilder() {
        this.reset();
    }

    public void reset() {
        this.context = new SchemaContext(new SchemaName("undefined"));
        this.mTableList = new ArrayList<>();
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
        this.mTableList.add(table);
        return this;
    }

    public Schema schema() {
        return this.context.getFacade();
    }

    private Table[] inner_create_tables() {
        List<Table> src = this.mTableList;
        if (src == null) {
            return new Table[0];
        }
        return src.toArray(new Table[0]);
    }

    public Schema create() {
        SchemaContext ctx = this.context;
        ctx.setTables(inner_create_tables());
        this.reset();
        return ctx.getFacade();
    }
}
