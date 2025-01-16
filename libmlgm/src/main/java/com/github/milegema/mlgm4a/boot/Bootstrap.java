package com.github.milegema.mlgm4a.boot;

import android.content.Context;

import com.github.milegema.mlgm4a.application.BaseMilegemaApplication;
import com.github.milegema.mlgm4a.configurations.Configuration;
import com.github.milegema.mlgm4a.contexts.ApplicationContext;
import com.github.milegema.mlgm4a.contexts.ContextHolder;

public final class Bootstrap {

    private Bootstrap() {
    }

    public static ApplicationContext boot(Context ctx) {
        Configuration config = new Configuration();
        BaseMilegemaApplication app = (BaseMilegemaApplication) ctx.getApplicationContext();
        ContextHolder holder = app.getContextHolder();

        config.setAndroid(ctx);
        config.getCustomizers().add(app);

        ApplicationContext ac = config.getFactory().create(config);
        holder.setApplicationContext(ac);
        return ac;
    }
}
