package com.github.milegema.mlgm4a.security.rsa;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import com.github.milegema.mlgm4a.security.KeyPairAlias;
import com.github.milegema.mlgm4a.security.PrivateKeyGenerator;
import com.github.milegema.mlgm4a.security.PrivateKeyInstance;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;

public class RsaKeyGenImpl implements PrivateKeyGenerator {

    private final RsaAlgorithmContext context;

    public RsaKeyGenImpl(RsaAlgorithmContext ctx) {
        this.context = ctx;
    }

    private static class MyKeyAliasMaker {

        private int indexer;

        public synchronized KeyPairAlias next() {
            final long now = System.currentTimeMillis();
            final int idx = ++this.indexer;
            return new KeyPairAlias("rsa-" + now + '-' + idx);
        }
    }

    private final static MyKeyAliasMaker the_key_alias_maker = new MyKeyAliasMaker();

    private static KeyGenParameterSpec.Builder createNewKeyGenParameterSpecBuilder(KeyPairAlias alias) {

        long now = System.currentTimeMillis();
        Date t1 = new Date(now - 1000);
        Date t2 = new Date(now + (1000L * 199L * 365 * 24 * 3600));
        int purposes = KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY | KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT;
        int keySize = 1024 * 4;
        String alias_str = String.valueOf(alias);

        KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(alias_str, purposes);

        builder.setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                .setCertificateNotBefore(t1).setCertificateNotAfter(t2)
                .setKeySize(keySize);
        builder.setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1, KeyProperties.ENCRYPTION_PADDING_RSA_OAEP);
        builder.setBlockModes(KeyProperties.BLOCK_MODE_ECB, KeyProperties.BLOCK_MODE_CBC, KeyProperties.BLOCK_MODE_CTR, KeyProperties.BLOCK_MODE_GCM);
        builder.setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1, KeyProperties.SIGNATURE_PADDING_RSA_PSS);

        return builder;
    }

    private static KeyPair genKeyPair(KeyPairAlias alias) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore");
            kpg.initialize(createNewKeyGenParameterSpecBuilder(alias).build());
            KeyPair kp = kpg.generateKeyPair();
            if (kp == null) {
                throw new RuntimeException("result of generateKeyPair() is null");
            }
            return kp;
        } catch (NoSuchProviderException | NoSuchAlgorithmException |
                 InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public PrivateKeyInstance generate() {

        KeyPairAlias alias = the_key_alias_maker.next();
        KeyPair pair = genKeyPair(alias);
        RsaKeyContext ctx = new RsaKeyContext(this.context);

        ctx.setKeyPair(pair);
        ctx.setPairInstance(new RsaPrivateKeyInst(ctx));
        ctx.setPublicInstance(new RsaPublicKeyInst(ctx));
        ctx.setAlias(alias);
        ctx.setPublicKey(pair.getPublic());

        return ctx.getPairInstance();
    }
}
