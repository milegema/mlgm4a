package com.github.milegema.mlgm4a.security;

import java.util.HashMap;
import java.util.Map;

public final class SecretKeyStore {

    private final Map<String, SecretKeyContext> keys;

    private SecretKeyStore() {
        this.keys = new HashMap<>();
    }

    public static SecretKeyStore getInstance() {
        return new SecretKeyStore();
    }

    private static String nameOf(SecretKeyAlias alias) {
        return String.valueOf(alias);
    }

    public void put(SecretKeyAlias alias, SecretKeyContext c) {
        final String name = nameOf(alias);
        this.keys.put(name, c);
    }

    public SecretKeyContext get(SecretKeyAlias alias) {
        final String name = nameOf(alias);
        return this.keys.get(name);
    }
}
