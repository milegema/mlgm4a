package com.github.milegema.mlgm4a;

import android.app.Application;
import android.content.Context;

public class MilegemaApplication extends Application {


    public static MilegemaApplication getInstance(Context ctx) {
        return (MilegemaApplication) ctx.getApplicationContext();
    }

}
