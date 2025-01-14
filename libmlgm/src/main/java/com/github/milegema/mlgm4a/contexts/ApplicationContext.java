package com.github.milegema.mlgm4a.contexts;

import com.github.milegema.mlgm4a.components.ComponentManager;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.utils.Attributes;

public interface ApplicationContext {

    Attributes attributes();

    PropertyTable properties();

    ComponentManager components();

}
