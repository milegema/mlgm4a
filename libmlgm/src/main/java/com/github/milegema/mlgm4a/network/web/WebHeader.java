package com.github.milegema.mlgm4a.network.web;

import androidx.annotation.NonNull;

public class WebHeader {

    private String name;
    private String value;

    public WebHeader() {
    }

    public WebHeader(String _name, String _value) {
        this.name = _name;
        this.value = _value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name + ":" + this.value;
    }
}
