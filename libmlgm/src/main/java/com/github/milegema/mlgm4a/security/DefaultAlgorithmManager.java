package com.github.milegema.mlgm4a.security;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DefaultAlgorithmManager implements AlgorithmManager {

    private final Map<String, AlgorithmProvider> table;

    public DefaultAlgorithmManager(AlgorithmProvider[] all) {
        Map<String, AlgorithmProvider> t = initTable(all);
        this.table = Collections.synchronizedMap(t);
    }

    private static Map<String, AlgorithmProvider> initTable(AlgorithmProvider[] src) {
        Map<String, AlgorithmProvider> dst = new HashMap<>();
        if (src == null) {
            return dst;
        }
        for (AlgorithmProvider ap : src) {
            if (ap == null) {
                continue;
            }
            String algorithm = ap.algorithm();
            algorithm = normalizeAlgorithmName(algorithm);
            dst.put(algorithm, ap);
        }
        return dst;
    }

    private static String normalizeAlgorithmName(String algorithm) {
        if (algorithm == null) {
            algorithm = "undefined";
        }
        return algorithm.trim().toUpperCase();
    }

    @Override
    public AlgorithmProvider findProvider(String algorithm) {
        algorithm = normalizeAlgorithmName(algorithm);
        AlgorithmProvider ap = this.table.get(algorithm);
        if (ap == null) {
            throw new SecurityException("unsupported algorithm: " + algorithm);
        }
        return ap;
    }

    @Override
    public KeyPairProvider findKeyPairProvider(String algorithm) {
        AlgorithmProvider ap = this.findProvider(algorithm);
        return (KeyPairProvider) ap;
    }

    @Override
    public SecretKeyProvider findSecretKeyProvider(String algorithm) {
        AlgorithmProvider ap = this.findProvider(algorithm);
        return (SecretKeyProvider) ap;
    }

    @Override
    public HashProvider findHashProvider(String algorithm) {
        AlgorithmProvider ap = this.findProvider(algorithm);
        return (HashProvider) ap;
    }
}
