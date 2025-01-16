package com.github.milegema.mlgm4a.data.repositories.tables;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SchemaTablesCache {

    private final SchemaContext context;
    private Map<TableName, Table> cache;


    public SchemaTablesCache(SchemaContext ctx) {
        this.context = ctx;
    }

    public void update() {
        this.cache = null;
    }

    private Map<TableName, Table> inner_load_map() {
        Table[] src = this.context.getTables();
        Map<TableName, Table> dst = new HashMap<>();
        if (src == null) {
            return dst;
        }
        for (Table item : src) {
            TableName name = item.name();
            if (name == null) {
                continue;
            }
            dst.put(name, item);
        }
        return dst;
    }

    private Map<TableName, Table> inner_get_map() {
        Map<TableName, Table> t = this.cache;
        if (t == null) {
            t = inner_load_map();
            t = Collections.synchronizedMap(t);
            this.cache = t;
        }
        return t;
    }

    public Table find(TableName name, boolean required) {
        Table table = inner_get_map().get(name);
        if (table == null && required) {
            throw new TableException("no table with name: " + name);
        }
        return table;
    }
}
