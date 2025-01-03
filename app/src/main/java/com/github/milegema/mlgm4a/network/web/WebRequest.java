package com.github.milegema.mlgm4a.network.web;

import com.github.milegema.mlgm4a.utils.Attributes;

public final  class WebRequest {

    private WebContext context;
    private WebMethod method;
    private String url;
    private final WebHeaderFields header;
    private WebEntity entity;
    private Attributes attributes;

    public WebRequest() {
        this.attributes = new Attributes();
        this.header = new WebHeaderFields();
        this.method = WebMethod.GET;
        this.url = "/";
    }

    public WebRequest(WebRequest src) {
        if (src == null) {
            this.attributes = new Attributes();
            this.header = new WebHeaderFields();
        } else {
            this.attributes = new Attributes(src.attributes);
            this.header = new WebHeaderFields(src.header);
            this.method = src.method;
            this.url = src.url;
            this.entity = src.entity;
        }
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public WebMethod getMethod() {
        return method;
    }

    public void setMethod(WebMethod method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public WebEntity getEntity() {
        return entity;
    }

    public void setEntity(WebEntity entity) {
        this.entity = entity;
    }

    public WebContext getContext() {
        return context;
    }

    public void setContext(WebContext context) {
        this.context = context;
    }

    public WebHeaderFields getHeader() {
        return header;
    }
}
