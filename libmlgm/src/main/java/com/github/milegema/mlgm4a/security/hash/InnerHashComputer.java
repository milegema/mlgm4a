package com.github.milegema.mlgm4a.security.hash;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

final class InnerHashComputer {

    private InnerHashComputer() {
    }


    public static class MessageDigestHolder {

        private final String algorithm;
        private MessageDigest messageDigest;

        public MessageDigestHolder(String _algorithm) {
            this.algorithm = _algorithm;
        }

        public MessageDigest get() {
            MessageDigest md = this.messageDigest;
            if (md != null) {
                return md;
            }
            try {
                md = MessageDigest.getInstance(this.algorithm);
                this.messageDigest = md;
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            return md;
        }
    }


    public static byte[] compute(InputStream in, MessageDigest md) {
        byte[] buffer = new byte[1024 * 4];
        md.reset();
        try {
            for (; ; ) {
                int cb = in.read(buffer);
                if (cb < 0) {
                    break;
                }
                md.update(buffer, 0, cb);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return md.digest();
    }
}
