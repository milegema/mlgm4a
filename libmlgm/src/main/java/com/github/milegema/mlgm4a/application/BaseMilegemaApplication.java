package com.github.milegema.mlgm4a.application;

import android.app.Application;

import com.github.milegema.mlgm4a.contexts.ContextHolder;
import com.github.milegema.mlgm4a.logs.AndroidLogger;

public class BaseMilegemaApplication extends Application implements MLGM {

    private ContextHolder mContextHolder;

    @Override
    public ContextHolder getContextHolder() {
        ContextHolder ch = mContextHolder;
        if (ch == null) {
            ch = new ContextHolder();
            ch.setAndroid(this);
            ch.setApp(this);
            mContextHolder = ch;
        }
        return ch;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidLogger.init();
    }
}
