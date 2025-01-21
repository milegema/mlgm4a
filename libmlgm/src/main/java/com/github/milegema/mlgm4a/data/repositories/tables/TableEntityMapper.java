package com.github.milegema.mlgm4a.data.repositories.tables;

import java.util.HashMap;
import java.util.Map;

public final class TableEntityMapper {

    private final Map<Class<?>, Table> cache;
    private final SchemaContext context;

    public TableEntityMapper(SchemaContext ctx) {
        this.context = ctx;
        this.cache = new HashMap<>();
    }

    public Table findTable(Class<?> entity_class) {
        Table table = this.cache.get(entity_class);
        if (table == null) {
            table = this.load_with_entity_class(entity_class);
            this.cache.put(entity_class, table);
        }
        return table;
    }

    private Table load_with_entity_class(Class<?> entity_class) {
        Table res = null;
        Table[] src = this.context.getTables();
        for (Table table : src) {
            Class<?> type = table.getEntityClass();
            if (type.equals(entity_class)) {
                res = table;
            }
            this.cache.put(type, table);
        }
        if (res == null) {
            String ec_name = entity_class.getName();
            throw new TableException("no table mapped to entity class:" + ec_name);
        }
        return res;
    }
}
