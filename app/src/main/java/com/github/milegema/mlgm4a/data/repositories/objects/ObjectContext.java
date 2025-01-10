package com.github.milegema.mlgm4a.data.repositories.objects;

import com.github.milegema.mlgm4a.data.repositories.RepositoryContext;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockID;

import java.nio.file.Path;

public class ObjectContext {

    private RepositoryContext parent;
    private ObjectInfoCache cache;
    private BlockID id;
    private ObjectHolder holder;
    private Path file;

    public ObjectContext() {
    }

    public RepositoryContext getParent() {
        return parent;
    }

    public void setParent(RepositoryContext parent) {
        this.parent = parent;
    }

    public ObjectInfoCache getCache() {
        return cache;
    }

    public void setCache(ObjectInfoCache cache) {
        this.cache = cache;
    }

    public Path getFile() {
        return file;
    }

    public void setFile(Path file) {
        this.file = file;
    }

    public BlockID getId() {
        return id;
    }

    public void setId(BlockID id) {
        this.id = id;
    }

    public ObjectHolder getHolder() {
        return holder;
    }

    public void setHolder(ObjectHolder holder) {
        this.holder = holder;
    }
}
