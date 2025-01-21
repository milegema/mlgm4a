package com.github.milegema.mlgm4a.data.repositories.tables;

import java.util.List;

public interface Schema {

    SchemaName name();

    Table getTable(TableName name);

    List<Table> listTables();

    Table findTableByEntityClass(Class<?> t);

    Table findTableByEntityInstance(Object inst);

}
