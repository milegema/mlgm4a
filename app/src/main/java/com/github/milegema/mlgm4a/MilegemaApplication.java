package com.github.milegema.mlgm4a;

import android.content.Context;

import com.github.milegema.mlgm4a.application.BaseMilegemaApplication;
import com.github.milegema.mlgm4a.config.MlgmAppCustomizer;
import com.github.milegema.mlgm4a.configurations.Configuration;

public class MilegemaApplication extends BaseMilegemaApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static MilegemaApplication getInstance(Context ctx) {
        return (MilegemaApplication) ctx.getApplicationContext();
    }


    @Override
    public void customize(Configuration configuration) {
        super.customize(configuration);
        (new MlgmAppCustomizer()).customize(configuration);
    }
}
