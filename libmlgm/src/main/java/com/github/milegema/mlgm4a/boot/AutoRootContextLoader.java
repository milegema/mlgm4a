package com.github.milegema.mlgm4a.boot;

import com.github.milegema.mlgm4a.components.BootOrder;
import com.github.milegema.mlgm4a.components.ComLife;
import com.github.milegema.mlgm4a.components.ComLifecycle;
import com.github.milegema.mlgm4a.contexts.ContextAgent;
import com.github.milegema.mlgm4a.contexts.ContextFactory;
import com.github.milegema.mlgm4a.contexts.ContextHolder;
import com.github.milegema.mlgm4a.contexts.RootContext;
import com.github.milegema.mlgm4a.network.inforefs.RemoteURL;

public class AutoRootContextLoader implements ComLifecycle {

    private ContextAgent contextAgent;

    private String defaultRemoteURL;


    public AutoRootContextLoader() {
    }

    public String getDefaultRemoteURL() {
        return defaultRemoteURL;
    }

    public void setDefaultRemoteURL(String defaultRemoteURL) {
        this.defaultRemoteURL = defaultRemoteURL;
    }

    public ContextAgent getContextAgent() {
        return contextAgent;
    }

    public void setContextAgent(ContextAgent contextAgent) {
        this.contextAgent = contextAgent;
    }

    @Override
    public ComLife life() {
        ComLife cl = new ComLife();
        cl.setOrder(BootOrder.root_context);
        cl.setOnCreate(this::onCreate);
        return cl;
    }

    public void onCreate() {
        ContextHolder ch = this.contextAgent.getContextHolder();
        RootContext root = ch.getRoot();
        if (root == null) {
            root = ContextFactory.createRootContext();
            ch.setRoot(root);
        }
        root.setRemote(new RemoteURL(this.defaultRemoteURL));
    }

}
