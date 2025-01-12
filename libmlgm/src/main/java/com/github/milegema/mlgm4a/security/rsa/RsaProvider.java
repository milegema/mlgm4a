package com.github.milegema.mlgm4a.security.rsa;

import com.github.milegema.mlgm4a.security.PrivateKeyGenerator;
import com.github.milegema.mlgm4a.security.PrivateKeyLoader;
import com.github.milegema.mlgm4a.security.KeyPairProvider;
import com.github.milegema.mlgm4a.security.PublicKeyLoader;

public class RsaProvider implements KeyPairProvider {

    private final RsaAlgorithmContext context;

    public RsaProvider() {
        RsaAlgorithmContext ctx = initContext();
        ctx.setProvider(this);
        this.context = ctx;
    }


    private static RsaAlgorithmContext initContext() {
        RsaAlgorithmContext ctx = new RsaAlgorithmContext();
        ctx.setAlgorithm("RSA");
        ctx.setGenerator(new RsaKeyGenImpl(ctx));
        ctx.setPrivateKeyLoader(new RsaPrivateKeyLoader(ctx));
        ctx.setPublicKeyLoader(new RsaPublicKeyLoader(ctx));
        return ctx;
    }

    @Override
    public String algorithm() {
        return this.context.getAlgorithm();
    }

    @Override
    public PrivateKeyLoader loader() {
        return this.context.getPrivateKeyLoader();
    }

    @Override
    public PublicKeyLoader publicKeyLoader() {
        return this.context.getPublicKeyLoader();
    }

    @Override
    public PrivateKeyGenerator generator() {
        return this.context.getGenerator();
    }
}
