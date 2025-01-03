package com.github.milegema.mlgm4a.components;

public class ComLifeProxy {

    private final ComLife inner;
    private final ComLife outer;

    private boolean created;
    private boolean started;

    ComLifeProxy(ComLife in) {
        if (in == null) {
            in = new ComLife();
        }
        this.inner = in;
        this.outer = initOuter();
    }

    private ComLife initOuter() {
        ComLife o = new ComLife();
        o.setOnCreate(this::onCreate);
        o.setOnStart(this::onStart);
        o.setLoop(this::onLoop);
        o.setOnStop(this::onStop);
        o.setOnDestroy(this::onDestroy);
        return o;
    }

    private void invoke(ComLife.Callback callback) throws Exception {
        if (callback == null) {
            return;
        }
        callback.invoke();
    }

    private void onCreate() throws Exception {
        invoke(inner.getOnCreate());
        this.created = true;
    }

    private void onStart() throws Exception {
        invoke(inner.getOnStart());
        this.started = true;
    }

    private void onLoop() throws Exception {
        invoke(inner.getLoop());
    }

    private void onStop() throws Exception {
        if (this.started) {
            invoke(inner.getOnStop());
        }
    }

    private void onDestroy() throws Exception {
        if (this.created) {
            invoke(inner.getOnDestroy());
        }
    }

    public ComLife life() {
        return this.outer;
    }
}
