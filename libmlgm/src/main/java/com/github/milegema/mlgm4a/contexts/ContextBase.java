package com.github.milegema.mlgm4a.contexts;

import com.github.milegema.mlgm4a.data.repositories.Repository;

public abstract class ContextBase implements BlockContext {

    private ContextScope scope;
    private Repository ownerRepository;

    public ContextBase() {
    }

    public Repository getOwnerRepository() {
        return ownerRepository;
    }

    public void setOwnerRepository(Repository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public ContextScope getScope() {
        return scope;
    }

    public void setScope(ContextScope scope) {
        this.scope = scope;
    }
}
