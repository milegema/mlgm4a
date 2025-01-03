package com.github.milegema.mlgm4a;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.milegema.mlgm4a.logs.AndroidLogger;
import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.security.KeyFingerprint;
import com.github.milegema.mlgm4a.security.KeyPairAlias;
import com.github.milegema.mlgm4a.security.KeyPairHolder;
import com.github.milegema.mlgm4a.security.KeyPairManager;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.KeyPair;


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


        Assert.assertEquals("com.github.milegema.mlgm4a", appContext.getPackageName());
    }
}
