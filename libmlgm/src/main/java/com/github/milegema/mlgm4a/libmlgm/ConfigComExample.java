package com.github.milegema.mlgm4a.libmlgm;

import com.github.milegema.mlgm4a.boot.AutoRootContextLoader;
import com.github.milegema.mlgm4a.boot.AutoUserContextLoader;
import com.github.milegema.mlgm4a.components.ComLifecycle;
import com.github.milegema.mlgm4a.components.ComponentHolderBuilder;
import com.github.milegema.mlgm4a.components.ComponentManager;
import com.github.milegema.mlgm4a.components.ComponentProviderT;
import com.github.milegema.mlgm4a.components.ComponentSetBuilder;
import com.github.milegema.mlgm4a.configurations.Configuration;
import com.github.milegema.mlgm4a.contexts.ContextAgent;
import com.github.milegema.mlgm4a.contexts.ac.AndroidContextAgent;
import com.github.milegema.mlgm4a.data.properties.Names;
import com.github.milegema.mlgm4a.data.properties.PropertyGetter;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.network.web.WebClient;
import com.github.milegema.mlgm4a.network.web.WebClientFacade;

final class ConfigComExample {

    public static void config_all(Configuration configuration) {
        final ComponentSetBuilder csb = configuration.getComponentSetBuilder();
        config_example(csb);
    }


    private static void config_example(ComponentSetBuilder csb) {
        ComponentProviderT<MyExampleCom> provider = new ComponentProviderT<>();
        provider.setFactory(MyExampleCom::new);
        provider.setWirer((ac, holder, inst) -> {

            PropertyTable pt = ac.properties();
            PropertyGetter getter = new PropertyGetter(pt);
            int limit = getter.getInt("com.example.limit", 0);
            String label = getter.getString("com.example.label", "");
            WebClient web_client = ac.components().find(WebClient.class);

            inst.setApplicationContext(ac);
            inst.setAttrLabel(label);
            inst.setAttrLimit(limit);
            inst.setClient(web_client);
        });
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addAlias(MyExampleCom.class);
    }
}
