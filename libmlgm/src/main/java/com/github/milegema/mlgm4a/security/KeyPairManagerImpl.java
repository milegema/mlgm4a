package com.github.milegema.mlgm4a.security;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import com.github.milegema.mlgm4a.errors.Errors;
import com.github.milegema.mlgm4a.security.hash.Hash;
import com.github.milegema.mlgm4a.security.hash.HashUtils;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

public class KeyPairManagerImpl implements KeyPairManager {


    @Override
    public KeyPairHolder get(KeyPairAlias alias) {
        return new MyKeyHolder(alias);
    }

    @Override
    public KeyPairHolder getRoot() {
        return this.get(KeyPairAlias.root());
    }

    @Override
    public KeyPairAlias[] listAliases() {
        List<KeyPairAlias> dst = new ArrayList<>();
        try {
            KeyStore store = getKeyStore();
            Enumeration<String> src = store.aliases();
            while (src.hasMoreElements()) {
                String al = src.nextElement();
                dst.add(KeyPairAlias.parse(al));
            }
        } catch (CertificateException | KeyStoreException | NoSuchAlgorithmException |
                 IOException e) {
            Errors.handle(null, e);
        }
        return dst.toArray(new KeyPairAlias[0]);
    }


    private static class MyKeyHolder implements KeyPairHolder {

        private final KeyPairAlias mAlias;
        private KeyPair mKeyPair;
        private KeyFingerprint mFinger;

        public MyKeyHolder(KeyPairAlias alias) {
            this.mAlias = KeyPairAlias.forAlias(alias);
        }

        @Override
        public KeyPairAlias alias() {
            return this.mAlias;
        }

        @Override
        public KeyFingerprint fingerprint() {
            KeyFingerprint fp = mFinger;
            if (fp == null) {
                fp = loadFingerprint(this);
                mFinger = fp;
            }
            return fp;
        }

        private KeyGenParameterSpec.Builder createNewKeyGenParameterSpecBuilder() {

            long now = System.currentTimeMillis();
            Date t1 = new Date(now - 1000);
            Date t2 = new Date(now + (1000L * 99L * 365 * 24 * 3600));
            int purposes = KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY | KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT;
            int keySize = 1024 * 4;
            String alias = this.mAlias.toString();

            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(alias, purposes);

            builder.setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                    .setCertificateNotBefore(t1).setCertificateNotAfter(t2)
                    .setKeySize(keySize);
            builder.setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1, KeyProperties.ENCRYPTION_PADDING_RSA_OAEP);
            builder.setBlockModes(KeyProperties.BLOCK_MODE_ECB, KeyProperties.BLOCK_MODE_CBC, KeyProperties.BLOCK_MODE_CTR, KeyProperties.BLOCK_MODE_GCM);
            builder.setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1, KeyProperties.SIGNATURE_PADDING_RSA_PSS);

            return builder;
        }


        @Override
        public boolean create() {
            try {
                KeyPairGenerator kpg = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore");
                kpg.initialize(this.createNewKeyGenParameterSpecBuilder().build());
                KeyPair kp = kpg.generateKeyPair();
                if (kp == null) {
                    throw new RuntimeException("result of generateKeyPair() is null");
                }
                return true;
            } catch (NoSuchProviderException | NoSuchAlgorithmException |
                     InvalidAlgorithmParameterException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public boolean delete() {
            try {
                String alias = this.mAlias.toString();
                KeyStore store = getKeyStore();
                store.deleteEntry(alias);
                return true;
            } catch (CertificateException | KeyStoreException | NoSuchAlgorithmException |
                     IOException e) {
                // throw new RuntimeException(e);
                Errors.handle(null, e);
                return false;
            }
        }

        @Override
        public boolean exists() {
            try {
                String alias = this.mAlias.toString();
                KeyStore store = getKeyStore();
                return store.containsAlias(alias);
            } catch (CertificateException | KeyStoreException | NoSuchAlgorithmException |
                     IOException e) {
                //  throw new RuntimeException(e);
                Errors.handle(null, e);
                return false;
            }
        }

        @Override
        public KeyPair fetch() {
            KeyPair kp = this.mKeyPair;
            if (kp != null) {
                return kp;
            }
            try {
                String alias = this.mAlias.toString();
                KeyStore store = getKeyStore();
                KeyStore.PrivateKeyEntry entry = (KeyStore.PrivateKeyEntry) store.getEntry(alias, null);
                PrivateKey pri = entry.getPrivateKey();
                PublicKey pub = entry.getCertificate().getPublicKey();
                kp = new KeyPair(pub, pri);
                this.mKeyPair = kp;
                return kp;
            } catch (CertificateException | KeyStoreException | NoSuchAlgorithmException |
                     UnrecoverableEntryException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static KeyFingerprint loadFingerprint(MyKeyHolder h) {
        PublicKey pub = h.fetch().getPublic();
        byte[] bin = pub.getEncoded();
        byte[] sum = HashUtils.sum(bin, Hash.SHA256);
        return new KeyFingerprint(sum);
    }

    private static KeyStore getKeyStore() throws CertificateException, IOException, NoSuchAlgorithmException, KeyStoreException {
        KeyStore store = KeyStore.getInstance("AndroidKeyStore");
        store.load(null);
        return store;
    }
}
