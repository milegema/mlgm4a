package com.github.milegema.mlgm4a.network.inforefs;

import android.content.Context;

import com.github.milegema.mlgm4a.components.ComponentManager;
import com.github.milegema.mlgm4a.contexts.ContextHolder;
import com.github.milegema.mlgm4a.contexts.RootContext;
import com.github.milegema.mlgm4a.contexts.UserContext;
import com.github.milegema.mlgm4a.network.web.WebClient;

public final class Remotes {

    private Remotes() {
    }

    public static RemoteContext getRemoteContext(Context ctx) {

        ContextHolder ch = ContextHolder.getInstance(ctx);
        ComponentManager com_man = ch.getApplicationContext().components();
        WebClient client = com_man.find(WebClient.class);

        RootContext root_ctx = ch.getRoot();
        UserContext user_ctx = ch.getUser();
        RemoteURL location = root_ctx.getRemote();
        if (user_ctx != null) {
            RemoteURL url = user_ctx.getRemote();
            if (url != null) {
                location = url;
            }
        }

        RemoteContext rc = new RemoteContext();
        rc.setClient(client);
        rc.setLocation(location);
        return rc;
    }
}
