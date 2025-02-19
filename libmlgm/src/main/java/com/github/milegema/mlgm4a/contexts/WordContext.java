package com.github.milegema.mlgm4a.contexts;

public class WordContext extends ContextBase {

    private SceneContext parent;

    public WordContext() {
    }

    public SceneContext getParent() {
        return parent;
    }

    public void setParent(SceneContext parent) {
        this.parent = parent;
    }

    @Override
    public BlockContext getParentContext() {
        return this.parent;
    }
}
