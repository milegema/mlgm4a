package com.github.milegema.mlgm4a.network.web.filters;


import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.network.web.WebFilter;
import com.github.milegema.mlgm4a.network.web.WebFilterChain;
import com.github.milegema.mlgm4a.network.web.WebFilterOrder;
import com.github.milegema.mlgm4a.network.web.WebFilterRegistration;
import com.github.milegema.mlgm4a.network.web.WebFilterRegistry;
import com.github.milegema.mlgm4a.network.web.WebRequest;
import com.github.milegema.mlgm4a.network.web.WebResponse;

import java.io.IOException;

public final class LogFilter implements WebFilterRegistry, WebFilter {


    public LogFilter() {
    }

    private static class LogItem {
        String method;
        String url;
        int code;
        String message;
    }

    @Override
    public WebResponse execute(WebRequest request, WebFilterChain next) throws IOException {
        LogItem item = new LogItem();
        try {
            item.method = request.getMethod().name();
            item.url = request.getUrl();
            WebResponse resp = next.execute(request);
            item.code = resp.getStatus().getCode();
            item.message = resp.getStatus().getMessage();
            return resp;
        } catch (Exception e) {
            item.message = "error:" + e.getMessage();
            item.code = -1;
            throw new RuntimeException(e);
        } finally {
            this.log(item);
        }
    }

    private void log(LogItem item) {
        StringBuilder b = new StringBuilder();
        b.append(item.method);
        b.append(' ');
        b.append(item.url);
        b.append("; Response:HTTP ");
        b.append(item.code);
        b.append(' ');
        b.append(item.message);
        Logs.debug(b.toString());
    }

    @Override
    public WebFilterRegistration registration() {
        WebFilterRegistration reg = new WebFilterRegistration();
        reg.setFilter(this);
        reg.setEnabled(true);
        reg.setOrder(WebFilterOrder.LOG);
        return reg;
    }
}
