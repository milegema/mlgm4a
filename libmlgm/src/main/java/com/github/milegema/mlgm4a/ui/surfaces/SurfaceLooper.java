package com.github.milegema.mlgm4a.ui.surfaces;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.github.milegema.mlgm4a.components.ComLifecycle;
import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.utils.Time;

public class SurfaceLooper implements Runnable {

    private final SurfaceContext context;

    public SurfaceLooper(SurfaceContext ctx) {
        this.context = ctx;
    }

    public void start() {
        Thread th = new Thread(this);
        th.start();
    }

    @Override
    public void run() {
        for (int frame = 0; ; frame++) {
            Runnable looper = this.context.getLooper();
            SurfaceHolder holder = this.context.getCurrentHolder();
            if (!this.isAlive(looper)) {
                break;
            }
            if (holder == null) {
                Time.sleep(300);
                continue;
            }
            Canvas canvas = holder.lockCanvas();
            try {
                this.onPaint(canvas, frame);
            } catch (Exception e) {
                Logs.error("surface-canvas-paint-error", e);
                Time.sleep(300);
            } finally {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void onPaint(Canvas canvas, int frame) throws Exception {

        SurfaceRenderer renderer = this.context.getRenderer();
        if (renderer == null || canvas == null) {
            return;
        }

        // check layout
        final int h1 = canvas.getHeight();
        final int w1 = canvas.getWidth();
        final int h2 = context.getHeight();
        final int w2 = context.getWidth();
        if (h1 != h2 || w1 != w2) {
            int rev = context.getLayoutRevision();
            context.setWidth(w1);
            context.setHeight(h1);
            context.setLayoutRevision(rev + 1);
        }

        // paint
        context.setFrame(frame);
        renderer.render(context, canvas);
    }

    private boolean isAlive(Runnable looper) {
        return this.equals(looper);
    }
}
