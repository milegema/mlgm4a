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
import com.github.milegema.mlgm4a.data.repositories.RepositoryManager;
import com.github.milegema.mlgm4a.security.KeyPairManager;

import java.security.KeyPair;

final class ConfigComOthers {

    public static void config_all(Configuration configuration) {
        final ComponentSetBuilder csb = configuration.getComponentSetBuilder();

        config_context_agent(csb);
        config_auto_root_context_loader(csb);
        config_auto_user_context_loader(csb);
    }

    private static void config_context_agent(ComponentSetBuilder csb) {
        ComponentProviderT<AndroidContextAgent> provider = new ComponentProviderT<>();
        provider.setFactory(AndroidContextAgent::new);
        provider.setWirer((ac, holder, inst) -> {
            inst.setApplicationContext(ac);
        });
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addAlias(ContextAgent.class);
    }

    private static void config_auto_root_context_loader(ComponentSetBuilder csb) {
        ComponentProviderT<AutoRootContextLoader> provider = new ComponentProviderT<>();
        provider.setFactory(AutoRootContextLoader::new);
        provider.setWirer((ac, holder, inst) -> {

            ComponentManager com_man = ac.components();
            PropertyTable pt = ac.properties();
            PropertyGetter getter = new PropertyGetter(pt);

            String url = getter.getString(Names.config_default_remote_url, "");
            ContextAgent ca = com_man.find(ContextAgent.class);
            KeyPairManager kp_man = com_man.find(KeyPairManager.class);
            RepositoryManager repo_man = com_man.find(RepositoryManager.class);

            inst.setKeyPairManager(kp_man);
            inst.setRepositoryManager(repo_man);
            inst.setContextAgent(ca);
            inst.setDefaultRemoteURL(url);
        });
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addClass(ComLifecycle.class);
    }

    private static void config_auto_user_context_loader(ComponentSetBuilder csb) {
        ComponentProviderT<AutoUserContextLoader> provider = new ComponentProviderT<>();
        provider.setFactory(AutoUserContextLoader::new);
        provider.setWirer((ac, holder, inst) -> {

            ComponentManager com_man = ac.components();
            //   PropertyTable pt = ac.properties();
            //   PropertyGetter getter = new PropertyGetter(pt);

            ContextAgent ca = com_man.find(ContextAgent.class);

            inst.setContextAgent(ca);
        });
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addClass(ComLifecycle.class);
    }

}
