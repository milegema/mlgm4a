package com.github.milegema.mlgm4a.network.web.filters;


import com.github.milegema.mlgm4a.network.web.WebException;
import com.github.milegema.mlgm4a.network.web.WebFilter;
import com.github.milegema.mlgm4a.network.web.WebFilterChain;
import com.github.milegema.mlgm4a.network.web.WebFilterOrder;
import com.github.milegema.mlgm4a.network.web.WebFilterRegistration;
import com.github.milegema.mlgm4a.network.web.WebFilterRegistry;
import com.github.milegema.mlgm4a.network.web.WebRequest;
import com.github.milegema.mlgm4a.network.web.WebResponse;
import com.github.milegema.mlgm4a.network.web.WebStatus;

import java.io.IOException;

public final class ResponseStatusFilter implements WebFilterRegistry, WebFilter {

    public ResponseStatusFilter() {
    }

    @Override
    public WebResponse execute(WebRequest request, WebFilterChain next) throws IOException {
        WebResponse resp = next.execute(request);
        WebStatus status = resp.getStatus();
        int code = status.getCode();
        if (code >= 400) {
            throw new WebException(code);
        }
        return resp;
    }

    @Override
    public WebFilterRegistration registration() {
        WebFilterRegistration reg = new WebFilterRegistration();
        reg.setFilter(this);
        reg.setEnabled(true);
        reg.setOrder(WebFilterOrder.STATUS);
        return reg;
    }
}
