package com.github.milegema.mlgm4a.network.web;

import com.github.milegema.mlgm4a.utils.Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class WebHeaderFields {

    private final Map<String, MyValueHolder> fields;

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


    private static class MyValueHolder {
        MyValueHolder prev;
        WebHeader header;
    }


    private static String normalizeName(String name) {
        if (name == null) {
            return "";
        }
        return name.trim().toLowerCase();
    }


    public void add(String name, String value) {
        if (name == null || value == null) {
            return;
        }
        name = normalizeName(name);
        final MyValueHolder holder1 = this.fields.get(name);
        final MyValueHolder holder2 = new MyValueHolder();
        holder2.prev = holder1;
        holder2.header = new WebHeader(name, value);
        fields.put(name, holder2);
    }

    public void add(WebHeader header) {
        if (header == null) {
            return;
        }
        this.add(header.getName(), header.getValue());
    }


    public void set(String name, String value) {
        if (name == null || value == null) {
            return;
        }
        name = normalizeName(name);
        MyValueHolder holder = new MyValueHolder();
        holder.header = new WebHeader(name, value);
        fields.put(name, holder);
    }

    public String get(String name) {
        name = normalizeName(name);
        MyValueHolder holder = fields.get(name);
        if (holder == null) {
            return null;
        }
        WebHeader header = holder.header;
        if (header == null) {
            return null;
        }
        return header.getValue();
    }

    public String[] getValues(String name) {
        name = normalizeName(name);
        MyValueHolder src = fields.get(name);
        List<String> dst = new ArrayList<>();
        MyValueHolder p = src;
        for (; p != null; p = p.prev) {
            if (p.header == null) {
                continue;
            }
            String value = p.header.getValue();
            if (value == null) {
                continue;
            }
            dst.add(value);
        }
        String[] array = dst.toArray(new String[0]);
        Strings.reverse(array);
        return array;
    }


    public String[] names() {
        Set<String> keys = this.fields.keySet();
        return keys.toArray(new String[0]);
    }
}
