package com.github.milegema.mlgm4a.libmlgm;

import com.github.milegema.mlgm4a.components.ComponentHolderBuilder;
import com.github.milegema.mlgm4a.components.ComponentProviderT;
import com.github.milegema.mlgm4a.components.ComponentSetBuilder;
import com.github.milegema.mlgm4a.configurations.Configuration;
import com.github.milegema.mlgm4a.data.properties.PropertyGetter;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.network.web.WebClient;
import com.github.milegema.mlgm4a.network.web.WebClientFacade;
import com.github.milegema.mlgm4a.security.AlgorithmProvider;
import com.github.milegema.mlgm4a.security.KeyPairManager;
import com.github.milegema.mlgm4a.security.KeyPairManagerImpl;
import com.github.milegema.mlgm4a.security.aes.AesProvider;
import com.github.milegema.mlgm4a.security.hash.MD5Provider;
import com.github.milegema.mlgm4a.security.hash.SHA1Provider;
import com.github.milegema.mlgm4a.security.hash.SHA256Provider;
import com.github.milegema.mlgm4a.security.rsa.RsaProvider;

final class ConfigComSecurity {

    public static void config_all(Configuration configuration) {
        final ComponentSetBuilder csb = configuration.getComponentSetBuilder();

        config_key_pair_manager(csb);

        config_rsa_provider(csb);
        config_aes_provider(csb);

        config_md5_provider(csb);
        config_sha1_provider(csb);
        config_sha256_provider(csb);
    }


    static void config_key_pair_manager(ComponentSetBuilder csb) {
        ComponentProviderT<KeyPairManagerImpl> provider = new ComponentProviderT<>();
        provider.setFactory(KeyPairManagerImpl::new);
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addAlias(KeyPairManager.class);
    }


    static void config_md5_provider(ComponentSetBuilder csb) {
        ComponentProviderT<MD5Provider> provider = new ComponentProviderT<>();
        provider.setFactory(MD5Provider::new);
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addClass(AlgorithmProvider.class);
    }

    static void config_sha1_provider(ComponentSetBuilder csb) {
        ComponentProviderT<SHA1Provider> provider = new ComponentProviderT<>();
        provider.setFactory(SHA1Provider::new);
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addClass(AlgorithmProvider.class);
    }

    static void config_sha256_provider(ComponentSetBuilder csb) {
        ComponentProviderT<SHA256Provider> provider = new ComponentProviderT<>();
        provider.setFactory(SHA256Provider::new);
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addClass(AlgorithmProvider.class);
    }


    static void config_rsa_provider(ComponentSetBuilder csb) {
        ComponentProviderT<RsaProvider> provider = new ComponentProviderT<>();
        provider.setFactory(RsaProvider::new);
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addClass(AlgorithmProvider.class);
    }

    static void config_aes_provider(ComponentSetBuilder csb) {
        ComponentProviderT<AesProvider> provider = new ComponentProviderT<>();
        provider.setFactory(AesProvider::new);
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addClass(AlgorithmProvider.class);
    }

}
