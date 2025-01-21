package com.github.milegema.mlgm4a.data.repositories.tables;

import java.nio.file.Path;

public class TableFileLocator {

    private final DatabaseContext context;

    public TableFileLocator(DatabaseContext rdc) {
        this.context = rdc;
    }

    public Path locateTableFile(Table table) {
        return this.locateTableFile(table.name());
    }

    public Path locateTableFile(TableName table_name) {
        Path dir = this.context.getParent().getLayout().getTables();
        return dir.resolve(String.valueOf(table_name));
    }
}
