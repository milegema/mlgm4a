package com.github.milegema.mlgm4a;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.github.milegema.mlgm4a.application.BaseMilegemaActivity;
import com.github.milegema.mlgm4a.components.ComLifeMonitor;

public class MainActivity extends BaseMilegemaActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        this.addLife(new ComLifeMonitor());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent i = new Intent(this, DeveloperActivity.class);
        this.startActivity(i);
    }
}
