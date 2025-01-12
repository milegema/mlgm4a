package com.github.milegema.mlgm4a.components;


import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.utils.Time;

public class ComLifeRunner implements Runnable {

    private final ComLife life;
    private final ComLifeContext context;

    // private final ContextHolder holder;

    public ComLifeRunner(ComLifeContext _ctx, ComLife _life) {
        this.life = new ComLife(_life);
        this.context = _ctx;
        //    this.holder = _holder;
    }

    public void start() {
        Thread th = new Thread(this);
        th.start();
        this.waitUtilStarting(5000);
    }

    public void run() {
        try {
            this.context.starting = true;
            run1(this.life);
        } catch (Exception e) {
            this.context.push(e);
        } finally {
            this.context.stopped = true;
            Logs.info("done");
        }
    }

    private void run1(ComLife cl) throws Exception {
        try {
            // create
            invoke(cl.getOnCreate());
            run2(cl);
        } finally {
            // destroy
            invoke(cl.getOnDestroy());
        }
    }

    private void run2(ComLife cl) throws Exception {
        try {
            // start
            invoke(cl.getOnStart());
            this.context.started = true;
            run3(cl);
        } finally {
            // stop
            this.context.stopping = true;
            invoke(cl.getOnStop());
        }
    }

    private void run3(ComLife cl) throws Exception {
        invoke(cl.getLoop());
    }

    private void invoke(ComLife.Callback callback) throws Exception {
        if (callback == null) {
            return;
        }
        callback.invoke();
    }

    public void waitUtilStarted(int timeout) {
        final int step = 1000;
        for (int ttl = timeout; ttl > 0; ttl -= step) {
            if (this.context.started) {
                return;
            }
            if (this.context.stopped) {
                throw new RuntimeException("stopped");
            }
            Time.sleep(step);
        }
        throw new RuntimeException("timeout: in ComponentLifeRunner.waitUtilStarted()");
    }

    public void waitUtilStarting(int timeout) {
        final int step = 1000;
        for (int ttl = timeout; ttl > 0; ttl -= step) {
            if (this.context.starting) {
                return;
            }
            if (this.context.stopped) {
                throw new RuntimeException("stopped");
            }
            Time.sleep(step);
        }
        throw new RuntimeException("timeout: in ComponentLifeRunner.waitUtilStarting()");
    }
}
