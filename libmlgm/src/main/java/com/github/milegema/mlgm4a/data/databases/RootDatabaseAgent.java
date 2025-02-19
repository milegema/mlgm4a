package com.github.milegema.mlgm4a.data.databases;

import com.github.milegema.mlgm4a.contexts.ContextAgent;
import com.github.milegema.mlgm4a.contexts.RootContext;

public class RootDatabaseAgent implements RootDBA {

    private ContextAgent ca;


    public RootDatabaseAgent() {
    }

    public ContextAgent getCa() {
        return ca;
    }

    public void setCa(ContextAgent ca) {
        this.ca = ca;
    }

    @Override
    public DB db(DB current) {
        if (current != null) {
            return current;
        }
        RootContext root = this.ca.getContextHolder().getRoot();
        return root.getRepository().tables().db();
    }
}
