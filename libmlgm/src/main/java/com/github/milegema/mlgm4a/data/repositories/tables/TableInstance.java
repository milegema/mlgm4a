package com.github.milegema.mlgm4a.data.repositories.tables;

import java.nio.file.Path;

public class TableInstance {

    private Table table;
    private Path file;

    public TableInstance() {
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Path getFile() {
        return file;
    }

    public void setFile(Path file) {
        this.file = file;
    }
}
