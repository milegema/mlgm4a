package com.github.milegema.mlgm4a.security.hash;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.milegema.mlgm4a.utils.Hex;

public class Sum160 implements Sum {

    private final static int LENGTH_IN_BIT = 160;
    private final static int BLOCK_SIZE_IN_BIT = 32; // sizeof(int32)
    private final static int BLOCK_SIZE_IN_BYTE = BLOCK_SIZE_IN_BIT / 8;
    private final static int LENGTH_IN_BYTE = LENGTH_IN_BIT / 8;
    private final static int BLOCK_COUNT_TOTAL = LENGTH_IN_BYTE / BLOCK_SIZE_IN_BYTE;

    // private final static int LENGTH_IN_CHAR = LENGTH_IN_BYTE * 2;

    private final int n0, n1, n2, n3, n4;

    public Sum160() {
        this.n0 = 0;
        this.n1 = 0;
        this.n2 = 0;
        this.n3 = 0;
        this.n4 = 0;
    }

    public Sum160(String src) {
        Builder b = new Builder();
        b.init(src);
        this.n0 = b.n0;
        this.n1 = b.n1;
        this.n2 = b.n2;
        this.n3 = b.n3;
        this.n4 = b.n4;
    }

    public Sum160(byte[] src) {
        Builder b = new Builder();
        b.init(src);
        this.n0 = b.n0;
        this.n1 = b.n1;
        this.n2 = b.n2;
        this.n3 = b.n3;
        this.n4 = b.n4;
    }

    private static class Builder {

        private int n0, n1, n2, n3, n4;

        public void init(String str) {
            byte[] bin = Hex.parse(str);
            this.init(bin);
        }

        public void init(byte[] src) {
            int[] dst = new int[BLOCK_COUNT_TOTAL];
            for (int i = 0; i < dst.length; i++) {
                dst[i] = IntegerCodec.bytes_to_int32(src, BLOCK_SIZE_IN_BYTE * i);
            }
            this.n0 = dst[0];
            this.n1 = dst[1];
            this.n2 = dst[2];
            this.n3 = dst[3];
            this.n4 = dst[4];
        }
    }


    @Override
    public byte[] toByteArray() {
        byte[] dst = new byte[LENGTH_IN_BYTE];
        int[] src = {n0, n1, n2, n3, n4};
        for (int i = 0; i < src.length; i++) {
            IntegerCodec.int32_to_bytes(src[i], dst, BLOCK_SIZE_IN_BYTE * i);
        }
        return dst;
    }

    @Override
    public boolean equals(@Nullable Object o2) {
        if (o2 == null) {
            return false;
        }
        if (o2 instanceof Sum160) {
            final Sum160 other = (Sum160) o2;
            boolean b0 = (this.n0 == other.n0);
            boolean b1 = (this.n1 == other.n1);
            boolean b2 = (this.n2 == other.n2);
            boolean b3 = (this.n3 == other.n3);
            boolean b4 = (this.n4 == other.n4);
            return (b0 && b1 && b2 && b3 && b4);
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
