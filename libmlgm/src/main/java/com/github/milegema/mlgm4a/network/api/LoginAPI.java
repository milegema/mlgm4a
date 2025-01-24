package com.github.milegema.mlgm4a.network.api;

import com.github.milegema.mlgm4a.data.ids.EmailAddress;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.data.properties.PropertyTableLS;
import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.network.inforefs.RemoteAPI;
import com.github.milegema.mlgm4a.network.inforefs.RemoteContext;
import com.github.milegema.mlgm4a.network.inforefs.RemoteServices;
import com.github.milegema.mlgm4a.network.inforefs.RemoteURL;
import com.github.milegema.mlgm4a.network.web.WebEntity;
import com.github.milegema.mlgm4a.network.web.WebRequest;
import com.github.milegema.mlgm4a.network.web.WebResponse;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class LoginAPI extends RemoteAPI<LoginAPI.Request, LoginAPI.Response> {

    public LoginAPI(RemoteContext ctx) {
        super(ctx, RemoteServices.SIGN_UP_IN);
    }

    public static class Request {
        public EmailAddress email; // used as user-id
        public String code;   // verification-code
        public RemoteURL url; // (optional)
    }

    public static class Response {
    }


    @Override
    protected void encode(LoginAPI.Request src, WebRequest dst) {

    }

    @Override
    protected void decode(WebResponse src, LoginAPI.Response dst) {


        WebEntity ent = src.getEntity();
        ByteSlice content = ent.getContent();
        byte[] data = content.toByteArray();
        String str = new String(data, StandardCharsets.UTF_8);
        PropertyTable pt = PropertyTableLS.parse(str);

        Logs.debug("" + pt);

    }

    @Override
    public LoginAPI.Response invoke(LoginAPI.Request req) throws IOException {
        LoginAPI.Response resp = new LoginAPI.Response();
        this.execute(req, resp);
        return resp;
    }
}
