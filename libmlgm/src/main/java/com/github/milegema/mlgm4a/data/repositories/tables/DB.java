package com.github.milegema.mlgm4a.data.repositories.tables;

public interface DB {

    class Query<T> {
        public Class<T> type;
    }


    <T> T[] query(Query<T> q);

    <T> T find(Object id, Class<T> t);

    <T> T create(T entity);

    <T> T update(Object id, T entity);

    void delete(Object entity);

}
