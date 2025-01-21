package com.github.milegema.mlgm4a.data.repositories.tables;

public interface TableManager {

    TableFileHolder get(TableName name);

    DB db();

}
