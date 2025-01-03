package com.github.milegema.mlgm4a.data.properties;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class PropertyTableImpl implements PropertyTable {

    private final Map<String, String> table;

    PropertyTableImpl() {
        Map<String, String> t = new HashMap<>();
        this.table = Collections.synchronizedMap(t);
    }

    @Override
    public void put(String name, String value) {
        if (name == null || value == null) {
            return;
        }
        table.put(name, value);
    }

    @Override
    public String get(String name) {
        return table.get(name);
    }

    @Override
    public void remove(String name) {
        table.remove(name);
    }

    @Override
    public void clear() {
        table.clear();
    }

    @Override
    public int size() {
        return this.table.size();
    }

    @Override
    public String[] names() {
        Set<String> src = table.keySet();
        String[] dst = new String[0];
        dst = src.toArray(dst);
        Arrays.sort(dst);
        return dst;
    }

    @Override
    public Map<String, String> exportAll(Map<String, String> dst) {
        if (dst == null) {
            dst = new HashMap<>();
        }
        dst.putAll(this.table);
        return dst;
    }

    @Override
    public void importAll(Map<String, String> src) {
        if (src == null) {
            return;
        }
        this.table.putAll(src);
    }
}
