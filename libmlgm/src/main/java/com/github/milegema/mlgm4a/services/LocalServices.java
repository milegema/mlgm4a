package com.github.milegema.mlgm4a.services;

import android.content.Context;

import com.github.milegema.mlgm4a.components.ComponentManager;
import com.github.milegema.mlgm4a.contexts.ContextHolder;

public final class LocalServices {

    private LocalServices() {
    }

    public static <T> T getService(Context ctx, Class<T> t) {
        ContextHolder ch = ContextHolder.getInstance(ctx);
        ComponentManager com_man = ch.getApplicationContext().components();
        return com_man.find(t);
    }

}
