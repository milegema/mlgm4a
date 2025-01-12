package com.github.milegema.mlgm4a.security.rsa;

import com.github.milegema.mlgm4a.security.KeyPairAlias;
import com.github.milegema.mlgm4a.security.PrivateKeyEncoded;
import com.github.milegema.mlgm4a.security.PrivateKeyInstance;
import com.github.milegema.mlgm4a.security.PrivateKeyLoader;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

public class RsaPrivateKeyLoader implements PrivateKeyLoader {

    private final RsaAlgorithmContext context;

    public RsaPrivateKeyLoader(RsaAlgorithmContext ctx) {
        this.context = ctx;
    }

    private KeyPair inner_load_key_pair(KeyPairAlias key_pair_alias) throws CertificateException, IOException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableEntryException {
        String alias = String.valueOf(key_pair_alias);
        KeyStore store = KeyStore.getInstance("AndroidKeyStore");
        store.load(null);
        KeyStore.PrivateKeyEntry entry = (KeyStore.PrivateKeyEntry) store.getEntry(alias, null);
        PrivateKey pri = entry.getPrivateKey();
        PublicKey pub = entry.getCertificate().getPublicKey();
        return new KeyPair(pub, pri);
    }


    @Override
    public PrivateKeyInstance load(PrivateKeyEncoded encoded) {
        KeyPairAlias alias = RsaKeyAliasCodec.decode(encoded);
        RsaKeyContext ctx = new RsaKeyContext(this.context);
        try {
            KeyPair pair = this.inner_load_key_pair(alias);
            ctx.setKeyPair(pair);
            ctx.setPublicKey(pair.getPublic());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ctx.setAlias(alias);
        ctx.setPublicInstance(new RsaPublicKeyInst(ctx));
        ctx.setPairInstance(new RsaPrivateKeyInst(ctx));
        return ctx.getPairInstance();
    }
}
