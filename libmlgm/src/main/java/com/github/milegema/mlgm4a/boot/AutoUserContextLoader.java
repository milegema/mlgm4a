package com.github.milegema.mlgm4a.boot;

import com.github.milegema.mlgm4a.components.BootOrder;
import com.github.milegema.mlgm4a.components.ComLife;
import com.github.milegema.mlgm4a.components.ComLifecycle;
import com.github.milegema.mlgm4a.contexts.ContextAgent;
import com.github.milegema.mlgm4a.contexts.ContextFactory;
import com.github.milegema.mlgm4a.contexts.ContextHolder;
import com.github.milegema.mlgm4a.contexts.RootContext;
import com.github.milegema.mlgm4a.contexts.UserContext;
import com.github.milegema.mlgm4a.network.inforefs.RemoteURL;

public class AutoUserContextLoader implements ComLifecycle {

    private ContextAgent contextAgent;


    public AutoUserContextLoader() {
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
        cl.setOrder(BootOrder.user_context);
        cl.setOnCreate(this::onCreate);
        return cl;
    }

    public void onCreate() {
        ContextHolder ch = this.contextAgent.getContextHolder();
        UserContext uc = ch.getUser();
        if (uc == null) {
            uc = ContextFactory.createUserContext(ch.getRoot());
            ch.setUser(uc);
        }
        uc.setUserID(null);
    }

}
