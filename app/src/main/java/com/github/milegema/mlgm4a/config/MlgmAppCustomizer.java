package com.github.milegema.mlgm4a.config;

import com.github.milegema.mlgm4a.configurations.ApplicationPropertiesLoader;
import com.github.milegema.mlgm4a.configurations.Configuration;
import com.github.milegema.mlgm4a.configurations.Customizer;
import com.github.milegema.mlgm4a.configurations.PropertiesHolder;


import java.util.List;

public final class MlgmAppCustomizer implements Customizer {

    @Override
    public void customize(Configuration configuration) {

        configuration.setProfile("debug");

        this.customize_props(configuration);
        this.customize_components(configuration);
    }

    private void customize_props(Configuration configuration) {

        ApplicationPropertiesLoader loader = new ApplicationPropertiesLoader();

        loader.add("application.properties", ApplicationProperties::get);
        loader.add("application-release.properties", ApplicationReleaseProperties::get);
        loader.add("application-debug.properties", ApplicationDebugProperties::get);
        loader.add("application-default.properties", ApplicationDefaultProperties::get);

        List<PropertiesHolder> list = loader.loadAll();
        configuration.getProperties().addAll(list);
    }

    private void customize_components(Configuration configuration) {


        // xxx = configuration.getComponentSetBuilder() .addComponent( obj ) ;

    }

}
