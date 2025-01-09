package com.github.milegema.mlgm4a.security;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.milegema.mlgm4a.security.hash.Hash;
import com.github.milegema.mlgm4a.security.hash.SHA256SUM;

import java.security.PublicKey;

/****
 * 表示公钥指纹.  对于 PrivateKey 和 SecretKey, 应该使用与 PublicKey-Fingerprint 相等的值.
 * */
public final class KeyFingerprint {

    private final SHA256SUM sum;

    public KeyFingerprint() {
        this.sum = new SHA256SUM();
    }

    public KeyFingerprint(String str) {
        this.sum = new SHA256SUM(str);
    }

    public KeyFingerprint(byte[] b) {
        this.sum = new SHA256SUM(b);
    }

    @Override
    public int hashCode() {
        return sum.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (o instanceof KeyFingerprint) {
            final KeyFingerprint other = (KeyFingerprint) o;
            return other.sum.equals(this.sum);
        }
        return false;
    }

    public static KeyFingerprint compute(PublicKey pk) {
        byte[] data = pk.getEncoded();
        byte[] sum = Hash.sum(data, Hash.SHA256);
        return new KeyFingerprint(sum);
    }

    @NonNull
    @Override
    public String toString() {
        return sum.toString();
    }

    public byte[] toByteArray() {
        return sum.toByteArray();
    }
}
