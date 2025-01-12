package com.github.milegema.mlgm4a.network.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WebHeaderFields {

    private final Map<String, String> fields;

    public WebHeaderFields() {
        this.fields = new HashMap<>();
    }

    public WebHeaderFields(WebHeaderFields src) {
        if (src == null) {
            this.fields = new HashMap<>();
        } else {
            this.fields = new HashMap<>(src.fields);
        }
    }

    private static String normalizeName(String name) {
        if (name == null) {
            return "";
        }
        return name.trim().toLowerCase();
    }

    public void set(String name, String value) {
        if (name == null || value == null) {
            return;
        }
        name = normalizeName(name);
        fields.put(name, value);
    }

    public String get(String name) {
        name = normalizeName(name);
        return fields.get(name);
    }

    public String[] names() {
        Set<String> keys = this.fields.keySet();
        return keys.toArray(new String[0]);
    }
}
