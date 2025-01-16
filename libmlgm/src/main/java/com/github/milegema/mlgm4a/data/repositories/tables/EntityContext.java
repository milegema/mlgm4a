package com.github.milegema.mlgm4a.data.repositories.tables;

import com.github.milegema.mlgm4a.data.properties.PropertyGetter;
import com.github.milegema.mlgm4a.data.properties.PropertySetter;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;

public class EntityContext {

    private Object entity;
    private EntityAdapter adapter;
    private Table table;
    private PropertyTable properties;
    private PropertyGetter getter;
    private PropertySetter setter;
    private boolean entityToProperties;

    public EntityContext() {
    }


    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    public EntityAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(EntityAdapter adapter) {
        this.adapter = adapter;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public PropertyTable getProperties() {
        return properties;
    }

    public void setProperties(PropertyTable properties) {
        this.properties = properties;
    }

    public PropertyGetter getGetter() {
        return getter;
    }

    public void setGetter(PropertyGetter getter) {
        this.getter = getter;
    }

    public PropertySetter getSetter() {
        return setter;
    }

    public void setSetter(PropertySetter setter) {
        this.setter = setter;
    }

    public boolean isEntityToProperties() {
        return entityToProperties;
    }

    public void setEntityToProperties(boolean entityToProperties) {
        this.entityToProperties = entityToProperties;
    }
}
