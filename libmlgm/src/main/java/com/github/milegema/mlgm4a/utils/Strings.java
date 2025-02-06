package com.github.milegema.mlgm4a.utils;

import java.util.List;

public final class Strings {

    private Strings() {
    }

    public static boolean equals(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return (s1 == null && s2 == null);
        }
        return s1.equals(s2);
    }

    public static boolean equalsIgnoreCase(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return (s1 == null && s2 == null);
        }
        return s1.equalsIgnoreCase(s2);
    }

    public static void reverse(String[] array) {
        if (array == null) {
            return;
        }
        int i1 = 0;
        int i2 = array.length - 1;
        for (; i1 < i2; i1++, i2--) {
            String tmp1 = array[i1];
            String tmp2 = array[i2];
            array[i1] = tmp2;
            array[i2] = tmp1;
        }
    }

    public static void reverse(List<String> list) {
        if (list == null) {
            return;
        }
        int i1 = 0;
        int i2 = list.size() - 1;
        for (; i1 < i2; i1++, i2--) {
            String tmp1 = list.get(i1);
            String tmp2 = list.get(i2);
            list.set(i1, tmp2);
            list.set(i2, tmp1);
        }
    }

}
