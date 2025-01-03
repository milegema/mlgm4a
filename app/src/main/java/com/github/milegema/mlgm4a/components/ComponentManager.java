package com.github.milegema.mlgm4a.components;

public interface ComponentManager {

    <T> T find(String selector, Class<T> t);

    <T> T findById(String id, Class<T> t);

    <T> T find(Class<T> t);

    ComponentHolder getHolder(String selector);

    String[] listIds();

}
