package com.github.milegema.mlgm4a.data.repositories;

import android.content.Context;

import com.github.milegema.mlgm4a.components.ComLife;
import com.github.milegema.mlgm4a.components.ComLifecycle;
import com.github.milegema.mlgm4a.contexts.ContextAgent;

import java.nio.file.Path;

public class AndroidRepositoriesFolder implements RepositoriesFolder, ComLifecycle {

    private ContextAgent contextAgent;
    private Path mLoadedFolder;

    public AndroidRepositoriesFolder() {
    }

    public AndroidRepositoriesFolder(Context ctx) {
        this.load(ctx);
    }

    public ContextAgent getContextAgent() {
        return contextAgent;
    }

    public void setContextAgent(ContextAgent contextAgent) {
        this.contextAgent = contextAgent;
    }

    @Override
    public Path folder() {
        Path f = this.mLoadedFolder;
        if (f == null) {
            throw new RepositoryException("no repos-folder loaded");
        }
        return f;
    }

    private void load() {
        Context ctx = this.contextAgent.getContextHolder().getAndroid();
        this.load(ctx);
    }

    private void load(Context ctx) {
        Path dir = ctx.getFilesDir().toPath();
        this.mLoadedFolder = dir.resolve("repositories");
    }

    @Override
    public ComLife life() {
        ComLife cl = new ComLife();
        cl.setOnCreate(this::load);
        return cl;
    }
}
