package com.github.milegema.mlgm4a;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.github.milegema.mlgm4a.application.MilegemaAppActivity;
import com.github.milegema.mlgm4a.data.ids.EmailAddress;
import com.github.milegema.mlgm4a.errors.Errors;
import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.network.api.LoginAPI;
import com.github.milegema.mlgm4a.network.inforefs.RemoteContext;
import com.github.milegema.mlgm4a.network.inforefs.RemoteURL;
import com.github.milegema.mlgm4a.network.inforefs.Remotes;
import com.github.milegema.mlgm4a.tasks.Promise;
import com.github.milegema.mlgm4a.tasks.Result;
import com.github.milegema.mlgm4a.ui.views.VerificationCodeInputView;

import java.io.IOException;

public class LoginActivity extends MilegemaAppActivity {

    private VerificationCodeInputView mVCodeInput;
    private EditText mEditTextEmail;
    private EditText mEditTextRemoteURL;
    private Button mButtonLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        mVCodeInput = findViewById(R.id.input_verification_code);
        mEditTextEmail = findViewById(R.id.edit_text_email_address);
        mEditTextRemoteURL = findViewById(R.id.edit_text_remote_url);
        mButtonLogin = findViewById(R.id.button_login);

        mButtonLogin.setOnClickListener(this::handleClickLogin);
        mVCodeInput.setOnClickSendListener(this::handleClickSend);
        mVCodeInput.setCode("123456");
    }

    private static class MyRequestContext {
        String email;
        String remoteURL;
        String verificationCode;
    }


    private void handleClickSend(View v) {
        final VerificationCodeInputView vc_input = this.mVCodeInput;
        final MyRequestContext rc = new MyRequestContext();
        rc.email = this.mEditTextEmail.getText().toString();
        rc.remoteURL = this.mEditTextRemoteURL.getText().toString();
        rc.verificationCode = null;
        vc_input.startTimer();
        Promise.init(this, Long.class).Try(() -> {
            // do send
            this.requestSendVerificationCode(rc);
            return new Result<>(Long.valueOf(0));
        }).Then((res) -> {
            return res;
        }).Catch((res) -> {
            vc_input.reset();
            return res;
        }).start();
    }

    private void handleClickLogin(View v) {
        final VerificationCodeInputView vc_input = this.mVCodeInput;
        final MyRequestContext rc = new MyRequestContext();
        rc.email = this.mEditTextEmail.getText().toString();
        rc.remoteURL = this.mEditTextRemoteURL.getText().toString();
        rc.verificationCode = vc_input.getCode();

        Promise.init(this, Long.class).Try(() -> {
            // do send
            this.requestLogin(rc);
            return new Result<>(Long.valueOf(0));
        }).Then((res) -> {
            Logs.info("OK");
            return res;
        }).Catch((res) -> {
            Errors.handle(this, res.getError());
            return res;
        }).start();
    }


    private void requestSendVerificationCode(MyRequestContext rc) throws IOException {
        //background

        RemoteContext remote_ctx = Remotes.getRemoteContext(this);
        LoginAPI api = new LoginAPI(remote_ctx);
        LoginAPI.Request req = new LoginAPI.Request();

        req.email = new EmailAddress(rc.email);
        req.code = null;

        if (hasRemoteURL(rc)) {
            req.url = new RemoteURL(rc.remoteURL);
        }

        api.invoke(req);
    }

    private void requestLogin(MyRequestContext rc) throws IOException {
        //background

        RemoteContext remote_ctx = Remotes.getRemoteContext(this);
        LoginAPI api = new LoginAPI(remote_ctx);
        LoginAPI.Request req = new LoginAPI.Request();

        req.email = new EmailAddress(rc.email);
        req.code = rc.verificationCode;

        if (hasRemoteURL(rc)) {
            req.url = new RemoteURL(rc.remoteURL);
        }

        api.invoke(req);
    }

    private static boolean hasRemoteURL(MyRequestContext rc) {
        if (rc == null) {
            return false;
        }
        String url = rc.remoteURL;
        if (url == null) {
            return false;
        }
        return url.startsWith("http:") || url.startsWith("https:");
    }

}
