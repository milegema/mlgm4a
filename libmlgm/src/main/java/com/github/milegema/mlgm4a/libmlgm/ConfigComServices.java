package com.github.milegema.mlgm4a.libmlgm;

import com.github.milegema.mlgm4a.components.ComponentHolderBuilder;
import com.github.milegema.mlgm4a.components.ComponentManager;
import com.github.milegema.mlgm4a.components.ComponentProviderT;
import com.github.milegema.mlgm4a.components.ComponentSetBuilder;
import com.github.milegema.mlgm4a.configurations.Configuration;
import com.github.milegema.mlgm4a.contexts.ContextAgent;
import com.github.milegema.mlgm4a.data.repositories.RepositoryManager;
import com.github.milegema.mlgm4a.security.KeyPairManager;
import com.github.milegema.mlgm4a.services.AuthService;
import com.github.milegema.mlgm4a.services.AuthServiceImpl;

final class ConfigComServices {

    public static void config_all(Configuration configuration) {
        final ComponentSetBuilder csb = configuration.getComponentSetBuilder();
        config_auth_service(csb);
    }

    private static void config_auth_service(ComponentSetBuilder csb) {
        ComponentProviderT<AuthServiceImpl> provider = new ComponentProviderT<>();
        provider.setFactory(AuthServiceImpl::new);
        provider.setWirer((ac, holder, inst) -> {

            ComponentManager com_man = ac.components();

            ContextAgent ctx_agent = com_man.find(ContextAgent.class);
            KeyPairManager kp_man = com_man.find(KeyPairManager.class);
            RepositoryManager repo_man = com_man.find(RepositoryManager.class);

            inst.setContextAgent(ctx_agent);
            inst.setKeyPairManager(kp_man);
            inst.setRepositoryManager(repo_man);
        });
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addAlias(AuthService.class);
    }
}
