package com.github.milegema.mlgm4a.contexts.ac;

import android.content.Context;

import com.github.milegema.mlgm4a.components.ComponentManager;
import com.github.milegema.mlgm4a.components.ComponentRegistrar;
import com.github.milegema.mlgm4a.configurations.Configuration;
import com.github.milegema.mlgm4a.contexts.ApplicationContext;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.utils.Attributes;

final class ApplicationContextInner {

    private Configuration configuration;
    private Attributes attributes;
    private PropertyTable properties;
    private ApplicationContext facade;
    private Context android;

    private String profile;


    private ComponentManager componentManager;
    private ComponentRegistrar componentRegistrar;


    private long createdAt;
    private long startedAt;
    private long stoppedAt;
    private Throwable error; // app 启动时候发生的最后一个错误

    public ApplicationContextInner() {
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public PropertyTable getProperties() {
        return properties;
    }

    public void setProperties(PropertyTable properties) {
        this.properties = properties;
    }

    public ComponentManager getComponentManager() {
        return componentManager;
    }

    public void setComponentManager(ComponentManager componentManager) {
        this.componentManager = componentManager;
    }

    public ComponentRegistrar getComponentRegistrar() {
        return componentRegistrar;
    }

    public void setComponentRegistrar(ComponentRegistrar componentRegistrar) {
        this.componentRegistrar = componentRegistrar;
    }

    public ApplicationContext getFacade() {
        return facade;
    }

    public void setFacade(ApplicationContext facade) {
        this.facade = facade;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(long startedAt) {
        this.startedAt = startedAt;
    }

    public long getStoppedAt() {
        return stoppedAt;
    }

    public void setStoppedAt(long stoppedAt) {
        this.stoppedAt = stoppedAt;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public Context getAndroid() {
        return android;
    }

    public void setAndroid(Context android) {
        this.android = android;
    }
}
