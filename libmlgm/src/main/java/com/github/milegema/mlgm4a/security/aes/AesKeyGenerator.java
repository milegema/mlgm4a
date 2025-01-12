package com.github.milegema.mlgm4a.security.aes;

import com.github.milegema.mlgm4a.security.SecretKeyGenerator;
import com.github.milegema.mlgm4a.security.SecretKeyInstance;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

final class AesKeyGenerator implements SecretKeyGenerator {

    private final AesAlgorithmContext context;

    public AesKeyGenerator(AesAlgorithmContext ctx) {
        this.context = ctx;
    }

    @Override
    public SecretKeyInstance generate() {
        final String algorithm = "AES";
        final int size = 256;
        AesKeyContext ctx = new AesKeyContext(this.context);
        AesKeyInstance inst = new AesKeyInstance(ctx);
        ctx.setInstance(inst);
        try {
            KeyGenerator generator = KeyGenerator.getInstance(algorithm);
            generator.init(size);
            SecretKey sk = generator.generateKey();
            ctx.setKey(sk);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return inst;
    }
}
