package com.github.milegema.mlgm4a.boot;

import android.content.Context;

import com.github.milegema.mlgm4a.application.BaseMilegemaApplication;
import com.github.milegema.mlgm4a.application.MLGM;
import com.github.milegema.mlgm4a.configurations.Configuration;
import com.github.milegema.mlgm4a.configurations.Customizer;
import com.github.milegema.mlgm4a.contexts.ApplicationContext;
import com.github.milegema.mlgm4a.contexts.ContextHolder;
import com.github.milegema.mlgm4a.contexts.ac.AndroidContextAgent;

public final class Bootstrap {

    private Bootstrap() {
    }

    public static ApplicationContext boot(Context ctx) {
        Configuration config = new Configuration();
        MLGM app = (MLGM) ctx.getApplicationContext();
        ContextHolder holder = app.getContextHolder();

        config.setAndroid(ctx);
        config.getCustomizers().add((Customizer) app);

        AndroidContextAgent.bind(holder, config.getAttributes());

        ApplicationContext ac = config.getFactory().create(config);
        holder.setApplicationContext(ac);
        return ac;
    }
}
