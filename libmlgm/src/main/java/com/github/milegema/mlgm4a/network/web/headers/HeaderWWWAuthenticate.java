package com.github.milegema.mlgm4a.network.web.headers;

import com.github.milegema.mlgm4a.network.web.AuthScheme;
import com.github.milegema.mlgm4a.network.web.HTTP;
import com.github.milegema.mlgm4a.network.web.WebHeader;
import com.github.milegema.mlgm4a.network.web.WebHeaderFields;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class HeaderWWWAuthenticate extends WebHeader {

    private AuthScheme scheme;
    private Map<String, String> challenges;


    public HeaderWWWAuthenticate(String _value) {
        super(HTTP.header_www_authenticate, _value);
    }

    public HeaderWWWAuthenticate(WebHeader wh) {
        super(HTTP.header_www_authenticate, "");
        if (wh != null) {
            this.setValue(wh.getValue());
        }
    }

    public static HeaderWWWAuthenticate parseValue(String value) {
        if (value == null) {
            return null;
        }
        value = value.trim();
        HeaderWWWAuthenticate dst = new HeaderWWWAuthenticate(value);
        String[] items = value.replace(' ', ',').split(",");
        Map<String, String> challenges = new HashMap<>();
        for (int i = 0; i < items.length; i++) {
            String item = items[i].trim();
            if (i == 0) {
                dst.setScheme(AuthScheme.valueOf(item));
                continue;
            }
            int index = item.indexOf('=');
            if (index < 0) {
                challenges.put(item, "");
                continue;
            }
            String k = item.substring(0, index).trim();
            String v = item.substring(index + 1).trim();
            challenges.put(k, v);
        }
        dst.setChallenges(challenges);
        return dst;
    }

    public static List<HeaderWWWAuthenticate> list(WebHeaderFields src) {
        List<HeaderWWWAuthenticate> dst = new ArrayList<>();
        if (src == null) {
            return dst;
        }
        final String name = HTTP.header_www_authenticate;
        String[] values = src.getValues(name);
        for (String value : values) {
            if (value == null) {
                continue;
            }
            HeaderWWWAuthenticate item = parseValue(value);
            dst.add(item);
        }
        return dst;
    }

    public AuthScheme getScheme() {
        return scheme;
    }

    public void setScheme(AuthScheme scheme) {
        this.scheme = scheme;
    }

    public Map<String, String> getChallenges() {
        return challenges;
    }

    public void setChallenges(Map<String, String> challenges) {
        this.challenges = challenges;
    }
}
