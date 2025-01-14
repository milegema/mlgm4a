package com.github.milegema.mlgm4a.components;

import com.github.milegema.mlgm4a.logs.Logs;

import java.util.List;

public class ComLifeEventDispatcher implements ComLifecycle {

    private final ComLifeContext context;

    public ComLifeEventDispatcher(ComLifeContext ctx) {
        this.context = ctx;
    }


    private void forEachLife(boolean reverse, ComLife.Handler h) throws Exception {
        List<ComLife> all = context.lives;
        all.sort((o1, o2) -> {
            int n1 = o1.getOrder();
            int n2 = o2.getOrder();
            return n1 - n2;
        });
        if (!reverse) {
            for (ComLife life : all) {
                h.handle(life);
            }
        } else {
            for (int i = all.size() - 1; i >= 0; --i) {
                ComLife life = all.get(i);
                h.handle(life);
            }
        }
    }


    private void invoke(boolean catch_err, ComLife.Callback callback) throws Exception {
        if (callback == null) {
            return;
        }
        if (catch_err) {
            try {
                callback.invoke();
            } catch (Exception e) {
                Logs.warn("catch exception", e);
            }
            return;
        }
        callback.invoke();
    }

    private void onCreate() throws Exception {
        forEachLife(false, (life) -> {
            invoke(false, life.getOnCreate());
        });
        this.context.setCreated(true);
    }

    private void onDestroy() throws Exception {
        forEachLife(true, (life) -> {
            invoke(true, life.getOnDestroy());
        });
    }

    private void onStart() throws Exception {
        forEachLife(false, (life) -> {
            invoke(false, life.getOnStart());
        });
    }

    private void onStop() throws Exception {
        forEachLife(true, (life) -> {
            invoke(true, life.getOnStop());
        });
    }

    private void onResume() throws Exception {
        forEachLife(false, (life) -> {
            invoke(false, life.getOnResume());
        });
    }

    private void onPause() throws Exception {
        forEachLife(false, (life) -> {
            invoke(false, life.getOnPause());
        });
    }

    private void onLoop() throws Exception {
        forEachLife(false, (life) -> {
            invoke(false, life.getLoop());
        });
    }


    @Override
    public ComLife life() {
        ComLife cl = new ComLife();
        cl.setOnCreate(this::onCreate);
        cl.setOnStart(this::onStart);

        cl.setOnResume(this::onResume);
        cl.setLoop(this::onLoop);
        cl.setOnPause(this::onPause);

        cl.setOnStop(this::onStop);
        cl.setOnDestroy(this::onDestroy);
        return cl;
    }
}
