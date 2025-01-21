package com.github.milegema.mlgm4a.data.repositories.tables;

import java.nio.file.Path;

public interface TableFileHolder {

    TableName name();

    Table table();

    Path file();

    boolean create();

    boolean exists();

}
