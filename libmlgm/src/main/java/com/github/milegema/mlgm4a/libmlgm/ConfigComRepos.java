package com.github.milegema.mlgm4a.libmlgm;

import com.github.milegema.mlgm4a.components.ComponentHolderBuilder;
import com.github.milegema.mlgm4a.components.ComponentProviderT;
import com.github.milegema.mlgm4a.components.ComponentSetBuilder;
import com.github.milegema.mlgm4a.configurations.Configuration;
import com.github.milegema.mlgm4a.contexts.ContextAgent;
import com.github.milegema.mlgm4a.data.repositories.AndroidRepositoriesFolder;
import com.github.milegema.mlgm4a.data.repositories.DefaultRepositoryFactory;
import com.github.milegema.mlgm4a.data.repositories.DefaultRepositoryManager;
import com.github.milegema.mlgm4a.data.repositories.RepositoriesFolder;
import com.github.milegema.mlgm4a.data.repositories.RepositoryFactory;
import com.github.milegema.mlgm4a.data.repositories.RepositoryFactoryContext;
import com.github.milegema.mlgm4a.data.repositories.RepositoryManager;
import com.github.milegema.mlgm4a.data.repositories.tables.Schema;

final class ConfigComRepos {

    public static void config_all(Configuration configuration) {

        final ComponentSetBuilder csb = configuration.getComponentSetBuilder();
        final MyLoader loader = new MyLoader();

        loader.load();

        config_repo_manager(csb, loader);
        config_repositories_folder(csb, loader);
        config_repo_factory(csb, loader);
        config_rfc(csb, loader);
    }


    static class MyLoader {

        DefaultRepositoryFactory m_factory;
        DefaultRepositoryManager m_manager;
        AndroidRepositoriesFolder m_folder;
        RepositoryFactoryContext m_rfc;

        void load() {

            RepositoryFactoryContext rf_ctx = new RepositoryFactoryContext();
            DefaultRepositoryFactory factory = new DefaultRepositoryFactory();
            DefaultRepositoryManager manager = new DefaultRepositoryManager();
            AndroidRepositoriesFolder folder = new AndroidRepositoriesFolder();

            factory.setRfc(rf_ctx);

            folder.setContextAgent(null);

            manager.setRepositoryFactory(factory);
            manager.setRepositoriesFolder(folder);

            rf_ctx.setFactory(factory);
            rf_ctx.setSchema(null);

            this.m_folder = folder;
            this.m_manager = manager;
            this.m_factory = factory;
            this.m_rfc = rf_ctx;
        }
    }

    private static void config_repo_factory(ComponentSetBuilder csb, MyLoader loader) {
        ComponentProviderT<DefaultRepositoryFactory> provider = new ComponentProviderT<>();
        provider.setFactory(() -> loader.m_factory);
        provider.setWirer((ac, holder, inst) -> {
        });
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addAlias(RepositoryFactory.class);
    }


    private static void config_repo_manager(ComponentSetBuilder csb, MyLoader loader) {
        ComponentProviderT<DefaultRepositoryManager> provider = new ComponentProviderT<>();
        provider.setFactory(() -> loader.m_manager);
        provider.setWirer((ac, holder, inst) -> {
        });
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addAlias(RepositoryManager.class);
    }


    private static void config_repositories_folder(ComponentSetBuilder csb, MyLoader loader) {
        ComponentProviderT<AndroidRepositoriesFolder> provider = new ComponentProviderT<>();
        provider.setFactory(() -> loader.m_folder);
        provider.setWirer((ac, holder, inst) -> {
            ContextAgent ctx_agent = ac.components().find(ContextAgent.class);
            inst.setContextAgent(ctx_agent);
        });
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addAlias(RepositoriesFolder.class);
    }


    private static void config_rfc(ComponentSetBuilder csb, MyLoader loader) {
        ComponentProviderT<RepositoryFactoryContext> provider = new ComponentProviderT<>();
        provider.setFactory(() -> loader.m_rfc);
        provider.setWirer((ac, holder, inst) -> {
            Schema schema = ac.components().find(Schema.class);
            inst.setSchema(schema);
        });
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addAlias(RepositoryFactoryContext.class);
    }

}
