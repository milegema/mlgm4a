package com.github.milegema.mlgm4a.security.aes;

import com.github.milegema.mlgm4a.security.SecretKeyGenerator;
import com.github.milegema.mlgm4a.security.SecretKeyLoader;
import com.github.milegema.mlgm4a.security.SecretKeyProvider;

public class AesProvider implements SecretKeyProvider {

    private final AesAlgorithmContext context;

    public AesProvider() {
        AesAlgorithmContext ctx = new AesAlgorithmContext();
        ctx.setProvider(this);
        ctx.setLoader(new AesKeyLoader(ctx));
        ctx.setGenerator(new AesKeyGenerator(ctx));
        ctx.setAlgorithm("AES");
        this.context = ctx;
    }

    @Override
    public String algorithm() {
        return this.context.getAlgorithm();
    }

    @Override
    public SecretKeyLoader loader() {
        return this.context.getLoader();
    }

    @Override
    public SecretKeyGenerator generator() {
        return this.context.getGenerator();
    }
}
