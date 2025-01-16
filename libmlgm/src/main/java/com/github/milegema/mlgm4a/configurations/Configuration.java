package com.github.milegema.mlgm4a.configurations;

import android.content.Context;

import com.github.milegema.mlgm4a.components.ComponentSetBuilder;
import com.github.milegema.mlgm4a.contexts.ac.ApplicationContextFactory;
import com.github.milegema.mlgm4a.contexts.ac.DefaultApplicationContextFactory;
import com.github.milegema.mlgm4a.utils.Attributes;

import java.util.ArrayList;
import java.util.List;

public class Configuration {

    private ApplicationContextFactory factory;
    private String profile;
    private Attributes attributes;
    private List<PropertiesHolder> properties;
    private List<Customizer> customizers;
    private Context android;

    private ComponentSetBuilder componentSetBuilder;
    // private List<ComponentHolder> components;


    public Configuration() {
        this.init();
    }

    public Configuration init() {
        // this.profile = "default";
        // this.components = new ArrayList<>();

        this.attributes = new Attributes();
        this.properties = new ArrayList<>();
        this.customizers = new ArrayList<>();
        this.factory = new DefaultApplicationContextFactory();
        this.componentSetBuilder = new ComponentSetBuilder();
        return this;
    }


    public ApplicationContextFactory getFactory() {
        return factory;
    }

    public void setFactory(ApplicationContextFactory factory) {
        this.factory = factory;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public List<PropertiesHolder> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertiesHolder> properties) {
        this.properties = properties;
    }

    public List<Customizer> getCustomizers() {
        return customizers;
    }

    public void setCustomizers(List<Customizer> customizers) {
        this.customizers = customizers;
    }


    public ComponentSetBuilder getComponentSetBuilder() {
        return componentSetBuilder;
    }

    public void setComponentSetBuilder(ComponentSetBuilder componentSetBuilder) {
        this.componentSetBuilder = componentSetBuilder;
    }

    public Context getAndroid() {
        return android;
    }

    public void setAndroid(Context android) {
        this.android = android;
    }
}
