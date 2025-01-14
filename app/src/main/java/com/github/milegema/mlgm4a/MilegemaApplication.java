package com.github.milegema.mlgm4a;

import android.content.Context;

import com.github.milegema.mlgm4a.application.BaseMilegemaApplication;

public class MilegemaApplication extends BaseMilegemaApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static MilegemaApplication getInstance(Context ctx) {
        return (MilegemaApplication) ctx.getApplicationContext();
    }
}
