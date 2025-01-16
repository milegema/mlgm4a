package com.github.milegema.mlgm4a.contexts.ac;

import com.github.milegema.mlgm4a.configurations.Configuration;
import com.github.milegema.mlgm4a.contexts.ApplicationContext;

public interface ApplicationContextFactory {

    ApplicationContext create(Configuration configuration);

}
