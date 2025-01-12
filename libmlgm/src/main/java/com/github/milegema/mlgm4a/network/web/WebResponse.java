package com.github.milegema.mlgm4a.network.web;

import java.net.URL;

public final class WebResponse {

    private final WebStatus status;
    private final WebHeaderFields header;
    private WebRequest request;
    private WebEntity entity;
    private URL location;

    public WebResponse() {
        this.status = new WebStatus();
        this.header = new WebHeaderFields();
    }


    public WebHeaderFields getHeader() {
        return header;
    }

    public WebStatus getStatus() {
        return status;
    }


    public WebRequest getRequest() {
        return request;
    }

    public void setRequest(WebRequest request) {
        this.request = request;
    }

    public URL getLocation() {
        return location;
    }

    public void setLocation(URL location) {
        this.location = location;
    }

    public WebEntity getEntity() {
        return entity;
    }

    public void setEntity(WebEntity entity) {
        this.entity = entity;
    }
}
