package com.github.milegema.mlgm4a.security.rsa;

import com.github.milegema.mlgm4a.security.KeyPairProvider;
import com.github.milegema.mlgm4a.security.PublicKeyEncoded;
import com.github.milegema.mlgm4a.security.PublicKeyInstance;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.security.PublicKey;

public class RsaPublicKeyInst implements PublicKeyInstance {

    private final RsaKeyContext context;

    public RsaPublicKeyInst(RsaKeyContext ctx) {
        this.context = ctx;
    }

    @Override
    public KeyPairProvider provider() {
        return this.context.getParent().getProvider();
    }

    @Override
    public PublicKey key() {
        return this.context.getPublicKey();
    }

    @Override
    public PublicKeyEncoded export() {
        PublicKeyEncoded encoded = new PublicKeyEncoded();
        PublicKey pk = this.context.getPublicKey();
        byte[] bin = pk.getEncoded();
        encoded.setAlgorithm(pk.getAlgorithm());
        encoded.setFormat(pk.getFormat());
        encoded.setEncoded(new ByteSlice(bin));
        return encoded;
    }
}
