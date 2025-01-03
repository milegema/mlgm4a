package com.github.milegema.mlgm4a.tasks;

import android.app.Activity;

final class AndroidPromiseContexts extends PromiseContext {

    private AndroidPromiseContexts() {
    }

    private static class MyBgWorker implements BackgroundExecutor {
        @Override
        public void execute(Runnable t) {
            Thread th = new Thread(t);
            th.start();
        }
    }

    private static class MyFgWorker implements ForegroundExecutor {

        private final Activity activity;

        public MyFgWorker(Activity a) {
            this.activity = a;
        }

        @Override
        public void execute(Runnable t) {
            this.activity.runOnUiThread(t);
        }
    }

    public static PromiseContext create(Activity activity) {
        PromiseContext pc = new PromiseContext();
        pc.setBackground(new MyBgWorker());
        pc.setForeground(new MyFgWorker(activity));
        return pc;
    }
}
