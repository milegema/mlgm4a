package com.github.milegema.mlgm4a.libmlgm;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.milegema.mlgm4a.security.AlgorithmManager;
import com.github.milegema.mlgm4a.security.AlgorithmProvider;
import com.github.milegema.mlgm4a.security.CipherMode;
import com.github.milegema.mlgm4a.security.CipherPadding;
import com.github.milegema.mlgm4a.security.PrivateKeyEncoded;
import com.github.milegema.mlgm4a.security.PrivateKeyInstance;
import com.github.milegema.mlgm4a.security.KeyPairProvider;
import com.github.milegema.mlgm4a.security.PublicKeyEncoded;
import com.github.milegema.mlgm4a.security.PublicKeyInstance;
import com.github.milegema.mlgm4a.security.SecretKeyEncoded;
import com.github.milegema.mlgm4a.security.SecretKeyInstance;
import com.github.milegema.mlgm4a.security.SecretKeyProvider;
import com.github.milegema.mlgm4a.security.SecurityRandom;
import com.github.milegema.mlgm4a.security.SimpleCipher;
import com.github.milegema.mlgm4a.security.SimpleSigner;
import com.github.milegema.mlgm4a.security.SimpleVerifier;
import com.github.milegema.mlgm4a.security.providers.AlgorithmProviders;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

@RunWith(AndroidJUnit4.class)
public class AlgorithmProvidersTest {

    @Test
    public void useAlgorithmProviders() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        AlgorithmManager am = AlgorithmProviders.getAlgorithmManager();
        String[] algorithm_list = {"aes", "rsa"};
        for (String algorithm : algorithm_list) {
            AlgorithmProvider ap = am.findProvider(algorithm);
            if (ap instanceof SecretKeyProvider) {
                this.tryUseSecretKeyProvider((SecretKeyProvider) ap);
            } else if (ap instanceof KeyPairProvider) {
                this.tryUseKeyPairProvider((KeyPairProvider) ap);
            }
        }
    }

    private void tryUseKeyPairProvider(KeyPairProvider kpp) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        PrivateKeyInstance inst1 = kpp.generator().generate();
        PrivateKeyEncoded encoded_kp = inst1.export();
        PublicKeyEncoded encoded_pub = inst1.getPublic().export();

        PrivateKeyInstance pair = kpp.loader().load(encoded_kp);
        PublicKeyInstance pub_key = kpp.publicKeyLoader().load(encoded_pub);

        // sign
        ////// todo ...

        SimpleSigner signer = new SimpleSigner();
        SimpleVerifier verifier = new SimpleVerifier();
        byte[] data_to_sign = new byte[99999];
        SecurityRandom.getRandom().nextBytes(data_to_sign);
        String sign_algorithm = "SHA256with" + inst1.provider().algorithm();

        signer.setKey(pair.pair().getPrivate());
        signer.setAlgorithm(sign_algorithm);
        signer.setContent(new ByteSlice(data_to_sign));

        verifier.setKey(pub_key.key());
        verifier.setAlgorithm(sign_algorithm);
        verifier.setContent(new ByteSlice(data_to_sign));

        ByteSlice signature = signer.sign();
        verifier.verify(signature);

        // cipher
        byte[] data1 = new byte[99];
        SecurityRandom.getRandom().nextBytes(data1);

        SimpleCipher cipher = new SimpleCipher();
        cipher.setAlgorithm(kpp.algorithm());
        cipher.setPadding(CipherPadding.PKCS1Padding);
        cipher.setMode(CipherMode.NONE);

        ByteSlice tmp = cipher.encrypt(new ByteSlice(data1), pub_key.key());
        byte[] data2 = cipher.decrypt(tmp, pair.pair().getPrivate()).toByteArray();

        Assert.assertArrayEquals(data1, data2);
    }

    private void tryUseSecretKeyProvider(SecretKeyProvider skp) {

        SecretKeyInstance inst1 = skp.generator().generate();
        SecretKeyEncoded encoded = inst1.export();
        SecretKeyInstance inst2 = skp.loader().load(encoded);


        byte[] data1 = new byte[999];
        SecurityRandom.getRandom().nextBytes(data1);


        SimpleCipher cipher = new SimpleCipher();
        cipher.setAlgorithm(skp.algorithm());
        cipher.setPadding(CipherPadding.PKCS7Padding);
        cipher.setMode(CipherMode.CBC);

        ByteSlice tmp = cipher.encrypt(new ByteSlice(data1), inst1.key());
        byte[] data2 = cipher.decrypt(tmp, inst2.key()).toByteArray();

        Assert.assertArrayEquals(data1, data2);
    }

}
