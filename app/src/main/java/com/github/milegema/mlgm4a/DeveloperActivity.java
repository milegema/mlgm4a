package com.github.milegema.mlgm4a;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.github.milegema.mlgm4a.application.MilegemaAppActivity;

public class DeveloperActivity extends MilegemaAppActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_developer);

        setupButtonToStartActivity(R.id.button_unlock, UnlockActivity.class);
    }

    private void setupButtonToStartActivity(int res_id, Class<?> activity_class) {
        Intent i = new Intent(this, activity_class);
        findViewById(res_id).setOnClickListener((view) -> {
            this.startActivity(i);
        });
    }

}
