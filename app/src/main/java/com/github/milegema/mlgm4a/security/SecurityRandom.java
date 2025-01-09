package com.github.milegema.mlgm4a.security;

import java.util.Random;

public final class SecurityRandom {

    private static Random _inst;

    private SecurityRandom() {
    }

    public static Random initRandom() {
        long now = System.nanoTime();
        return new Random(now);
    }

    public static Random getRandom() {
        Random r = _inst;
        if (r == null) {
            r = initRandom();
            _inst = r;
        }
        return r;
    }
}
