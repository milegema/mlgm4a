package com.github.milegema.mlgm4a.contexts;

import com.github.milegema.mlgm4a.network.inforefs.RemoteURL;

public class RootContext extends ContextBase {

    private RemoteURL remote;

    public RootContext() {
    }

    @Override
    public BlockContext getParentContext() {
        return null; // no parent
    }

    public RemoteURL getRemote() {
        return remote;
    }

    public void setRemote(RemoteURL remote) {
        this.remote = remote;
    }
}
