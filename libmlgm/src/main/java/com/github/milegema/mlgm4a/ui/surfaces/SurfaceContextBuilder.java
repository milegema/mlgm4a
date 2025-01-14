package com.github.milegema.mlgm4a.ui.surfaces;

import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.github.milegema.mlgm4a.components.ComLifecycle;

public class SurfaceContextBuilder {

    private SurfaceView view;

    public SurfaceContextBuilder() {
    }

    public SurfaceView getView() {
        return view;
    }

    public void setView(SurfaceView view) {
        this.view = view;
    }

    public SurfaceContext create() {
        SurfaceContext ctx = new SurfaceContext();

        ctx.setView(this.view);
        ctx.setLifecycle(new SurfaceLifecycle(ctx));

        return ctx;
    }
}
