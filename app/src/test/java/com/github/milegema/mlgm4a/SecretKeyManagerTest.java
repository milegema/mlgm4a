package com.github.milegema.mlgm4a;

import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.security.CipherMode;
import com.github.milegema.mlgm4a.security.CipherPadding;
import com.github.milegema.mlgm4a.security.SecretKeyAlias;
import com.github.milegema.mlgm4a.security.SecretKeyConfig;
import com.github.milegema.mlgm4a.security.SecretKeyContext;
import com.github.milegema.mlgm4a.security.SecretKeyHolder;
import com.github.milegema.mlgm4a.security.SecretKeyManager;
import com.github.milegema.mlgm4a.security.SecretKeyManagerImpl;
import com.github.milegema.mlgm4a.security.SecretKeyStore;
import com.github.milegema.mlgm4a.security.SimpleCipher;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.crypto.SecretKey;

public class SecretKeyManagerTest {


    @Test
    public void useSecretKeyManager() {


        SecretKeyStore store = SecretKeyStore.getInstance();
        SecretKeyAlias alias = new SecretKeyAlias("test");
        SecretKeyManager skm = new SecretKeyManagerImpl();
        SecretKeyConfig cfg = new SecretKeyConfig();

        cfg.setAlgorithm("AES");
        cfg.setSize(128);
        cfg.setStore(store);

        SecretKeyHolder holder = skm.get(alias, cfg);

        if (!holder.exists()) {
            holder.create();
        }

        SecretKey sk = holder.fetch();

        SimpleCipher ci = new SimpleCipher();
        ci.setAlgorithm(sk.getAlgorithm());
        ci.setPadding(CipherPadding.PKCS5Padding);
        ci.setMode(CipherMode.CBC);
        ci.setIv(new byte[16]);

        ByteSlice data1 = this.prepareMockData();
        ByteSlice data2 = ci.encrypt(data1, sk);
        ByteSlice data3 = ci.decrypt(data2, sk);


        Logs.debug("data1 = " + Arrays.toString(data1.toByteArray()));
        Logs.debug("data3 = " + Arrays.toString(data3.toByteArray()));
        Logs.debug("data2 = " + Arrays.toString(data2.toByteArray()));

        Assert.assertArrayEquals(data1.toByteArray(), data3.toByteArray());
    }

    private ByteSlice prepareMockData() {
        String text = "" + this;
        byte[] bin = text.getBytes(StandardCharsets.UTF_8);
        return new ByteSlice(bin);
    }
}
