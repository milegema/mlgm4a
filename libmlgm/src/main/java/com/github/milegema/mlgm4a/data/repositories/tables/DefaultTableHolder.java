package com.github.milegema.mlgm4a.data.repositories.tables;

import com.github.milegema.mlgm4a.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DefaultTableHolder implements TableFileHolder {

    private final Path mFile;
    private final Table mTable;
    private final TableName mName;


    private DefaultTableHolder(Builder builder) {
        this.mFile = builder.file;
        this.mTable = builder.table;
        this.mName = builder.table.name();
    }

    public static class Builder {

        Path file;
        Table table;

        public TableFileHolder create() {
            return new DefaultTableHolder(this);
        }
    }


    @Override
    public TableName name() {
        return this.mName;
    }

    @Override
    public Table table() {
        return this.mTable;
    }

    @Override
    public Path file() {
        return this.mFile;
    }

    @Override
    public boolean create() {
        Path file = this.mFile;
        if (Files.exists(file)) {
            return false;
        }
        try {
            FileUtils.mkdirsForFile(file);
            Files.createFile(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean exists() {
        return Files.exists(mFile);
    }
}
