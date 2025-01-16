package com.github.milegema.mlgm4a.data.repositories.tables;

public interface Field {

    FieldName name();

    Table owner();

    FieldType type();
}
