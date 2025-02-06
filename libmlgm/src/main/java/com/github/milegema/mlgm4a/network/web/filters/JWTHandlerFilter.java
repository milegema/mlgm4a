package com.github.milegema.mlgm4a.network.web.filters;


import com.github.milegema.mlgm4a.contexts.ContextAgent;
import com.github.milegema.mlgm4a.contexts.ContextHolder;
import com.github.milegema.mlgm4a.contexts.UserContext;
import com.github.milegema.mlgm4a.network.web.AuthScheme;
import com.github.milegema.mlgm4a.network.web.HTTP;
import com.github.milegema.mlgm4a.network.web.WebFilter;
import com.github.milegema.mlgm4a.network.web.WebFilterChain;
import com.github.milegema.mlgm4a.network.web.WebFilterOrder;
import com.github.milegema.mlgm4a.network.web.WebFilterRegistration;
import com.github.milegema.mlgm4a.network.web.WebFilterRegistry;
import com.github.milegema.mlgm4a.network.web.WebHeaderFields;
import com.github.milegema.mlgm4a.network.web.WebRequest;
import com.github.milegema.mlgm4a.network.web.WebResponse;
import com.github.milegema.mlgm4a.network.web.headers.HeaderWWWAuthenticate;
import com.github.milegema.mlgm4a.security.Token;
import com.github.milegema.mlgm4a.utils.Strings;

import java.io.IOException;
import java.util.List;

public final class JWTHandlerFilter implements WebFilterRegistry, WebFilter {

    private ContextAgent contextAgent;

    public JWTHandlerFilter() {
    }

    public ContextAgent getContextAgent() {
        return contextAgent;
    }

    public void setContextAgent(ContextAgent contextAgent) {
        this.contextAgent = contextAgent;
    }

    @Override
    public WebResponse execute(WebRequest request, WebFilterChain next) throws IOException {
        UserContext uc = null;
        ContextAgent ca = this.contextAgent;
        if (ca != null) {
            ContextHolder ch = ca.getContextHolder();
            if (ch != null) {
                uc = ch.getUser();
            }
        }
        this.prepareRequest(uc, request);
        WebResponse resp = next.execute(request);
        this.handleResponse(uc, resp);
        return resp;
    }

    private void prepareRequest(UserContext uc, WebRequest req) {
        if (uc == null || req == null) {
            return;
        }
        Token token = uc.getToken();
        if (token == null) {
            return;
        }
        String name = HTTP.header_x_token;
        String value = token.toString();
        req.getHeader().set(name, value);
    }

    private void handleResponse(UserContext uc, WebResponse resp) {
        if (uc == null || resp == null) {
            return;
        }
        WebHeaderFields header = resp.getHeader();
        String x_set_token = header.get(HTTP.header_x_set_token);
        List<HeaderWWWAuthenticate> www_auth_list = HeaderWWWAuthenticate.list(header);
        Token token = null;

        // for 'x-token'
        if (x_set_token != null) {
            token = new Token(x_set_token);
        }

        // for 'www-auth:JWT'
        // if (www_auth_list != null) {
        final AuthScheme want_scheme = AuthScheme.JWT;
        for (HeaderWWWAuthenticate auth : www_auth_list) {
            final AuthScheme have_scheme = auth.getScheme();
            if (want_scheme.equals(have_scheme)) {
                String token_str = auth.getChallenges().get("token");
                if (token_str == null) {
                    continue;
                }
                token = new Token(token_str);
            }
        }
        // }

        // hold result
        if (token == null) {
            return;
        }
        uc.setToken(token);
    }

    @Override
    public WebFilterRegistration registration() {
        WebFilterRegistration reg = new WebFilterRegistration();
        reg.setFilter(this);
        reg.setEnabled(true);
        reg.setOrder(WebFilterOrder.JWT);
        return reg;
    }
}
