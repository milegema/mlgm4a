package com.github.milegema.mlgm4a.network.api;

import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.data.properties.PropertyTableLS;
import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.network.inforefs.RemoteAPI;
import com.github.milegema.mlgm4a.network.inforefs.RemoteContext;
import com.github.milegema.mlgm4a.network.inforefs.RemoteServices;
import com.github.milegema.mlgm4a.network.web.AuthScheme;
import com.github.milegema.mlgm4a.network.web.HTTP;
import com.github.milegema.mlgm4a.network.web.WebEntity;
import com.github.milegema.mlgm4a.network.web.WebMethod;
import com.github.milegema.mlgm4a.network.web.WebRequest;
import com.github.milegema.mlgm4a.network.web.WebResponse;
import com.github.milegema.mlgm4a.network.web.headers.HeaderWWWAuthenticate;
import com.github.milegema.mlgm4a.utils.Base64;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class AuthAPI extends RemoteAPI<AuthAPI.Request, AuthAPI.Response> {

    public AuthAPI(RemoteContext ctx) {
        super(ctx, RemoteServices.AUTH);
    }

    public static class Request {
        public WebMethod method;
        public AuthScheme mechanism;
        public byte[] credentials;
    }

    public static class Response {
        public int status;
        public List<HeaderWWWAuthenticate> challenges;
    }

    @Override
    protected void encode(AuthAPI.Request src, WebRequest dst) {
        byte[] credentials = src.credentials;
        String value = src.mechanism + " " + Base64.encode(credentials);
        dst.getHeader().set(HTTP.header_authorization, value);
        dst.setMethod(src.method);
    }

    @Override
    protected void decode(WebResponse src, AuthAPI.Response dst) {

        WebEntity ent = src.getEntity();
        ByteSlice content = ent.getContent();
        byte[] data = content.toByteArray();
        String str = new String(data, StandardCharsets.UTF_8);
        PropertyTable pt = PropertyTableLS.parse(str);

        dst.status = src.getStatus().getCode();
        dst.challenges = HeaderWWWAuthenticate.list(src.getHeader());

        Logs.debug("AuthAPI.decode:" + pt);
    }


    @Override
    public AuthAPI.Response invoke(AuthAPI.Request req) throws IOException {
        AuthAPI.Response resp = new AuthAPI.Response();
        this.execute(req, resp);
        return resp;
    }
}
