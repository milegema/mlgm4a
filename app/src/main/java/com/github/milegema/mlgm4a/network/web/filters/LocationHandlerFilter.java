package com.github.milegema.mlgm4a.network.web.filters;


import com.github.milegema.mlgm4a.network.web.WebFilter;
import com.github.milegema.mlgm4a.network.web.WebFilterChain;
import com.github.milegema.mlgm4a.network.web.WebFilterOrder;
import com.github.milegema.mlgm4a.network.web.WebFilterRegistration;
import com.github.milegema.mlgm4a.network.web.WebFilterRegistry;
import com.github.milegema.mlgm4a.network.web.WebRequest;
import com.github.milegema.mlgm4a.network.web.WebResponse;

import java.io.IOException;

public final class LocationHandlerFilter implements WebFilterRegistry, WebFilter {

    public LocationHandlerFilter() {
    }

    @Override
    public WebResponse execute(WebRequest request, WebFilterChain next) throws IOException {

        String url = request.getUrl();
        url = this.normalizeURL(url);
        request.setUrl(url);

        return next.execute(request);
    }

    private String normalizeURL(String url) {

        if (url == null) {
            url = "/";
        }

        if (url.startsWith("http:/") || url.startsWith("https:/")) {
            return url;
        }

        if (url.startsWith("/")) {
            String base = this.getBaseURL();
            return base + url;
        }

        return url;
    }

    private String getBaseURL() {
        return "http://bitwormhole.com:80"; // 这里暂时写死
    }

    @Override
    public WebFilterRegistration registration() {
        WebFilterRegistration reg = new WebFilterRegistration();
        reg.setFilter(this);
        reg.setEnabled(true);
        reg.setOrder(WebFilterOrder.LOCATION);
        return reg;
    }
}
