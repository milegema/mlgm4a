package com.github.milegema.mlgm4a.configurations;

import com.github.milegema.mlgm4a.data.properties.PropertyTable;

public class PropertiesHolder {

    private String name; // like 'application.properties'
    private PropertyTable properties;

    public PropertiesHolder() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PropertyTable getProperties() {
        return properties;
    }

    public void setProperties(PropertyTable properties) {
        this.properties = properties;
    }
}
