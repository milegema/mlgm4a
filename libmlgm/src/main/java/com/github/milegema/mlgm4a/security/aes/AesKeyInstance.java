package com.github.milegema.mlgm4a.security.aes;

import com.github.milegema.mlgm4a.security.SecretKeyEncoded;
import com.github.milegema.mlgm4a.security.SecretKeyInstance;
import com.github.milegema.mlgm4a.security.SecretKeyProvider;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import javax.crypto.SecretKey;

final class AesKeyInstance implements SecretKeyInstance {

    private final AesKeyContext context;

    public AesKeyInstance(AesKeyContext ctx) {
        this.context = ctx;
    }

    @Override
    public SecretKeyProvider provider() {
        return this.context.getParent().getProvider();
    }

    @Override
    public SecretKey key() {
        return this.context.getKey();
    }

    @Override
    public SecretKeyEncoded export() {
        SecretKey key = this.context.getKey();
        byte[] encoded = key.getEncoded();
        SecretKeyEncoded ske = new SecretKeyEncoded();
        ske.setAlgorithm(this.context.getParent().getAlgorithm());
        ske.setFormat(key.getFormat());
        ske.setEncoded(new ByteSlice(encoded));
        return ske;
    }
}
