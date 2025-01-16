package com.github.milegema.mlgm4a;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.github.milegema.mlgm4a.application.MilegemaAppActivity;
import com.github.milegema.mlgm4a.boot.Bootstrap;
import com.github.milegema.mlgm4a.components.ComLifeMonitor;
import com.github.milegema.mlgm4a.contexts.ApplicationContext;
import com.github.milegema.mlgm4a.errors.Errors;
import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.tasks.Promise;
import com.github.milegema.mlgm4a.tasks.PromiseBuilder;
import com.github.milegema.mlgm4a.tasks.Result;

public class MainActivity extends MilegemaAppActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        this.addLife(new ComLifeMonitor());
    }

    @Override
    protected void onStart() {
        super.onStart();
        PromiseBuilder<ApplicationContext> p1 = Promise.init(this, ApplicationContext.class);
        p1.Try(() -> {
            ApplicationContext ac = Bootstrap.boot(this);
            return new Result<>(ac);
        }).Then((res) -> {
            this.startNextActivity();
            return res;
        }).Catch((res) -> {
            Errors.handle(this, res.getError());
            Logs.error(String.valueOf(res.getError()));
            return res;
        }).Finally((res) -> {
            return res;
        }).start();
    }

    private void startNextActivity() {
        Intent i = new Intent(this, DeveloperActivity.class);
        this.startActivity(i);
    }
}
