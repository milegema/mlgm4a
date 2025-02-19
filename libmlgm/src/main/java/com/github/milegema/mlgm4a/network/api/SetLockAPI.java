package com.github.milegema.mlgm4a.network.api;

import com.github.milegema.mlgm4a.data.types.PIN;
import com.github.milegema.mlgm4a.network.inforefs.RemoteAPI;
import com.github.milegema.mlgm4a.network.inforefs.RemoteContext;
import com.github.milegema.mlgm4a.network.inforefs.RemoteServices;
import com.github.milegema.mlgm4a.network.web.WebRequest;
import com.github.milegema.mlgm4a.network.web.WebResponse;

import java.io.IOException;

public final class SetLockAPI extends RemoteAPI<SetLockAPI.Request, SetLockAPI.Response> {

    public SetLockAPI(RemoteContext ctx) {
        super(ctx, RemoteServices.LOCK);
    }

    public static class Request {

        public PIN.Digest pin;

    }

    public static class Response {
    }


    @Override
    protected void encode(Request src, WebRequest dst) {

    }

    @Override
    protected void decode(WebResponse src, Response dst) {

    }

    @Override
    public Response invoke(Request req) throws IOException {
        Response resp = new Response();
        this.execute(req, resp);
        return resp;
    }
}
