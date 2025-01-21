package com.github.milegema.mlgm4a.data.repositories.tables;

public interface Table {

    TableName name();

    Schema owner();

    Field primaryKey();

    IdentityGenerator getIdentityGenerator();

    EntityAdapter getEntityAdapter();

    Class<?> getEntityClass();

}
