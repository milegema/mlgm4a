package com.github.milegema.mlgm4a.security.aes;

import com.github.milegema.mlgm4a.security.SecretKeyEncoded;
import com.github.milegema.mlgm4a.security.SecretKeyInstance;
import com.github.milegema.mlgm4a.security.SecretKeyLoader;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

final class AesKeyLoader implements SecretKeyLoader {

    private final AesAlgorithmContext context;

    public AesKeyLoader(AesAlgorithmContext ctx) {
        this.context = ctx;
    }

    @Override
    public SecretKeyInstance load(SecretKeyEncoded ske) {

        byte[] bin = ske.getEncoded().toByteArray();
        String algorithm = this.context.getAlgorithm();

        SecretKey spec = new SecretKeySpec(bin, algorithm);
        AesKeyContext ctx = new AesKeyContext(this.context);
        AesKeyInstance inst = new AesKeyInstance(ctx);

        ctx.setInstance(inst);
        ctx.setKey(spec);
        return inst;
    }
}
