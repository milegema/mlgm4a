package com.github.milegema.mlgm4a.ui.surfaces;

import android.view.SurfaceHolder;

import androidx.annotation.NonNull;

public class SurfaceCallback implements SurfaceHolder.Callback {

    private final SurfaceContext context;

    public SurfaceCallback(SurfaceContext ctx) {
        this.context = ctx;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        SurfaceLooper looper = new SurfaceLooper(this.context);
        this.context.setLooper(looper);
        this.context.setCurrentHolder(holder);
        looper.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        this.context.setCurrentHolder(holder);
        this.context.setFormat(format);
        this.context.setWidth(width);
        this.context.setHeight(height);
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        this.context.setCurrentHolder(null);
    }
}
