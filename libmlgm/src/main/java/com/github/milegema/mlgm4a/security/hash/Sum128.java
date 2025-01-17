package com.github.milegema.mlgm4a.security.hash;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.milegema.mlgm4a.utils.Hex;

public class Sum128 implements Sum {

    private final static int LENGTH_IN_BIT = 128;
    private final static int BLOCK_SIZE_IN_BIT = 64; // sizeof(int64)
    private final static int BLOCK_SIZE_IN_BYTE = BLOCK_SIZE_IN_BIT / 8;
    private final static int LENGTH_IN_BYTE = LENGTH_IN_BIT / 8;
    private final static int BLOCK_COUNT_TOTAL = LENGTH_IN_BYTE / BLOCK_SIZE_IN_BYTE;

    // private final static int LENGTH_IN_CHAR = LENGTH_IN_BYTE * 2;

    private final long n0, n1;

    public Sum128() {
        this.n0 = 0;
        this.n1 = 0;
    }

    public Sum128(byte[] src) {
        Builder b = new Builder();
        b.init(src);
        this.n0 = b.n0;
        this.n1 = b.n1;
    }

    public Sum128(String src) {
        Builder b = new Builder();
        b.init(src);
        this.n0 = b.n0;
        this.n1 = b.n1;
    }

    private static class Builder {

        private long n0, n1;

        public void init(String str) {
            byte[] bin = Hex.parse(str);
            this.init(bin);
        }

        public void init(byte[] src) {
            long[] dst = new long[BLOCK_COUNT_TOTAL];
            for (int i = 0; i < dst.length; i++) {
                dst[i] = IntegerCodec.bytes_to_int64(src, BLOCK_SIZE_IN_BYTE * i);
            }
            this.n0 = dst[0];
            this.n1 = dst[1];
        }
    }


    @Override
    public byte[] toByteArray() {
        byte[] dst = new byte[LENGTH_IN_BYTE];
        long[] src = {n0, n1};
        for (int i = 0; i < src.length; i++) {
            IntegerCodec.int64_to_bytes(src[i], dst, BLOCK_SIZE_IN_BYTE * i);
        }
        return dst;
    }

    @Override
    public boolean equals(@Nullable Object o2) {
        if (o2 == null) {
            return false;
        }
        if (o2 instanceof Sum128) {
            final Sum128 other = (Sum128) o2;
            boolean b0 = (this.n0 == other.n0);
            boolean b1 = (this.n1 == other.n1);
            return (b0 && b1);
        }
        return false;
    }

    @Override
    public int hashCode() {
        String str = this.toString();
        return str.hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        byte[] bin = this.toByteArray();
        return Hex.stringify(bin);
    }
}
