package com.github.milegema.mlgm4a.data.repositories.tables;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TableFieldsCache {

    private final TableContext context;
    private Map<FieldName, Field> cache;

    public TableFieldsCache(TableContext ctx) {
        this.context = ctx;
    }

    public void update() {
        this.cache = null;
    }

    private Map<FieldName, Field> inner_get_map() {
        Map<FieldName, Field> t = this.cache;
        if (t == null) {
            t = inner_load_map();
            t = Collections.synchronizedMap(t);
            this.cache = t;
        }
        return t;
    }

    private Map<FieldName, Field> inner_load_map() {
        Field[] src = this.context.getFields();
        Map<FieldName, Field> dst = new HashMap<>();
        if (src == null) {
            return dst;
        }
        for (Field item : src) {
            FieldName name = item.name();
            if (name == null) {
                continue;
            }
            dst.put(name, item);
        }
        return dst;
    }

    public Field find(FieldName name, boolean required) {
        Map<FieldName, Field> map = inner_get_map();
        Field field = map.get(name);
        if (field == null && required) {
            throw new TableException("no field with name: " + name);
        }
        return field;
    }
}
