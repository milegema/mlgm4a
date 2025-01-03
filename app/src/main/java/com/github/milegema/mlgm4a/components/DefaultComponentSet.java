package com.github.milegema.mlgm4a.components;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DefaultComponentSet extends ComponentSet {

    private final Map<String, ComponentHolder> table;

    public DefaultComponentSet() {
        Map<String, ComponentHolder> t = new HashMap<>();
        this.table = Collections.synchronizedMap(t);
    }

    /**
     * @noinspection unchecked
     */
    @Override
    public <T> T find(String selector, Class<T> t) {
        ComponentHolder h = getHolder(selector);
        Object inst = h.getInstance();
        if (inst == null) {
            throw new RuntimeException("component instance is null, with selector: " + selector);
        }
        return (T) inst;
    }

    @Override
    public <T> T findById(String id, Class<T> t) {
        String sel = "#" + id;
        return find(sel, t);
    }

    @Override
    public <T> T find(Class<T> t) {
        String sel = "#" + t.getName();
        return find(sel, t);
    }

    @Override
    public ComponentHolder getHolder(String selector) {
        ComponentHolder h = table.get(selector);
        if (h == null) {
            throw new RuntimeException("no component with selector: " + selector);
        }
        return h;
    }

    @Override
    public String[] listIds() {
        Map<String, ComponentHolder> mid = new HashMap<>(); // map<id, holder>
        Collection<ComponentHolder> src = table.values();
        for (ComponentHolder ch : src) {
            String id = ch.getId();
            mid.put(id, ch);
        }
        return mid.keySet().toArray(new String[0]);
    }

    private void putWithId(String id, ComponentHolder h) {
        if (id == null) {
            return;
        }
        final String name = "#" + id;
        final ComponentHolder older = table.get(name);
        if (older != null) {
            throw new RuntimeException("component.id is duplicate: " + name);
        }
        table.put(name, h);
    }

    private void putWithClass(String cl, ComponentHolder h) {
        if (cl == null) {
            return;
        }
        final String name = "." + cl;
        // final ComponentHolder older = table.get(name);
        // if (older != null) {
        //  throw new RuntimeException("component.class is duplicate: " + name);
        // }
        table.put(name, h);
    }

    private void putWithAlias(String alias, ComponentHolder h) {
        if (alias == null) {
            return;
        }
        final String name = "#" + alias;
        final ComponentHolder older = table.get(name);
        if (older != null) {
            throw new RuntimeException("component.alias is duplicate: " + name);
        }
        table.put(name, h);
    }

    @Override
    public void register(ComponentHolder h) {
        if (h == null) {
            return;
        }

        final String id = h.getId();
        final String[] aliases = h.getAliases();
        final String[] classes = h.getClasses();

        if (aliases != null) {
            for (String al : aliases) {
                putWithAlias(al, h);
            }
        }

        if (classes != null) {
            for (String cl : classes) {
                putWithClass(cl, h);
            }
        }

        putWithId(id, h);
    }
}
