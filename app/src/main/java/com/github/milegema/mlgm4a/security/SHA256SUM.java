package com.github.milegema.mlgm4a.security;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.milegema.mlgm4a.utils.Hex;

/**
 * SHA256SUM 是一个 256 位的哈希值
 */
public final class SHA256SUM {

    private final long n0, n1, n2, n3;

    public SHA256SUM() {
        this.n0 = 0;
        this.n1 = 0;
        this.n2 = 0;
        this.n3 = 0;
    }

    public SHA256SUM(String str) {
        Builder b = new Builder();
        b.init(str);
        this.n0 = b.n0;
        this.n1 = b.n1;
        this.n2 = b.n2;
        this.n3 = b.n3;
    }

    public SHA256SUM(byte[] bin) {
        Builder b = new Builder();
        b.init(bin);
        this.n0 = b.n0;
        this.n1 = b.n1;
        this.n2 = b.n2;
        this.n3 = b.n3;
    }

    private SHA256SUM(Builder b) {
        this.n0 = b.n0;
        this.n1 = b.n1;
        this.n2 = b.n2;
        this.n3 = b.n3;
    }

    private final static int LENGTH_IN_BIT = 256;
    private final static int LENGTH_IN_BYTE = LENGTH_IN_BIT / 8;
    private final static int LENGTH_IN_CHAR = LENGTH_IN_BYTE * 2;
    private final static int NUM_STEP = 8;

    private static class Builder {

        private long n0, n1, n2, n3;

        public void init(String str) {
            byte[] bin = Hex.parse(str);
            this.init(bin);
        }

        public void init(byte[] bin) {
            final int step = NUM_STEP;
            this.n0 = Convertor.bytes2long(bin, 0);
            this.n1 = Convertor.bytes2long(bin, step);
            this.n2 = Convertor.bytes2long(bin, step * 2);
            this.n3 = Convertor.bytes2long(bin, step * 3);
        }
    }


    private static class Convertor {

        static boolean isIndexInRange(int index, int offset, int end) {
            return ((0 <= index) && (offset <= index) && (index < end));
        }

        public static long bytes2long(byte[] bin, int off) {
            long dst = 0;
            final int end1 = bin.length;
            final int end2 = off + NUM_STEP;
            final int end = Math.min(end1, end2);
            for (int i = off; isIndexInRange(i, off, end); i++) {
                byte b = bin[i];
                dst = (dst << 8) | (0xff & b);
            }
            return dst;
        }


        public static void long2bytes(long src, byte[] bin, int off) {
            final int end1 = bin.length;
            final int end2 = off + NUM_STEP;
            final int end = Math.min(end1, end2);
            for (int i = end - 1; isIndexInRange(i, off, end); i--) {
                bin[i] = (byte) (0xff & src);
                src = src >> 8;
            }
        }
    }


    public byte[] toByteArray() {
        byte[] bin = new byte[LENGTH_IN_BYTE];
        final int step = NUM_STEP;
        Convertor.long2bytes(this.n0, bin, 0);
        Convertor.long2bytes(this.n1, bin, step);
        Convertor.long2bytes(this.n2, bin, step * 2);
        Convertor.long2bytes(this.n3, bin, step * 3);
        return bin;
    }

    @Override
    public int hashCode() {
        String str = this.toString();
        return str.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object o2) {
        if (o2 == null) {
            return false;
        }
        if (o2 instanceof SHA256SUM) {
            final SHA256SUM other = (SHA256SUM) o2;
            boolean b0 = (this.n0 == other.n0);
            boolean b1 = (this.n1 == other.n1);
            boolean b2 = (this.n2 == other.n2);
            boolean b3 = (this.n3 == other.n3);
            return (b0 && b1 && b2 && b3);
        }
        return false;
    }


    @NonNull
    @Override
    public String toString() {
        byte[] bin = this.toByteArray();
        return Hex.stringify(bin);
    }
}
