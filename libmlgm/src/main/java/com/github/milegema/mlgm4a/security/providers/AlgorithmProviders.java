package com.github.milegema.mlgm4a.security.providers;

import com.github.milegema.mlgm4a.security.AlgorithmManager;
import com.github.milegema.mlgm4a.security.AlgorithmProvider;
import com.github.milegema.mlgm4a.security.DefaultAlgorithmManager;
import com.github.milegema.mlgm4a.security.aes.AesProvider;
import com.github.milegema.mlgm4a.security.hash.MD5Provider;
import com.github.milegema.mlgm4a.security.hash.SHA1Provider;
import com.github.milegema.mlgm4a.security.hash.SHA256Provider;
import com.github.milegema.mlgm4a.security.rsa.RsaProvider;

import java.util.ArrayList;
import java.util.List;

public final class AlgorithmProviders {

    private AlgorithmProviders() {
    }

    public static AlgorithmProvider[] all() {
        List<AlgorithmProvider> list = new ArrayList<>();
        list.add(new AesProvider());
        list.add(new RsaProvider());

        list.add(new MD5Provider());
        list.add(new SHA1Provider());
        list.add(new SHA256Provider());

        return list.toArray(new AlgorithmProvider[0]);
    }

    public static AlgorithmManager getAlgorithmManager() {
        AlgorithmProvider[] sources = all();
        return new DefaultAlgorithmManager(sources);
    }
}
