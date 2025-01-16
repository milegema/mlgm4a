package com.github.milegema.mlgm4a.contexts.ac;

import com.github.milegema.mlgm4a.components.ComponentManager;
import com.github.milegema.mlgm4a.contexts.ApplicationContext;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.utils.Attributes;

final class ApplicationContextFacade implements ApplicationContext {

    private final ApplicationContextInner inner;

    public ApplicationContextFacade(ApplicationContextInner in) {
        this.inner = in;
    }

    @Override
    public Attributes attributes() {
        return inner.getAttributes();
    }

    @Override
    public PropertyTable properties() {
        return inner.getProperties();
    }

    @Override
    public ComponentManager components() {
        return inner.getComponentManager();
    }

    @Override
    public String profile() {
        return inner.getProfile();
    }
}
