package com.github.milegema.mlgm4a.network.inforefs;

import java.net.URL;

public final class RemoteService {

    private RemoteContext context;
    private URL url;
    private String service;


    public RemoteService() {
    }

    public RemoteContext getContext() {
        return context;
    }

    public void setContext(RemoteContext context) {
        this.context = context;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

}
