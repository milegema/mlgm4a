package com.github.milegema.mlgm4a.data.repositories.tables;

import com.github.milegema.mlgm4a.data.repositories.RepositoryContext;

import java.nio.file.Path;

public class DefaultTableManager implements TableManager {

    private final RepositoryContext context;

    public DefaultTableManager(RepositoryContext ctx) {
        this.context = ctx;
    }

    @Override
    public TableFileHolder get(TableName name) {

        DatabaseContext rdc = this.context.getDatabaseContext();
        Schema schema = rdc.getSchema();
        Table table = schema.getTable(name);
        Path file = rdc.getTableFileLocator().locateTableFile(name);

        DefaultTableHolder.Builder b = new DefaultTableHolder.Builder();
        b.file = file;
        b.table = table;
        return b.create();
    }

    @Override
    public DB db() {
        return context.getDatabaseContext().toDB();
    }
}
