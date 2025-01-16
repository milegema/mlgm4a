package com.github.milegema.mlgm4a.data.repositories.tables;

import java.nio.file.Path;

public class SchemaInstance {

    private Schema schema;
    private Path folder;

    public SchemaInstance() {
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public Path getFolder() {
        return folder;
    }

    public void setFolder(Path folder) {
        this.folder = folder;
    }
}
