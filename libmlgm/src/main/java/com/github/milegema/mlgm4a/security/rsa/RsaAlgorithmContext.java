package com.github.milegema.mlgm4a.security.rsa;

import com.github.milegema.mlgm4a.security.KeyPairProvider;
import com.github.milegema.mlgm4a.security.PrivateKeyGenerator;
import com.github.milegema.mlgm4a.security.PrivateKeyLoader;
import com.github.milegema.mlgm4a.security.PublicKeyLoader;

public class RsaAlgorithmContext {


    private String algorithm;
    private PrivateKeyGenerator generator;
    private PrivateKeyLoader privateKeyLoader;
    private PublicKeyLoader publicKeyLoader;
    private KeyPairProvider provider;

    public RsaAlgorithmContext() {
    }

    public KeyPairProvider getProvider() {
        return provider;
    }

    public void setProvider(KeyPairProvider provider) {
        this.provider = provider;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public PrivateKeyGenerator getGenerator() {
        return generator;
    }

    public void setGenerator(PrivateKeyGenerator generator) {
        this.generator = generator;
    }

    public PrivateKeyLoader getPrivateKeyLoader() {
        return privateKeyLoader;
    }

    public void setPrivateKeyLoader(PrivateKeyLoader privateKeyLoader) {
        this.privateKeyLoader = privateKeyLoader;
    }

    public PublicKeyLoader getPublicKeyLoader() {
        return publicKeyLoader;
    }

    public void setPublicKeyLoader(PublicKeyLoader publicKeyLoader) {
        this.publicKeyLoader = publicKeyLoader;
    }
}
