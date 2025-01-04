package com.github.milegema.mlgm4a;

import android.app.Application;
import android.content.Context;

import com.github.milegema.mlgm4a.contexts.ContextHolder;

public class MilegemaApplication extends Application implements MilegemaApp {

    private ContextHolder mContextHolder;

    public static MilegemaApplication getInstance(Context ctx) {
        return (MilegemaApplication) ctx.getApplicationContext();
    }

    @Override
    public ContextHolder getContextHolder() {
        ContextHolder ch = mContextHolder;
        if (ch == null) {
            ch = new ContextHolder();
            ch.setApp(this);
            ch.setAndroid(this);
            mContextHolder = ch;
        }
        return ch;
    }
}
