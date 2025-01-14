package com.github.milegema.mlgm4a.ui.surfaces;

import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.github.milegema.mlgm4a.components.ComLifecycle;

public class SurfaceContext {

    private SurfaceView view;
    private SurfaceHolder originHolder;
    private SurfaceHolder currentHolder;
    private ComLifecycle lifecycle;
    private Runnable looper;
    private SurfaceRenderer renderer;

    private int format;
    private int frame;
    private int width;
    private int height;
    private int layoutRevision; // 可以通过查询这个值, 判断是否需要重新排版


    public SurfaceContext() {
    }

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public SurfaceRenderer getRenderer() {
        return renderer;
    }

    public void setRenderer(SurfaceRenderer renderer) {
        this.renderer = renderer;
    }

    public int getLayoutRevision() {
        return layoutRevision;
    }

    public void setLayoutRevision(int layoutRevision) {
        this.layoutRevision = layoutRevision;
    }

    public Runnable getLooper() {
        return looper;
    }

    public void setLooper(Runnable looper) {
        this.looper = looper;
    }

    public SurfaceView getView() {
        return view;
    }

    public void setView(SurfaceView view) {
        this.view = view;
    }

    public SurfaceHolder getOriginHolder() {
        return originHolder;
    }

    public void setOriginHolder(SurfaceHolder originHolder) {
        this.originHolder = originHolder;
    }

    public SurfaceHolder getCurrentHolder() {
        return currentHolder;
    }

    public void setCurrentHolder(SurfaceHolder currentHolder) {
        this.currentHolder = currentHolder;
    }

    public ComLifecycle getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(ComLifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }


    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
