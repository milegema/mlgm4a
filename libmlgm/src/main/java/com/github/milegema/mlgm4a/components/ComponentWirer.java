package com.github.milegema.mlgm4a.components;

import com.github.milegema.mlgm4a.contexts.ApplicationContext;

public interface ComponentWirer {

    void wire(ApplicationContext appContext, ComponentHolder holder);

}
