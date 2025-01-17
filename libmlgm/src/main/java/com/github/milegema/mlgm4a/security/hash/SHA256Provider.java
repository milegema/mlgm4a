package com.github.milegema.mlgm4a.security.hash;

import com.github.milegema.mlgm4a.security.HashProvider;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class SHA256Provider implements HashProvider {

    private final static String ALGORITHM = Hash.SHA256;

    public SHA256Provider() {
    }


    private static class MyHash implements Hash {

        private final InnerHashComputer.MessageDigestHolder md_holder;

        MyHash() {
            this.md_holder = new InnerHashComputer.MessageDigestHolder(ALGORITHM);
        }

        @Override
        public String algorithm() {
            return ALGORITHM;
        }

        @Override
        public Sum compute(byte[] data) {
            InputStream in = new ByteArrayInputStream(data);
            return this.compute(in);
        }

        @Override
        public Sum compute(ByteSlice d) {
            InputStream in = new ByteArrayInputStream(d.getData(), d.getOffset(), d.getLength());
            return this.compute(in);
        }

        @Override
        public synchronized Sum compute(InputStream in) {
            MessageDigest md = this.md_holder.get();
            byte[] sum = InnerHashComputer.compute(in, md);
            return new SHA256SUM(sum);
        }
    }

    @Override
    public String algorithm() {
        return ALGORITHM;
    }

    @Override
    public Hash hash() {
        return new MyHash();
    }

}
