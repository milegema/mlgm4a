package com.github.milegema.mlgm4a.components;

public class ComponentHolder {

    private Object instance;
    private String id;
    private String[] classes;
    private String[] aliases;


    private ComponentFactory factory;
    private ComponentWirer wirer;

    public ComponentHolder() {
    }

    public ComponentFactory getFactory() {
        return factory;
    }

    public void setFactory(ComponentFactory factory) {
        this.factory = factory;
    }

    public ComponentWirer getWirer() {
        return wirer;
    }

    public void setWirer(ComponentWirer wirer) {
        this.wirer = wirer;
    }

    public String[] getAliases() {
        return aliases;
    }

    public void setAliases(String[] aliases) {
        this.aliases = aliases;
    }

    public String[] getClasses() {
        return classes;
    }

    public void setClasses(String[] classes) {
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
