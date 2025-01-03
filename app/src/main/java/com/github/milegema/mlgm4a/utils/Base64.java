package com.github.milegema.mlgm4a.utils;

import java.nio.charset.StandardCharsets;

public final class Base64 {

    private Base64() {
    }

    public static String encode(byte[] data) {
        if (data == null) {
            return "";
        }
        byte[] str = java.util.Base64.getEncoder().encode(data);
        return new String(str, StandardCharsets.UTF_8);
    }

    public static byte[] decode(String b64str) {
        if (b64str == null) {
            return new byte[0];
        }
        return java.util.Base64.getDecoder().decode(b64str);
    }
}
