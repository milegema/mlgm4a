package com.github.milegema.mlgm4a.network.api;

import com.github.milegema.mlgm4a.network.inforefs.RemoteAPI;
import com.github.milegema.mlgm4a.network.inforefs.RemoteContext;
import com.github.milegema.mlgm4a.network.inforefs.RemoteServices;
import com.github.milegema.mlgm4a.network.web.WebRequest;
import com.github.milegema.mlgm4a.network.web.WebResponse;

import java.io.IOException;

public final class ExampleAPI extends RemoteAPI<ExampleAPI.Request, ExampleAPI.Response> {

    public ExampleAPI(RemoteContext ctx) {
        super(ctx, RemoteServices.EXAMPLE);
    }

    public static class Request {
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
