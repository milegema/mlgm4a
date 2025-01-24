package com.github.milegema.mlgm4a.network.inforefs;

import com.github.milegema.mlgm4a.network.web.WebClient;

public final class RemoteContext {

    private RemoteURL location;
    private WebClient client;

    public RemoteContext() {
    }

    public RemoteURL getLocation() {
        return location;
    }

    public void setLocation(RemoteURL location) {
        this.location = location;
    }

    public WebClient getClient() {
        return client;
    }

    public void setClient(WebClient client) {
        this.client = client;
    }
}
