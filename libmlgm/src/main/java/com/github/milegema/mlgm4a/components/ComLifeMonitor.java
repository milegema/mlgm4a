package com.github.milegema.mlgm4a.components;

import com.github.milegema.mlgm4a.logs.Logs;

public class ComLifeMonitor implements ComLifecycle {

    public ComLifeMonitor() {
    }

    @Override
    public ComLife life() {
        final String prefix = "" + this;
        ComLife cl = new ComLife();

        cl.setOnCreate(() -> {
            Logs.debug(prefix + ".onCreate");
        });
        cl.setOnStart(() -> {
            Logs.debug(prefix + ".onStart");
        });
        cl.setOnResume(() -> {
            Logs.debug(prefix + ".onResume");
        });
        cl.setLoop(() -> {
            Logs.debug(prefix + ".onLoop");
        });
        cl.setOnPause(() -> {
            Logs.debug(prefix + ".onPause");
        });
        cl.setOnStop(() -> {
            Logs.debug(prefix + ".onStop");
        });
        cl.setOnDestroy(() -> {
            Logs.debug(prefix + ".onDestroy");
        });

        return cl;
    }
}
