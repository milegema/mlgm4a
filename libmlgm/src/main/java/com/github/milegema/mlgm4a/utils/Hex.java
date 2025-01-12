package com.github.milegema.mlgm4a.utils;

import java.io.ByteArrayOutputStream;

public final class Hex {

    private static final char[] charset = "0123456789abcdef".toCharArray();

    private Hex() {
    }

    public static String stringify(byte[] data) {
        if (data == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (byte b : data) {
            builder.append(charset[0x0f & (b >> 4)]);
            builder.append(charset[0x0f & b]);
        }
        return builder.toString();
    }

    public static byte[] parse(String text) {
        if (text == null) {
            return new byte[0];
        }
        char[] src = text.toCharArray();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int bit4current, bit4prev, bit4count, limit;
        bit4count = bit4current = bit4prev = 0;
        limit = 1024 * 1024 * 16;
        for (char ch : src) {
            if (('0' <= ch) && (ch <= '9')) {
                bit4current = ch - '0';
            } else if (('a' <= ch) && (ch <= 'f')) {
                bit4current = ch - 'a' + 0x0a;
            } else if (('A' <= ch) && (ch <= 'F')) {
                bit4current = ch - 'A' + 0x0a;
            } else if (ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r') {
                continue;
            } else {
                throw new NumberFormatException("bad hex char: '" + ch + "'");
            }

            if ((bit4count & 0x01) == 1) {
                out.write((0xf0 & (bit4prev << 4)) | (0x0f & bit4current));
            }
            if (bit4count > limit) {
                throw new RuntimeException("hex string is too long, limit = " + limit);
            }

            bit4count++;
            bit4prev = bit4current;
        }
        return out.toByteArray();
    }

    public static boolean isHexString(String str) {
        if (str == null) {
            return false;
        }
        char[] array = str.trim().toCharArray();
        for (char ch : array) {
            if ('0' <= ch && ch <= '9') {
                continue;
            } else if ('a' <= ch && ch <= 'f') {
                continue;
            } else if ('A' <= ch && ch <= 'F') {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }
}
