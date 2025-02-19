package com.github.milegema.mlgm4a;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.github.milegema.mlgm4a.application.MilegemaAppActivity;
import com.github.milegema.mlgm4a.contexts.ContextFactory;
import com.github.milegema.mlgm4a.contexts.ContextHolder;
import com.github.milegema.mlgm4a.contexts.RootContext;
import com.github.milegema.mlgm4a.contexts.UserContext;
import com.github.milegema.mlgm4a.data.ids.EmailAddress;
import com.github.milegema.mlgm4a.errors.Errors;
import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.network.inforefs.RemoteURL;
import com.github.milegema.mlgm4a.network.web.AuthScheme;
import com.github.milegema.mlgm4a.network.web.WebMethod;
import com.github.milegema.mlgm4a.network.web.headers.HeaderWWWAuthenticate;
import com.github.milegema.mlgm4a.security.Token;
import com.github.milegema.mlgm4a.classes.services.LocalServices;
import com.github.milegema.mlgm4a.classes.authx.AuthService;
import com.github.milegema.mlgm4a.tasks.Promise;
import com.github.milegema.mlgm4a.tasks.Result;
import com.github.milegema.mlgm4a.ui.views.VerificationCodeInputView;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class LoginActivity extends MilegemaAppActivity {

    private VerificationCodeInputView mVCodeInput;
    private EditText mEditTextEmail;
    private EditText mEditTextRemoteURL;
    private Button mButtonLogin;


    // data

    private ContextHolder mContextHolder;
    private UserContext mUserContext;
    private Token mEmailToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        mVCodeInput = findViewById(R.id.input_verification_code);
        mEditTextEmail = findViewById(R.id.edit_text_email_address);
        mEditTextRemoteURL = findViewById(R.id.edit_text_remote_url);
        mButtonLogin = findViewById(R.id.button_login);

        mButtonLogin.setOnClickListener(this::handleClickLogin);
        mVCodeInput.setOnClickSendListener(this::handleClickSendMail);
        mVCodeInput.setCode("123456");

        mContextHolder = ContextHolder.getInstance(this);
    }

    private static class MyRequestContext {
        RemoteURL url;
        EmailAddress email;
        String code; // verification-code
        Token token; // JWT

        AuthService.Action action;
        AuthService.AuthResult result;
    }

    private RemoteURL getRemoteURL() {
        Editable edit = this.mEditTextRemoteURL.getText();
        String url = String.valueOf(edit);
        if (!hasRemoteURL(url)) {
            return this.mContextHolder.getRoot().getDefaultLocation();
        }
        return new RemoteURL(url);
    }

    private EmailAddress getUserEmail() {
        Editable text = this.mEditTextEmail.getText();
        return new EmailAddress(String.valueOf(text));
    }

    private String getVerificationCode() {
        return this.mVCodeInput.getCode();
    }


    private static Token getEmailToken(List<HeaderWWWAuthenticate> challenges) {
        if (challenges == null) {
            return null;
        }
        for (HeaderWWWAuthenticate header : challenges) {
            if (!AuthScheme.Email.equals(header.getScheme())) {
                continue;
            }
            String token = header.getChallenges().get("token");
            if (token == null) {
                continue;
            }
            return new Token(token);
        }
        return null;
    }


    private void handleClickSendMail(View v) {
        final VerificationCodeInputView vc_input = this.mVCodeInput;
        final MyRequestContext rc = new MyRequestContext();

        rc.email = this.getUserEmail();
        rc.url = this.getRemoteURL();
        rc.code = "";
        rc.token = null;
        rc.action = AuthService.Action.SEND;

        vc_input.startTimer();
        Promise.init(this, AuthService.AuthResult.class).Try(() -> {
            // do send
            this.execute(rc);
            return new Result<>(rc.result);
        }).Then((res) -> {
            AuthService.AuthResult ar = res.getValue();
            this.mEmailToken = getEmailToken(ar.challenges);
            return res;
        }).Catch((res) -> {
            vc_input.reset();
            Errors.handle(this, res.getError());
            return res;
        }).start();
    }

    private void handleClickLogin(View v) {

        final MyRequestContext rc = new MyRequestContext();
        rc.email = this.getUserEmail();
        rc.url = this.getRemoteURL();
        rc.code = this.getVerificationCode();
        rc.token = this.mEmailToken;
        rc.action = AuthService.Action.LOGIN;

        Promise.init(this, AuthService.AuthResult.class).Try(() -> {
            // do send
            this.execute(rc);
            return new Result<>(rc.result);
        }).Then((res) -> {
            Logs.info("OK");
            return res;
        }).Catch((res) -> {
            int flags = Errors.FLAG_ALERT | Errors.FLAG_LOG;
            Errors.handle(this, res.getError(), flags);
            return res;
        }).start();
    }

    private static byte[] makeCredentials(MyRequestContext rc) {
        StringBuilder builder = new StringBuilder();
        Object[] src = {rc.email, rc.code, rc.token};
        for (int i = 0; i < src.length; i++) {
            Object item = src[i];
            if (i > 0) {
                builder.append(':');
            }
            if (item == null) {
                continue;
            }
            builder.append(item);
        }
        String text = builder.toString();
        return text.getBytes(StandardCharsets.UTF_8);
    }

    private void execute(MyRequestContext rc) throws IOException {
        //background

        AuthService.AuthParams lp = new AuthService.AuthParams();
        lp.method = WebMethod.POST;
        lp.mechanism = AuthScheme.Email;
        lp.user = rc.email;
        lp.credentials = makeCredentials(rc);
        lp.url = rc.url;
        lp.action = rc.action;

        AuthService ser = LocalServices.getService(this, AuthService.class);
        rc.result = ser.auth(this, lp);
    }


    private static boolean hasRemoteURL(String url) {
        if (url == null) {
            return false;
        }
        return url.startsWith("http:") || url.startsWith("https:");
    }

    @Override
    protected void onStart() {
        super.onStart();
        RootContext rc = this.mContextHolder.getRoot();
        UserContext uc = ContextFactory.createUserContext(rc);
        this.mUserContext = uc;
        this.mContextHolder.setUser(uc);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.mContextHolder.setUser(this.mUserContext);
    }
}
