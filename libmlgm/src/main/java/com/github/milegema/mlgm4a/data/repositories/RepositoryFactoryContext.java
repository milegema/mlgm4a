package com.github.milegema.mlgm4a.data.repositories;

import com.github.milegema.mlgm4a.data.repositories.tables.Schema;

public class RepositoryFactoryContext {

    private Schema schema;
    private RepositoryFactory factory;

    public RepositoryFactoryContext() {
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public RepositoryFactory getFactory() {
        return factory;
    }

    public void setFactory(RepositoryFactory factory) {
        this.factory = factory;
    }
}
