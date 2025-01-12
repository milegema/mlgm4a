package com.github.milegema.mlgm4a.utils;

import android.util.NoSuchPropertyException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Attributes {

    private final Map<String, Object> table;

    public Attributes() {
        this.table = Collections.synchronizedMap(new HashMap<>());
    }

    public Attributes(Attributes src) {
        Map<String, Object> tmp = new HashMap<>();
        if (src != null) {
            tmp.putAll(src.table);
        }
        this.table = Collections.synchronizedMap(tmp);
    }

    public void set(String name, Object value) {
        if (name == null || value == null) {
            return;
        }
        table.put(name, value);
    }

    public Object get(String name) {
        return get(name, true);
    }

    public Object get(String name, boolean required) {
        Object att = table.get(name);
        if (att == null && required) {
            throw new NoSuchPropertyException("no attribute with name: " + name);
        }
        return att;
    }

    public Object get(String name, Object def) {
        Object att = table.get(name);
        if (att == null) {
            att = def;
        }
        return att;
    }


    /**
     * @noinspection unchecked
     */
    public <T> T get(String name, Class<T> t) {
        Object att = get(name, true);
        return (T) att;
    }
}
