package com.github.milegema.mlgm4a.libmlgm;

import com.github.milegema.mlgm4a.components.ComLife;
import com.github.milegema.mlgm4a.components.ComLifecycle;
import com.github.milegema.mlgm4a.contexts.ApplicationContext;
import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.network.web.WebClient;

public class MyExampleCom implements ComLifecycle {

    private ApplicationContext applicationContext;
    private String attrLabel;
    private int attrLimit;
    private WebClient client;

    public MyExampleCom() {
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public String getAttrLabel() {
        return attrLabel;
    }

    public void setAttrLabel(String attrLabel) {
        this.attrLabel = attrLabel;
    }

    public int getAttrLimit() {
        return attrLimit;
    }

    public void setAttrLimit(int attrLimit) {
        this.attrLimit = attrLimit;
    }

    public WebClient getClient() {
        return client;
    }

    public void setClient(WebClient client) {
        this.client = client;
    }

    @Override
    public ComLife life() {
        ComLife cl = new ComLife();
        cl.setOnCreate(() -> {
            Logs.info(this + ".on_create()");
        });
        cl.setOnStart(() -> {
            Logs.info(this + ".on_start()");
            // if (this.attrLimit > 0) {
            // throw new RuntimeException("this.attrLimit > 0");
            // }
        });
        cl.setOnResume(() -> {
            Logs.info(this + ".on_resume()");
        });

        cl.setLoop(() -> {
            Logs.info(this + ".loop()");
        });

        cl.setOnPause(() -> {
            Logs.info(this + ".on_pause()");
        });
        cl.setOnStop(() -> {
            Logs.info(this + ".on_stop()");
        });
        cl.setOnDestroy(() -> {
            Logs.info(this + ".on_destroy()");
        });
        return cl;
    }
}
