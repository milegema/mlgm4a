package com.github.milegema.mlgm4a.data.repositories.tables;

public interface TableHolder {

    TableName name();

    Table table();

    boolean create();

    boolean exists();

}
