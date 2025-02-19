package com.github.milegema.mlgm4a.libmlgm;

import com.github.milegema.mlgm4a.configurations.ApplicationPropertiesLoader;
import com.github.milegema.mlgm4a.configurations.Configuration;
import com.github.milegema.mlgm4a.configurations.Customizer;
import com.github.milegema.mlgm4a.configurations.PropertiesHolder;

import java.util.List;

public final class LibMlgmCustomizer implements Customizer {

    public LibMlgmCustomizer() {
    }

    @Override
    public void customize(Configuration configuration) {
        this.customize_banner(configuration);
        this.customize_props(configuration);
        this.customize_attrs(configuration);
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
        ConfigComDB.config_all(configuration);
        ConfigComDAObjects.config_all(configuration);
        ConfigComRepos.config_all(configuration);
        ConfigComSecurity.config_all(configuration);
        ConfigComOthers.config_all(configuration);
        ConfigComWeb.config_all(configuration);
        ConfigComServices.config_all(configuration);
    }

    private void customize_attrs(Configuration configuration) {
        Object bar = new MyExampleCom();
        configuration.getAttributes().set("foo", bar);
    }

    private void customize_banner(Configuration configuration) {
        final char nl = '\n';
        final StringBuilder bb = new StringBuilder();
        bb.append("============================================").append(nl);
        bb.append("        _ _                                 ").append(nl);
        bb.append("  /\\/\\ (_) | ___  __ _  ___ _ __ ___   __ _ ").append(nl);
        bb.append(" /    \\| | |/ _ \\/ _` |/ _ \\ '_ ` _ \\ / _` |").append(nl);
        bb.append("/ /\\/\\ \\ | |  __/ (_| |  __/ | | | | | (_| |").append(nl);
        bb.append("\\/    \\/_|_|\\___|\\__, |\\___|_| |_| |_|\\__,_|").append(nl);
        bb.append("                 |___/                      ").append(nl);
        bb.append("Milegame Android Application").append(nl);
        bb.append("============================================").append(nl);
        configuration.setBannerText(bb.toString());
    }
}
