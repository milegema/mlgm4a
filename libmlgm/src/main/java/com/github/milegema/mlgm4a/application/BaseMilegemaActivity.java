package com.github.milegema.mlgm4a.application;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.github.milegema.mlgm4a.components.ComLifeManager;
import com.github.milegema.mlgm4a.components.ComLife;
import com.github.milegema.mlgm4a.components.ComLifecycle;
import com.github.milegema.mlgm4a.contexts.ContextHolder;
import com.github.milegema.mlgm4a.logs.Logs;

public class BaseMilegemaActivity extends Activity implements MLGM {

    private final ComLifeManager mLifeManager = new ComLifeManager();
    private ComLife mMainComLife;


    private void invokeCallback(ComLife.Callback callback) {
        if (callback == null) {
            return;
        }
        try {
            callback.invoke();
        } catch (Exception e) {
            Logs.error("exception of life-cycle-callback", e);
        }
    }

    private ComLife getMainComLife() {
        ComLife cl = this.mMainComLife;
        if (cl == null) {
            cl = this.mLifeManager.getMain();
            this.mMainComLife = cl;
        }
        return cl;
    }

    public ComLifeManager getLifeManager() {
        return this.mLifeManager;
    }

    public void addLife(ComLifecycle lifecycle) {
        this.mLifeManager.add(lifecycle.life());
    }

    public void addLife(ComLife life) {
        this.mLifeManager.add(life);
    }

    @Override
    public ContextHolder getContextHolder() {
        BaseMilegemaApplication app = (BaseMilegemaApplication) this.getApplicationContext();
        return app.getContextHolder();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.invokeCallback(this.getMainComLife().getOnCreate());
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.invokeCallback(this.getMainComLife().getOnStart());
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.invokeCallback(this.getMainComLife().getOnPause());
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.invokeCallback(this.getMainComLife().getOnResume());
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.invokeCallback(this.getMainComLife().getOnStop());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.invokeCallback(this.getMainComLife().getOnDestroy());
    }
}
