package com.github.milegema.mlgm4a.data.repositories.tables;

import com.github.milegema.mlgm4a.data.databases.DB;

public interface TableManager {

    TableFileHolder get(TableName name);

    DB db();

}
