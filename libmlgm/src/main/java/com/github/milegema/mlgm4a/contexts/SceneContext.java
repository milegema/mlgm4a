package com.github.milegema.mlgm4a.contexts;

public class SceneContext extends ContextBase {

    private AccountContext parent;


    public SceneContext() {
    }

    public AccountContext getParent() {
        return parent;
    }

    public void setParent(AccountContext parent) {
        this.parent = parent;
    }

    @Override
    public BlockContext getParentContext() {
        return this.parent;
    }
}
