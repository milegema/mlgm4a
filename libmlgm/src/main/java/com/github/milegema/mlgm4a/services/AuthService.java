package com.github.milegema.mlgm4a.services;

import android.content.Context;

import com.github.milegema.mlgm4a.data.ids.EmailAddress;
import com.github.milegema.mlgm4a.network.inforefs.RemoteURL;
import com.github.milegema.mlgm4a.network.web.AuthScheme;
import com.github.milegema.mlgm4a.network.web.WebMethod;
import com.github.milegema.mlgm4a.network.web.headers.HeaderWWWAuthenticate;

import java.io.IOException;
import java.util.List;

public interface AuthService extends LocalService {

    enum Action {
        SEND, LOGIN
    }


    class AuthParams {

        public Action action;
        public EmailAddress user;
        public RemoteURL url;
        public WebMethod method;
        public AuthScheme mechanism;
        public byte[] credentials;
    }

    class AuthResult {
        public int status; // http-status-code
        public List<HeaderWWWAuthenticate> challenges;
    }

    AuthResult auth(Context ctx, AuthParams params) throws IOException;
}
