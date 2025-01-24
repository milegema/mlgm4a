package com.github.milegema.mlgm4a.contexts;

public class PasscodeContext extends ContextBase {

    private SceneContext parent;

    public PasscodeContext() {
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
