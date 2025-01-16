package com.github.milegema.mlgm4a.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ComponentSetBuilder {

    private final List<ComponentHolderBuilder> list;

    public ComponentSetBuilder() {
        this.list = new ArrayList<>();
    }


    public ComponentHolderBuilder addComponentInstance(Object inst) {
        ComponentHolderBuilder b = new ComponentHolderBuilder();
        b.init(inst);
        this.list.add(b);
        return b;
    }

    public ComponentHolderBuilder addComponentFactory(ComponentFactory factory) {
        ComponentHolderBuilder b = new ComponentHolderBuilder();
        b.setFactory(factory);
        this.list.add(b);
        return b;
    }

    public ComponentHolderBuilder addComponentProvider(ComponentProvider provider) {
        ComponentHolderBuilder b = new ComponentHolderBuilder();
        b.setFactory(provider);
        b.setWirer(provider);
        b.setId(provider.getDefaultID());
        this.list.add(b);
        return b;
    }

    public ComponentSet create() {
        DefaultComponentSet cs = new DefaultComponentSet();
        for (ComponentHolderBuilder b : list) {
            b.registerTo(cs);
        }
        return cs;
    }
}
