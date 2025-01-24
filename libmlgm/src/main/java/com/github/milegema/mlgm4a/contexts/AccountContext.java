package com.github.milegema.mlgm4a.contexts;

public class AccountContext extends ContextBase {

    private DomainContext parent;

    public AccountContext() {
    }

    public DomainContext getParent() {
        return parent;
    }

    public void setParent(DomainContext parent) {
        this.parent = parent;
    }

    @Override
    public BlockContext getParentContext() {
        return this.parent;
    }
}
