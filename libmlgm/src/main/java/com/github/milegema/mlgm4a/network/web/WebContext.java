package com.github.milegema.mlgm4a.network.web;

public class WebContext {

    private String baseURL;
    private WebClient client;
    private WebClientFactory factory;
    private WebConfiguration configuration;
    private WebFilterChain chain;

    public WebContext() {
    }

    public WebClient getClient() {
        return client;
    }

    public void setClient(WebClient client) {
        this.client = client;
    }

    public WebClientFactory getFactory() {
        return factory;
    }

    public void setFactory(WebClientFactory factory) {
        this.factory = factory;
    }

    public WebConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(WebConfiguration configuration) {
        this.configuration = configuration;
    }

    public WebFilterChain getChain() {
        return chain;
    }

    public void setChain(WebFilterChain chain) {
        this.chain = chain;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }
}
