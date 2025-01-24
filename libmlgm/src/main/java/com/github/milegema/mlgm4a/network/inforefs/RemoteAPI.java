package com.github.milegema.mlgm4a.network.inforefs;

import com.github.milegema.mlgm4a.network.web.WebClient;
import com.github.milegema.mlgm4a.network.web.WebMethod;
import com.github.milegema.mlgm4a.network.web.WebRequest;
import com.github.milegema.mlgm4a.network.web.WebResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class RemoteAPI<REQ, RESP> implements API<REQ, RESP> {

    private RemoteService service;

    public RemoteAPI(RemoteContext ctx, String service_name) {
        RemoteService rs = new RemoteService();
        rs.setService(service_name);
        rs.setContext(ctx);
        rs.setUrl(computeURL(ctx, service_name));
        this.service = rs;
    }

    protected abstract void encode(REQ src, WebRequest dst);

    protected abstract void decode(WebResponse src, RESP dst);

    protected final void execute(REQ req, RESP resp) throws IOException {
        WebClient client = this.service.getContext().getClient();
        WebRequest web_req = new WebRequest();
        web_req.setUrl(this.service.getUrl().toString());
        web_req.setMethod(WebMethod.POST); // 默认是 POST

        this.encode(req, web_req);

        RemoteContext ctx = this.service.getContext();
        String service_name = this.service.getService();
        URL url = computeURL(ctx, service_name);
        web_req.setUrl(url.toString());

        WebResponse web_resp = client.execute(web_req);
        this.decode(web_resp, resp);
    }

    private static URL computeURL(RemoteContext ctx, String service_name) {
        String url = ctx.getLocation().toString();
        int index = url.indexOf('?');
        if (index >= 0) {
            url = url.substring(0, index);
        }
        if (!url.endsWith("/")) {
            url = url + "/";
        }
        url = url + "info/refs?service=" + service_name;
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public RemoteService getService() {
        return this.service;
    }

    public void setService(RemoteService service) {
        this.service = service;
    }
}
