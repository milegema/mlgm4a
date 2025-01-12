package com.github.milegema.mlgm4a.libmlgm;

import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.network.web.DefaultWebClientFactory;
import com.github.milegema.mlgm4a.network.web.WebClient;
import com.github.milegema.mlgm4a.network.web.WebClientFactory;
import com.github.milegema.mlgm4a.network.web.WebConfiguration;
import com.github.milegema.mlgm4a.network.web.WebHeaderFields;
import com.github.milegema.mlgm4a.network.web.WebMethod;
import com.github.milegema.mlgm4a.network.web.WebRequest;
import com.github.milegema.mlgm4a.network.web.WebResponse;
import com.github.milegema.mlgm4a.network.web.WebStatus;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebClientTest {

    @Test
    public void useWebClient() throws IOException {

        String url = "https://gitee.com/bitwormhole/mlgm4a.git";

        WebConfiguration config = new WebConfiguration();
        WebClientFactory factory = new DefaultWebClientFactory();
        WebClient client = factory.create(config);

        WebRequest req = new WebRequest();
        req.setMethod(WebMethod.GET);
        req.setUrl(url);

        WebResponse resp = client.execute(req);
        WebStatus status = resp.getStatus();
        int code = status.getCode();
        Logs.info(status.toString());

        if (code == WebStatus.OK) {
            WebHeaderFields headers = resp.getHeader();
            List<String> fields = new ArrayList<>();
            fields.add("Content-Length");
            fields.add("Content-Type");
            for (String name : fields) {
                String value = headers.get(name);
                Logs.debug("http.response.headers: " + name + " = " + value);
            }
        }
        Assert.assertEquals(code, WebStatus.OK);
    }
}
