package com.github.milegema.mlgm4a;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.github.milegema.mlgm4a.application.BaseMilegemaActivity;
import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.ui.views.UnlockGraphView;

public class UnlockActivity extends BaseMilegemaActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_unlock);

        UnlockGraphView ugv = findViewById(R.id.unlock_graph_view);
        ugv.setOnResultListener((res, res_txt) -> {

            Logs.debug(this + ".onResult: " + res_txt);
        });

    }
}
