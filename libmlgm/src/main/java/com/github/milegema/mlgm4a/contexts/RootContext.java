package com.github.milegema.mlgm4a.contexts;

import com.github.milegema.mlgm4a.network.inforefs.RemoteURL;

public class RootContext extends ContextBase {

    private RemoteURL defaultLocation;

    public RootContext() {
    }

    @Override
    public BlockContext getParentContext() {
        return null; // no parent
    }

    public RemoteURL getDefaultLocation() {
        return defaultLocation;
    }

    public void setDefaultLocation(RemoteURL defaultLocation) {
        this.defaultLocation = defaultLocation;
    }
}
