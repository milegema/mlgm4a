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

        // app
        setupButtonToStartActivity(R.id.button_app_settings, AppSettingsActivity.class);
        setupButtonToStartActivity(R.id.button_login, LoginActivity.class);

        // user
        setupButtonToStartActivity(R.id.button_unlock, UnlockActivity.class);
        setupButtonToStartActivity(R.id.button_set_lock_graph, SetLockGraphActivity.class);
        setupButtonToStartActivity(R.id.button_user_settings, UserSettingsActivity.class);
        setupButtonToStartActivity(R.id.button_account_list, AccountListActivity.class);
        setupButtonToStartActivity(R.id.button_account_detail, AccountDetailActivity.class);
        setupButtonToStartActivity(R.id.button_domain_list, DomainListActivity.class);
        setupButtonToStartActivity(R.id.button_domain_detail, DomainDetailActivity.class);
    }

    private void setupButtonToStartActivity(int res_id, Class<?> activity_class) {
        Intent i = new Intent(this, activity_class);
        findViewById(res_id).setOnClickListener((view) -> {
            this.startActivity(i);
        });
    }
}
