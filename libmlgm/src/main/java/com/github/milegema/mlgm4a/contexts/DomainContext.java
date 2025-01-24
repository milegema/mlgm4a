package com.github.milegema.mlgm4a.contexts;

public class DomainContext extends ContextBase {

    private UserContext parent;

    public DomainContext() {
    }

    @Override
    public BlockContext getParentContext() {
        return this.parent;
    }

    public UserContext getParent() {
        return parent;
    }

    public void setParent(UserContext parent) {
        this.parent = parent;
    }
}
