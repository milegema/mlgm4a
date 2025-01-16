package com.github.milegema.mlgm4a;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.github.milegema.mlgm4a.application.MilegemaUserActivity;

public class UserSettingsActivity extends MilegemaUserActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.layout_example);
    }

}
