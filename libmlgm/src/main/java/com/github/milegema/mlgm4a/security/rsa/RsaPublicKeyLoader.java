package com.github.milegema.mlgm4a.security.rsa;

import com.github.milegema.mlgm4a.security.PublicKeyEncoded;
import com.github.milegema.mlgm4a.security.PublicKeyInstance;
import com.github.milegema.mlgm4a.security.PublicKeyLoader;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RsaPublicKeyLoader implements PublicKeyLoader {

    private final RsaAlgorithmContext context;

    public RsaPublicKeyLoader(RsaAlgorithmContext ctx) {
        this.context = ctx;
    }

    @Override
    public PublicKeyInstance load(PublicKeyEncoded encoded) {
        RsaKeyContext ctx = new RsaKeyContext(this.context);
        try {
            String algorithm = this.context.getAlgorithm();
            ByteSlice der = encoded.getEncoded();
            X509EncodedKeySpec spec = new X509EncodedKeySpec(der.toByteArray());
            KeyFactory factory = KeyFactory.getInstance(algorithm);
            PublicKey pub = factory.generatePublic(spec);
            ctx.setPublicKey(pub);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ctx.setPublicInstance(new RsaPublicKeyInst(ctx));
        return ctx.getPublicInstance();
    }
}
