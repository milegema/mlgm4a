package com.github.milegema.mlgm4a;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.github.milegema.mlgm4a.application.MilegemaAppActivity;

import com.github.milegema.mlgm4a.errors.Errors;
import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.network.api.AuthAPI;
import com.github.milegema.mlgm4a.network.api.SetLockAPI;
import com.github.milegema.mlgm4a.network.inforefs.RemoteContext;
import com.github.milegema.mlgm4a.network.inforefs.Remotes;
import com.github.milegema.mlgm4a.tasks.Promise;
import com.github.milegema.mlgm4a.tasks.Result;
import com.github.milegema.mlgm4a.ui.views.UnlockGraphView;

import java.util.Arrays;


public class SetLockGraphActivity extends MilegemaAppActivity {

    private UnlockGraphView mGraphView;
    private TextView mTextTip;

    private int[] mResult1;
    private int[] mResult2;
    private String mTipContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_set_lock_graph);
        mGraphView = findViewById(R.id.unlock_graph_view);
        mTextTip = findViewById(R.id.text_tip);

        mGraphView.setOnResultListener(this::onResult);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.reset();
    }

    private void displayTip() {
        this.mTextTip.setText(this.mTipContent);
    }

    private void displayDifferentMessage() {

        String title = "different";
        String msg = "redo input";

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(title).setMessage(msg);
        adb.setNegativeButton(R.string.ok, (x, y) -> {
        });
        adb.show();
    }

    private void reset() {
        this.mResult1 = null;
        this.mResult2 = null;
        this.mTipContent = "input new graph";
        this.displayTip();
    }

    private void commit() {
        String code = Arrays.toString(this.mResult1);
        Logs.debug("todo: commit pin: " + code);

        RemoteContext remote_ctx = Remotes.getRemoteContext(this);
        SetLockAPI api = new SetLockAPI(remote_ctx);
        SetLockAPI.Request request = new SetLockAPI.Request();

        Promise.init(this, SetLockAPI.Response.class).Try(() -> {
            SetLockAPI.Response resp = api.invoke(request);
            return new Result<>(resp);
        }).Then((res) -> {
            Logs.debug("done");
            return res;
        }).Catch((res) -> {
            Errors.handle(this, res.getError());
            return res;
        }).Finally((res) -> {
            return res;
        }).start();
    }

    private void onResult(int[] array, String str) {

        Logs.debug(this + ".onResult: " + str);

        if (!checkResult(array, str)) {
            return;
        }

        int[] r1 = this.mResult1;
        int[] r2 = this.mResult2;
        if (r1 == null && r2 == null) {
            this.mResult1 = array;
            this.mTipContent = "input again";
            this.displayTip();
            return;
        }
        r2 = array;
        this.mResult2 = array;

        if (!Arrays.equals(r1, r2)) {
            this.reset();
            this.displayDifferentMessage();
            return;
        }

        this.mTipContent = "done";
        this.displayTip();
        this.commit();
    }

    private boolean checkResult(int[] array, String str) {

        if (array == null || str == null) {
            return false;
        }

        final int want_len = 9;
        if (array.length != want_len || str.length() != want_len) {
            String msg = "need full-size graph: 9-digits";
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
