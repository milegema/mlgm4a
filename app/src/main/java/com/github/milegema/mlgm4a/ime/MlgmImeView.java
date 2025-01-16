package com.github.milegema.mlgm4a.ime;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.github.milegema.mlgm4a.AppSettingsActivity;
import com.github.milegema.mlgm4a.R;
import com.github.milegema.mlgm4a.logs.Logs;

public class MlgmImeView extends LinearLayout {

    private MlgmImeService imeService;


    public MlgmImeView(Context context) {
        super(context);
        this.init_layout();
    }

    public MlgmImeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init_layout();
    }

    public MlgmImeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init_layout();
    }

    public MlgmImeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.init_layout();
    }


    private void init_layout() {
        Context ctx = this.getContext();
        View view = LayoutInflater.from(ctx).inflate(R.layout.layout_ime_box, this);

        view.findViewById(R.id.button_input_username).setOnClickListener(this::onClickButtonInputUsername);
        view.findViewById(R.id.button_input_password).setOnClickListener(this::onClickButtonInputPassword);
        view.findViewById(R.id.button_back_space).setOnClickListener(this::onClickButtonBackSpace);
        view.findViewById(R.id.button_settings).setOnClickListener(this::onClickButtonSettings);
    }

    private void onClickButtonInputUsername(View view) {
        Logs.debug("onClickButtonInputUsername");
        InputConnection conn = this.imeService.getCurrentInputConnection();
        conn.commitText("user@mlgm", 1);
    }

    private void onClickButtonInputPassword(View view) {
        Logs.debug("onClickButtonInputPassword");
        InputConnection conn = this.imeService.getCurrentInputConnection();
        conn.commitText("mlgm:password", 1);
    }

    private void onClickButtonBackSpace(View view) {
        InputConnection conn = this.imeService.getCurrentInputConnection();
        // conn.commitText("mlgm:password", 1);

        KeyEvent event = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL);
        conn.sendKeyEvent(event);
    }


    private void onClickButtonSettings(View view) {

        /*
        final Context ctx = this.getContext();
        AlertDialog.Builder ab = new AlertDialog.Builder(ctx);
        ab.setTitle("Open").setMessage("settings?");
        ab.setNegativeButton("Cancel", (x, y) -> {
        });
        ab.setPositiveButton("Yes", (x, y) -> {
        });
        ab.show();

         */

        this.showSettingsUI();
    }

    private void showSettingsUI() {
        Context ctx = this.getContext();
        Intent i = new Intent(ctx, AppSettingsActivity.class);
        ctx.startActivity(i);
    }


    public MlgmImeService getImeService() {
        return imeService;
    }

    public void setImeService(MlgmImeService imeService) {
        this.imeService = imeService;
    }
}
