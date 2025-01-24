package com.github.milegema.mlgm4a.utils;

public final class Strings {

    private Strings() {
    }

    public static boolean equals(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return (s1 == null && s2 == null);
        }
        return s1.equals(s2);
    }

}
