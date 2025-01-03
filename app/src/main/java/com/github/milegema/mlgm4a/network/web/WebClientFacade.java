package com.github.milegema.mlgm4a.network.web;

import com.github.milegema.mlgm4a.components.ComponentManager;
import com.github.milegema.mlgm4a.contexts.ApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebClientFacade implements WebClient {

    private WebClient innerWebClient;
    private ApplicationContext applicationContext;

    public WebClientFacade() {
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private WebClient getInner() {
        WebClient i = this.innerWebClient;
        if (i == null) {
            i = this.loadInner();
            this.innerWebClient = i;
        }
        return i;
    }

    private WebClient loadInner() {
        WebClientFactory factory = new DefaultWebClientFactory();
        WebConfiguration cfg = new WebConfiguration();
        cfg.setFilters(this.loadFilters());
        return factory.create(cfg);
    }

    private WebFilterRegistration[] loadFilters() {
        List<WebFilterRegistration> dst = new ArrayList<>();
        ComponentManager cm = this.applicationContext.components();
        String[] ids = cm.listIds();
        for (String id : ids) {
            Object item = cm.findById(id, Object.class);
            if (item instanceof WebFilterRegistry) {
                WebFilterRegistry r1 = (WebFilterRegistry) item;
                WebFilterRegistration r2 = r1.registration();
                dst.add(r2);
            } else if (item instanceof WebFilterRegistration) {
                WebFilterRegistration r1 = (WebFilterRegistration) item;
                dst.add(r1);
            }
        }
        return dst.toArray(new WebFilterRegistration[0]);
    }

    @Override
    public WebResponse execute(WebRequest request) throws IOException {
        return this.getInner().execute(request);
    }
}
