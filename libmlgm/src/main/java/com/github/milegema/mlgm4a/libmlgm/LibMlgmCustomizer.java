package com.github.milegema.mlgm4a.libmlgm;

import com.github.milegema.mlgm4a.components.ComponentHolderBuilder;
import com.github.milegema.mlgm4a.components.ComponentProviderT;
import com.github.milegema.mlgm4a.components.ComponentSetBuilder;
import com.github.milegema.mlgm4a.configurations.ApplicationPropertiesLoader;
import com.github.milegema.mlgm4a.configurations.Configuration;
import com.github.milegema.mlgm4a.configurations.Customizer;
import com.github.milegema.mlgm4a.configurations.PropertiesHolder;
import com.github.milegema.mlgm4a.network.web.WebClient;
import com.github.milegema.mlgm4a.network.web.WebClientFacade;

import java.util.List;

public final class LibMlgmCustomizer implements Customizer {

    public LibMlgmCustomizer() {
    }

    @Override
    public void customize(Configuration configuration) {
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
        MyComponents.config_all(configuration);
    }
}
