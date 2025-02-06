package com.github.milegema.mlgm4a.libmlgm;

import com.github.milegema.mlgm4a.components.ComponentHolderBuilder;
import com.github.milegema.mlgm4a.components.ComponentProviderT;
import com.github.milegema.mlgm4a.components.ComponentSetBuilder;
import com.github.milegema.mlgm4a.configurations.Configuration;
import com.github.milegema.mlgm4a.contexts.ContextAgent;
import com.github.milegema.mlgm4a.data.properties.PropertyGetter;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.network.web.WebClient;
import com.github.milegema.mlgm4a.network.web.WebClientFacade;
import com.github.milegema.mlgm4a.network.web.WebFilterRegistry;
import com.github.milegema.mlgm4a.network.web.filters.CoreWebFilter;
import com.github.milegema.mlgm4a.network.web.filters.ExampleFilter;
import com.github.milegema.mlgm4a.network.web.filters.JWTHandlerFilter;
import com.github.milegema.mlgm4a.network.web.filters.LocationHandlerFilter;
import com.github.milegema.mlgm4a.network.web.filters.LogFilter;
import com.github.milegema.mlgm4a.network.web.filters.ResponseStatusFilter;

final class ConfigComWeb {

    public static void config_all(Configuration configuration) {
        final ComponentSetBuilder csb = configuration.getComponentSetBuilder();

        config_web_client(csb);

        config_web_filter_core(csb);
        config_web_filter_log(csb);
        config_web_filter_location(csb);
        config_web_filter_jwt(csb);
        config_web_filter_status(csb);
    }


    private static void config_web_client(ComponentSetBuilder csb) {
        ComponentProviderT<WebClientFacade> provider = new ComponentProviderT<>();
        provider.setFactory(WebClientFacade::new);
        provider.setWirer((ac, holder, inst) -> {
            inst.setApplicationContext(ac);
        });
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addAlias(WebClient.class);
    }


    private static void config_web_filter_core(ComponentSetBuilder csb) {
        ComponentProviderT<CoreWebFilter> provider = new ComponentProviderT<>();
        provider.setFactory(CoreWebFilter::new);
        provider.setWirer((ac, holder, inst) -> {
            //  inst.setApplicationContext(ac);
        });
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addClass(WebFilterRegistry.class);
    }

    private static void config_web_filter_jwt(ComponentSetBuilder csb) {
        ComponentProviderT<JWTHandlerFilter> provider = new ComponentProviderT<>();
        provider.setFactory(JWTHandlerFilter::new);
        provider.setWirer((ac, holder, inst) -> {
            ContextAgent ca1 = ac.components().find(ContextAgent.class);
            inst.setContextAgent(ca1);
        });
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addClass(WebFilterRegistry.class);
    }

    private static void config_web_filter_log(ComponentSetBuilder csb) {
        ComponentProviderT<LogFilter> provider = new ComponentProviderT<>();
        provider.setFactory(LogFilter::new);
        provider.setWirer((ac, holder, inst) -> {
            // inst.setApplicationContext(ac);
        });
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addClass(WebFilterRegistry.class);
    }

    private static void config_web_filter_location(ComponentSetBuilder csb) {
        ComponentProviderT<LocationHandlerFilter> provider = new ComponentProviderT<>();
        provider.setFactory(LocationHandlerFilter::new);
        provider.setWirer((ac, holder, inst) -> {
            //  inst.setApplicationContext(ac);
        });
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addClass(WebFilterRegistry.class);
    }

    private static void config_web_filter_status(ComponentSetBuilder csb) {
        ComponentProviderT<ResponseStatusFilter> provider = new ComponentProviderT<>();
        provider.setFactory(ResponseStatusFilter::new);
        provider.setWirer((ac, holder, inst) -> {
            //  inst.setApplicationContext(ac);
        });
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addClass(WebFilterRegistry.class);
    }

}
