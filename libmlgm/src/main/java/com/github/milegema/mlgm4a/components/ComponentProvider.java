package com.github.milegema.mlgm4a.components;

public interface ComponentProvider extends ComponentFactory, ComponentWirer {

    String getDefaultID();

}
