package com.github.milegema.mlgm4a.data.types;

import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.security.hash.Hash;
import com.github.milegema.mlgm4a.security.hash.HashUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class PIN {

    public enum Type {
        NONE, GRAPH, DIGIT
    }

    public static class Digest extends HexString {
        public Digest(byte[] bin) {
            super(bin);
        }
    }

    public static class Plain extends Text {
    }

    public static class Context {

        private Type type;
        private byte[] plain;
        private byte[] salt;
        private byte[] digest;

        public Context() {
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public byte[] getPlain() {
            return plain;
        }

        public void setPlain(byte[] plain) {
            this.plain = plain;
        }

        public byte[] getSalt() {
            return salt;
        }

        public void setSalt(byte[] salt) {
            this.salt = salt;
        }

        public byte[] getDigest() {
            return digest;
        }

        public void setDigest(byte[] digest) {
            this.digest = digest;
        }
    }

    public static PIN.Digest getDigest(Context ctx) {
        if (ctx == null) {
            return null;
        }
        return new PIN.Digest(ctx.digest);
    }

    public static void computeDigest(Context ctx) {
        if (ctx == null) {
            return;
        }
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            buffer.write(ctx.plain);
            buffer.write(ctx.salt);
        } catch (IOException e) {
            // throw new RuntimeException(e);
            Logs.warn("", e);
        }
        ctx.digest = HashUtils.sum(buffer.toByteArray(), Hash.SHA1);
    }
}
