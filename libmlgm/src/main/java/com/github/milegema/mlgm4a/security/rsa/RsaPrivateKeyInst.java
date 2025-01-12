package com.github.milegema.mlgm4a.security.rsa;

import com.github.milegema.mlgm4a.security.KeyPairAlias;
import com.github.milegema.mlgm4a.security.KeyPairProvider;
import com.github.milegema.mlgm4a.security.PrivateKeyEncoded;
import com.github.milegema.mlgm4a.security.PrivateKeyInstance;
import com.github.milegema.mlgm4a.security.PublicKeyInstance;

import java.security.KeyPair;

public class RsaPrivateKeyInst implements PrivateKeyInstance {

    private final RsaKeyContext context;

    public RsaPrivateKeyInst(RsaKeyContext ctx) {
        this.context = ctx;
    }

    @Override
    public KeyPairProvider provider() {
        return this.context.getParent().getProvider();
    }

    @Override
    public PublicKeyInstance getPublic() {
        return this.context.getPublicInstance();
    }

    @Override
    public KeyPair pair() {
        return this.context.getKeyPair();
    }

    @Override
    public PrivateKeyEncoded export() {
        KeyPairAlias alias = this.context.getAlias();
        return RsaKeyAliasCodec.encode(alias);
    }
}
