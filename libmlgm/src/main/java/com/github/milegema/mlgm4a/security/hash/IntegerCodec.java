package com.github.milegema.mlgm4a.security.hash;

import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.security.PublicKey;

final class IntegerCodec {

    private IntegerCodec() {
    }

    public static void int32_to_bytes(int src, byte[] dst_buffer, int dst_offset) {
        Convertor32.int2bytes(src, dst_buffer, dst_offset);
    }

    public static int bytes_to_int32(byte[] buffer, int offset) {
        return Convertor32.bytes2int(buffer, offset);
    }

    public static void int64_to_bytes(long src, byte[] dst_buffer, int dst_offset) {
        Convertor64.long2bytes(src, dst_buffer, dst_offset);
    }

    public static long bytes_to_int64(byte[] buffer, int offset) {
        return Convertor64.bytes2long(buffer, offset);
    }

    // private

    private static class Convertor32 {

        private final static int LENGTH_IN_BYTE = 4;

        static int bytes2int(byte[] bin, int off) {
            int dst = 0;
            final int end1 = bin.length;
            final int end2 = off + LENGTH_IN_BYTE;
            final int end = Math.min(end1, end2);
            for (int i = off; isIndexInRange(i, off, end); i++) {
                byte b = bin[i];
                dst = (dst << 8) | (0xff & b);
            }
            return dst;
        }

        static void int2bytes(int src, byte[] bin, int off) {
            final int end1 = bin.length;
            final int end2 = off + LENGTH_IN_BYTE;
            final int end = Math.min(end1, end2);
            for (int i = end - 1; isIndexInRange(i, off, end); i--) {
                bin[i] = (byte) (0xff & src);
                src = src >> 8;
            }
        }

    }

    private static class Convertor64 {

        private final static int LENGTH_IN_BYTE = 8;

        static long bytes2long(byte[] bin, int off) {
            long dst = 0;
            final int end1 = bin.length;
            final int end2 = off + LENGTH_IN_BYTE;
            final int end = Math.min(end1, end2);
            for (int i = off; isIndexInRange(i, off, end); i++) {
                byte b = bin[i];
                dst = (dst << 8) | (0xff & b);
            }
            return dst;
        }

        static void long2bytes(long src, byte[] bin, int off) {
            final int end1 = bin.length;
            final int end2 = off + LENGTH_IN_BYTE;
            final int end = Math.min(end1, end2);
            for (int i = end - 1; isIndexInRange(i, off, end); i--) {
                bin[i] = (byte) (0xff & src);
                src = src >> 8;
            }
        }
    }

    private static boolean isIndexInRange(int index, int offset, int end) {
        return ((0 <= index) && (offset <= index) && (index < end));
    }
}
