package com.github.milegema.mlgm4a.network.web.headers;

import com.github.milegema.mlgm4a.network.web.AuthScheme;
import com.github.milegema.mlgm4a.network.web.HTTP;
import com.github.milegema.mlgm4a.network.web.WebHeader;

public class HeaderAuthorization extends WebHeader {

    private AuthScheme scheme;

    public HeaderAuthorization(String _value) {
        super(HTTP.header_authorization, _value);
    }

    public HeaderAuthorization(WebHeader wh) {
        super(HTTP.header_authorization, "");
        if (wh != null) {
            this.setValue(wh.getValue());
        }
    }


    public AuthScheme getScheme() {
        return scheme;
    }

    public void setScheme(AuthScheme scheme) {
        this.scheme = scheme;
    }
}
