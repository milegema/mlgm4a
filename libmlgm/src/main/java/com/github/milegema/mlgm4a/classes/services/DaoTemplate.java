package com.github.milegema.mlgm4a.classes.services;

import com.github.milegema.mlgm4a.data.databases.DB;

import java.util.List;

public interface DaoTemplate<ID, ENTITY> {

    interface Updater<ID, ENTITY> {
        void onUpdate(ID id, ENTITY item);
    }

    interface Filter<ID, ENTITY> {
        boolean accept(ID id, ENTITY item);
    }

    ENTITY insert(DB db, ENTITY item);

    ENTITY update(DB db, ID id, Updater<ID, ENTITY> fn);

    ENTITY delete(DB db, ID id);

    ENTITY find(DB db, ID id);

    List<ENTITY> list(DB db, Filter<ID, ENTITY> filter);

}
