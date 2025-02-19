package com.github.milegema.mlgm4a.classes.services;

import android.content.Context;

import java.util.List;

public interface ServiceTemplate<ID, ENTITY> extends LocalService {

    interface Updater<ID, ENTITY> {
        void onUpdate(ID id, ENTITY item);
    }

    interface Filter<ID, ENTITY> {
        boolean accept(ID id, ENTITY item);
    }

    ENTITY insert(Context ctx, ENTITY item);

    ENTITY update(Context ctx, ID id, Updater<ID, ENTITY> fn);

    ENTITY delete(Context ctx, ID id);

    ENTITY find(Context ctx, ID id);

    List<ENTITY> list(Context ctx, Filter<ID, ENTITY> filter);

}
