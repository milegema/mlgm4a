package com.github.milegema.mlgm4a.security.hash;

import com.github.milegema.mlgm4a.utils.ByteSlice;
import com.github.milegema.mlgm4a.utils.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public final class HashUtils {

    private HashUtils() {
    }

    private static class HashInner {

        String algorithm;
        MessageDigest digest;

        HashInner(String alg) {
            this.algorithm = alg;
        }

        MessageDigest getMD() {
            MessageDigest md = this.digest;
            if (md != null) {
                return md;
            }
            try {
                md = MessageDigest.getInstance(this.algorithm);
                this.digest = md;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return md;
        }

        void append(String text) {
            if (text == null) {
                return;
            }
            byte[] bin = text.getBytes(StandardCharsets.UTF_8);
            this.append(bin);
        }

        void append(byte[] bin) {
            if (bin == null) {
                return;
            }
            MessageDigest md = this.getMD();
            md.update(bin);
        }

        void append(byte[] bin, int off, int len) {
            if (bin == null) {
                return;
            }
            MessageDigest md = this.getMD();
            md.update(bin, off, len);
        }


        byte[] sum() {
            MessageDigest md = this.getMD();
            return md.digest();
        }

        String sumHex() {
            byte[] s = this.sum();
            return Hex.stringify(s);
        }
    }

    public static byte[] sum(byte[] data, String algorithm) {
        HashInner h = new HashInner(algorithm);
        h.append(data);
        return h.sum();
    }

    public static String hexSum(byte[] data, String algorithm) {
        HashInner h = new HashInner(algorithm);
        h.append(data);
        return h.sumHex();
    }

    public static byte[] sum(String data, String algorithm) {
        HashInner h = new HashInner(algorithm);
        h.append(data);
        return h.sum();
    }

    public static byte[] sum(ByteSlice data, String algorithm) {
        HashInner h = new HashInner(algorithm);
        h.append(data.getData(), data.getOffset(), data.getLength());
        return h.sum();
    }

    public static String hexSum(String data, String algorithm) {
        HashInner h = new HashInner(algorithm);
        h.append(data);
        return h.sumHex();
    }
}
