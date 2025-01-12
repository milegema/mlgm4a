package com.github.milegema.mlgm4a.libmlgm;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.milegema.mlgm4a.logs.AndroidLogger;
import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.security.CipherMode;
import com.github.milegema.mlgm4a.security.CipherPadding;
import com.github.milegema.mlgm4a.security.KeyFingerprint;
import com.github.milegema.mlgm4a.security.KeyPairAlias;
import com.github.milegema.mlgm4a.security.KeyPairHolder;
import com.github.milegema.mlgm4a.security.KeyPairManager;
import com.github.milegema.mlgm4a.security.SimpleCipher;
import com.github.milegema.mlgm4a.security.SimpleSigner;
import com.github.milegema.mlgm4a.security.SimpleVerifier;
import com.github.milegema.mlgm4a.security.hash.Hash;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.Random;


@RunWith(AndroidJUnit4.class)
public class KeyPairSignatureBenchmarkTest {

    @Test
    public void useKeyPairManager() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        AndroidLogger.init();

        // Context of the app under test.
        Context appContext = ApplicationProvider.getApplicationContext();


        KeyPairManager kpm = KeyPairManager.Agent.getKeyPairManager();
        // kpm.getRoot() ;
        KeyPairHolder holder = kpm.get(new KeyPairAlias("test"));
        if (!holder.exists()) {
            holder.create();
        }
        KeyPair pair = holder.fetch();
        KeyFingerprint fp = holder.fingerprint();

        Logs.info("KeyFingerprint : " + fp);

        final long t1 = System.currentTimeMillis();
        final int total = 1000;
        for (int count = 0; count < total; count++) {
            ByteSlice data = this.prepareMockData();
            // byte[] sum = Hash.sum(data, Hash.SHA256);
            ByteSlice signature = this.sign(data.toByteArray(), pair);
            this.verify(signature, data.toByteArray(), pair);
        }
        final long t2 = System.currentTimeMillis();
        long span = t2 - t1;

        Logs.info("[" + this + " span:" + span + "(ms) count:" + total + "]");
    }

    private ByteSlice sign(byte[] content, KeyPair pair) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        SimpleSigner ss = new SimpleSigner();
        ss.setAlgorithm(SIGNATURE_ALGORITHM);
        ss.setKey(pair.getPrivate());
        ss.setContent(new ByteSlice(content));
        return ss.sign();
    }

    private final static String SIGNATURE_ALGORITHM = "SHA256withRSA";

    private void verify(ByteSlice signature, byte[] content, KeyPair pair) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        Logs.debug("signature.bin.length: " + signature.getLength());

        SimpleVerifier v = new SimpleVerifier();
        v.setAlgorithm(SIGNATURE_ALGORITHM);
        v.setKey(pair.getPublic());
        v.setContent(new ByteSlice(content));
        v.verify(signature);
    }


    private Random mRandom;

    private ByteSlice prepareMockData() {
        Random rand = this.mRandom;
        if (rand == null) {
            long now = System.currentTimeMillis();
            rand = new Random(now);
            this.mRandom = rand;
        }
        byte[] buffer = new byte[32];
        rand.nextBytes(buffer);
        return new ByteSlice(buffer);
    }
}
