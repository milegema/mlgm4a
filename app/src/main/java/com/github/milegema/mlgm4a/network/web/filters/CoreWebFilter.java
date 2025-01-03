package com.github.milegema.mlgm4a.network.web.filters;


import com.github.milegema.mlgm4a.network.web.WebEntity;
import com.github.milegema.mlgm4a.network.web.WebFilter;
import com.github.milegema.mlgm4a.network.web.WebFilterChain;
import com.github.milegema.mlgm4a.network.web.WebFilterOrder;
import com.github.milegema.mlgm4a.network.web.WebFilterRegistration;
import com.github.milegema.mlgm4a.network.web.WebFilterRegistry;
import com.github.milegema.mlgm4a.network.web.WebHeaderFields;
import com.github.milegema.mlgm4a.network.web.WebRequest;
import com.github.milegema.mlgm4a.network.web.WebResponse;
import com.github.milegema.mlgm4a.utils.ByteSlice;
import com.github.milegema.mlgm4a.utils.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public final class CoreWebFilter implements WebFilterRegistry, WebFilter {


    @Override
    public WebResponse execute(WebRequest request, WebFilterChain next) throws IOException {

        WebResponse resp = next.execute(request);
        if (resp == null) {
            resp = new WebResponse();
        }

        final String method = request.getMethod().name();
        final String location = request.getUrl();
        final URL url = new URL(location);
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        try {
            conn.setRequestMethod(method);
            conn.setUseCaches(false);
            resp.setRequest(request);
            resp.setLocation(url);

            // prepare
            this.prepareRequestHead(conn, request);
            this.prepareRequestBody(conn, request);

            // send
            int code = conn.getResponseCode();
            String msg = conn.getResponseMessage();
            resp.getStatus().setCode(code);
            resp.getStatus().setMessage(msg);

            // handle
            this.handleResponseHead(conn, resp);
            this.handleResponseBody(conn, resp);
        } finally {
            conn.disconnect();
        }
        return resp;
    }

    private void prepareRequestHead(HttpURLConnection conn, WebRequest req) {
        WebHeaderFields src = req.getHeader();
        if (src != null) {
            String[] names = src.names();
            for (String name : names) {
                String value = src.get(name);
                conn.setRequestProperty(name, value);
            }
        }
    }

    private void prepareRequestBody(HttpURLConnection conn, WebRequest req) throws IOException {
        WebEntity src = req.getEntity();
        if (src == null) {
            return;
        }
        ByteSlice content = src.getContent();
        if (content == null) {
            return;
        }
        String content_type = src.getContentType();
        if (content_type != null) {
            conn.setRequestProperty("content-type", content_type);
        }
        conn.setDoOutput(true);
        OutputStream out = conn.getOutputStream();
        try {
            out.write(content.getData(), content.getOffset(), content.getLength());
            out.flush();
        } finally {
            IOUtils.close(out);
        }
    }

    private void handleResponseHead(HttpURLConnection conn, WebResponse resp) {
        Map<String, List<String>> src = conn.getHeaderFields();
        WebHeaderFields dst = resp.getHeader();
        if (src == null || dst == null) {
            return;
        }
        src.forEach((name, values) -> {
            for (String value : values) {
                dst.set(name, value);
            }
        });
    }

    private void handleResponseBody(HttpURLConnection conn, WebResponse resp) throws IOException {
        final InputStream in;
        if (this.hasError(resp)) {
            in = conn.getErrorStream();
        } else {
            in = conn.getInputStream();
        }
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            IOUtils.copyAll(in, buffer);
            // String content_type = resp.getHeader().get( "" ) ;
            String content_type = conn.getContentType();
            WebEntity entity = new WebEntity();
            entity.setContentType(content_type);
            entity.setContent(new ByteSlice(buffer.toByteArray()));
            resp.setEntity(entity);
        } finally {
            IOUtils.close(in);
        }
    }

    private boolean hasError(WebResponse resp) {
        int code = resp.getStatus().getCode();
        return !((200 <= code) && (code < 300));
    }

    @Override
    public WebFilterRegistration registration() {
        WebFilterRegistration reg = new WebFilterRegistration();
        reg.setFilter(this);
        reg.setOrder(WebFilterOrder.CORE);
        reg.setEnabled(true);
        return reg;
    }
}
