package com.github.milegema.mlgm4a.security.aes;

import com.github.milegema.mlgm4a.security.SecretKeyAlias;
import com.github.milegema.mlgm4a.security.SecretKeyGenerator;
import com.github.milegema.mlgm4a.security.SecretKeyInstance;
import com.github.milegema.mlgm4a.security.SecretKeyLoader;
import com.github.milegema.mlgm4a.security.SecretKeyProvider;

import javax.crypto.SecretKey;

final class AesAlgorithmContext {

    private String algorithm;
    private SecretKeyProvider provider;
    private SecretKeyLoader loader;
    private SecretKeyGenerator generator;

    public AesAlgorithmContext() {
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public SecretKeyProvider getProvider() {
        return provider;
    }

    public void setProvider(SecretKeyProvider provider) {
        this.provider = provider;
    }

    public SecretKeyLoader getLoader() {
        return loader;
    }

    public void setLoader(SecretKeyLoader loader) {
        this.loader = loader;
    }

    public SecretKeyGenerator getGenerator() {
        return generator;
    }

    public void setGenerator(SecretKeyGenerator generator) {
        this.generator = generator;
    }
}
