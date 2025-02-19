package com.github.milegema.mlgm4a.data.databases;

import com.github.milegema.mlgm4a.data.ids.EntityID;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public interface DB {

    class Query<E> {
        public Class<E> type;
        public int limit;
        public int offset;
        public Comparator<E> sorter;
        public QueryFilter<E> filter;

        public Query(Class<E> t) {
            this.type = t;
            this.results = new ArrayList<>();
            this.offset = 0;
            this.limit = 10;
        }

        public Throwable error;
        public List<E> results;
    }

    interface QueryFilter<E> {
        boolean accept(E e);
    }

    interface TransactionHandler {
        void onTransaction(DB db) throws DatabaseException;
    }

    void transaction(TransactionHandler h) throws DatabaseException;

    void clearCache();

    <T> void query(Query<T> q);

    <T> T find(EntityID id, Class<T> t);

    <T> T create(T entity);

    <T> T update(EntityID id, T entity);

    void delete(EntityID id, Class<?> t);

    void commit();

    DB includeDeleted();

}
