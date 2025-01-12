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
import com.github.milegema.mlgm4a.utils.ByteSlice;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.util.Arrays;


@RunWith(AndroidJUnit4.class)
public class KeyPairManagerTest {

    @Test
    public void useKeyPairManager() {

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
        Logs.info("public-key     : " + pair.getPublic().getClass().getName());


        // cipher
        SimpleCipher ci = new SimpleCipher();
        ci.setAlgorithm(pair.getPublic().getAlgorithm());
        ci.setPadding(CipherPadding.PKCS1Padding);
        ci.setMode(CipherMode.NONE);
        // ci.setIv(new byte[16]);

        ByteSlice data1 = this.prepareMockData();
        ByteSlice data2 = ci.encrypt(data1, pair.getPublic());
        ByteSlice data3 = ci.decrypt(data2, pair.getPrivate());


        Logs.debug("data1 = " + Arrays.toString(data1.toByteArray()));
        Logs.debug("data3 = " + Arrays.toString(data3.toByteArray()));
        Logs.debug("data2 = " + Arrays.toString(data2.toByteArray()));

        Logs.debug("data1.len = " + data1.getLength());
        Logs.debug("data2.len = " + data2.getLength());

        Assert.assertArrayEquals(data1.toByteArray(), data3.toByteArray());
    }

    private ByteSlice prepareMockData() {
        String text = "" + this;
        byte[] bin = text.getBytes(StandardCharsets.UTF_8);
        return new ByteSlice(bin);
    }
}
