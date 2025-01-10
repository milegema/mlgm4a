package com.github.milegema.mlgm4a.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ByteSlice {

    private byte[] data;
    private int offset;
    private int length;

    public ByteSlice() {
    }

    public ByteSlice(ByteSlice src) {
        if (src == null) {
            return;
        }
        this.data = src.data;
        this.offset = src.offset;
        this.length = src.length;
    }

    public ByteSlice(byte[] src) {
        if (src == null) {
            return;
        }
        this.data = src;
        this.offset = 0;
        this.length = src.length;
    }

    public ByteSlice(byte[] src, int off, int len) {
        if (src == null) {
            return;
        }
        this.data = src;
        this.offset = off;
        this.length = len;
    }

    public ByteSlice(String text) {
        if (text == null) {
            return;
        }
        byte[] bin = text.getBytes(StandardCharsets.UTF_8);
        this.data = bin;
        this.offset = 0;
        this.length = bin.length;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void writeTo(OutputStream dst) throws IOException {
        if (dst == null || data == null) {
            return;
        }
        dst.write(this.data, this.offset, this.length);
    }

    public byte[] toByteArray() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            this.writeTo(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return out.toByteArray();
    }
}
