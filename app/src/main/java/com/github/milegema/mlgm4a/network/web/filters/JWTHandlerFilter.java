package com.github.milegema.mlgm4a.network.web.filters;


import com.github.milegema.mlgm4a.network.web.WebFilter;
import com.github.milegema.mlgm4a.network.web.WebFilterChain;
import com.github.milegema.mlgm4a.network.web.WebFilterOrder;
import com.github.milegema.mlgm4a.network.web.WebFilterRegistration;
import com.github.milegema.mlgm4a.network.web.WebFilterRegistry;
import com.github.milegema.mlgm4a.network.web.WebRequest;
import com.github.milegema.mlgm4a.network.web.WebResponse;

import java.io.IOException;

public final class JWTHandlerFilter implements WebFilterRegistry, WebFilter {


    public JWTHandlerFilter() {
    }

    @Override
    public WebResponse execute(WebRequest request, WebFilterChain next) throws IOException {
        return next.execute(request);
    }

    @Override
    public WebFilterRegistration registration() {
        WebFilterRegistration reg = new WebFilterRegistration();
        reg.setFilter(this);
        reg.setEnabled(true);
        reg.setOrder(WebFilterOrder.JWT);
        return reg;
    }
}
