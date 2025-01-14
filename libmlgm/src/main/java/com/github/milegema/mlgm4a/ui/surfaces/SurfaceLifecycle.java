package com.github.milegema.mlgm4a.ui.surfaces;

import android.view.SurfaceHolder;

import com.github.milegema.mlgm4a.components.ComLife;
import com.github.milegema.mlgm4a.components.ComLifecycle;

public class SurfaceLifecycle implements ComLifecycle {

    private final SurfaceContext context;

    public SurfaceLifecycle(SurfaceContext ctx) {
        this.context = ctx;
    }


    @Override
    public ComLife life() {
        ComLife cl = new ComLife();
        SurfaceCallback callback = new SurfaceCallback(this.context);

        cl.setOnCreate(() -> {
            SurfaceHolder holder = this.context.getView().getHolder();
            holder.addCallback(callback);
            this.context.setOriginHolder(holder);
        });

        cl.setOnStart(() -> {
        });
        cl.setOnStop(() -> {
        });

        return cl;
    }
}
