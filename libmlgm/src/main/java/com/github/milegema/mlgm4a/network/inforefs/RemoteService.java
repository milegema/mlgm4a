package com.github.milegema.mlgm4a.network.inforefs;

import com.github.milegema.mlgm4a.network.web.WebClient;

public class RemoteService {

    private RemoteURL location;
    private String service;
    private String requestContentType;
    private String responseContentType;
    private WebClient client;

    public RemoteService() {
    }

    public RemoteURL getLocation() {
        return location;
    }

    public void setLocation(RemoteURL location) {
        this.location = location;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getRequestContentType() {
        return requestContentType;
    }

    public void setRequestContentType(String requestContentType) {
        this.requestContentType = requestContentType;
    }

    public String getResponseContentType() {
        return responseContentType;
    }

    public void setResponseContentType(String responseContentType) {
        this.responseContentType = responseContentType;
    }

    public WebClient getClient() {
        return client;
    }

    public void setClient(WebClient client) {
        this.client = client;
    }
}
