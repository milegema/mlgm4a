package com.github.milegema.mlgm4a.data.databases;

import com.github.milegema.mlgm4a.contexts.ContextAgent;
import com.github.milegema.mlgm4a.contexts.RootContext;
import com.github.milegema.mlgm4a.contexts.UserContext;

public class UserDatabaseAgent implements UserDBA {

    private ContextAgent ca;

    public UserDatabaseAgent() {
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
        UserContext user = this.ca.getContextHolder().getUser();
        return user.getRepository().tables().db();
    }
}
