package com.github.milegema.mlgm4a.components;

import java.util.ArrayList;
import java.util.List;

public final class ComponentHolderBuilder {

    private Object instance;
    private String id;
    private List<String> classes;
    private List<String> aliases;

    public ComponentHolderBuilder() {
        this.aliases = new ArrayList<>();
        this.classes = new ArrayList<>();
    }

    public ComponentHolderBuilder addClass(String cl) {
        if (cl != null) {
            this.classes.add(cl);
        }
        return this;
    }

    public ComponentHolderBuilder addAlias(String al) {
        if (al != null) {
            this.aliases.add(al);
        }
        return this;
    }

    public ComponentHolderBuilder addAlias(Class<?> al) {
        if (al != null) {
            this.aliases.add(al.getName());
        }
        return this;
    }

    public ComponentHolderBuilder addClass(Class<?> cl) {
        if (cl != null) {
            this.classes.add(cl.getName());
        }
        return this;
    }

    public ComponentHolderBuilder init(String id, Object inst) {
        this.id = id;
        this.instance = inst;
        this.aliases = new ArrayList<>();
        this.classes = new ArrayList<>();
        return this;
    }

    public ComponentHolderBuilder init(Class<?> id, Object inst) {
        this.id = id.getName();
        this.instance = inst;
        this.aliases = new ArrayList<>();
        this.classes = new ArrayList<>();
        return this;
    }

    public ComponentHolderBuilder init(Object inst) {
        this.id = inst.getClass().getName();
        this.instance = inst;
        this.aliases = new ArrayList<>();
        this.classes = new ArrayList<>();
        return this;
    }


    public ComponentHolder create() {
        ComponentHolder holder = new ComponentHolder();
        holder.setId(this.id);
        holder.setInstance(this.instance);
        holder.setAliases(toArray(this.aliases));
        holder.setClasses(toArray(this.classes));
        return holder;
    }

    public void registerTo(ComponentRegistrar reg) {
        ComponentHolder ch = create();
        reg.register(ch);
    }

    private static String[] toArray(List<String> list) {
        String[] a0 = new String[0];
        if (list == null) {
            return a0;
        }
        return list.toArray(a0);
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }
}
