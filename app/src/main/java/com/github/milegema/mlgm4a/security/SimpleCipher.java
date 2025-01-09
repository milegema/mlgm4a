package com.github.milegema.mlgm4a.security;

import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public final class SimpleCipher {

    private String provider; // optional
    private String algorithm; // required
    private CipherMode mode; // required
    private CipherPadding padding; // required
    private byte[] iv; // optional

    public SimpleCipher() {
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public CipherPadding getPadding() {
        return padding;
    }

    public void setPadding(CipherPadding padding) {
        this.padding = padding;
    }

    public CipherMode getMode() {
        return mode;
    }

    public void setMode(CipherMode mode) {
        this.mode = mode;
    }

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }

    private Cipher createNewCipher() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        String p = this.provider;
        String transformation = this.algorithm + '/' + this.mode + '/' + this.padding;
        if (p != null) {
            p = p.trim();
            if (!p.isEmpty()) {
                return Cipher.getInstance(transformation, p);
            }
        }
        return Cipher.getInstance(transformation);
    }

    private void prepare_iv(Cipher ci) {
        SimpleCipherIV iv_tool = new SimpleCipherIV();
        iv_tool.setCipher(ci);
        iv_tool.setParent(this);
        iv_tool.setIv(this.iv);
        iv_tool.apply();
        this.iv = iv_tool.getIv();
    }

    private void initCipher(Cipher ci, int op_mode, Key key) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.prepare_iv(ci);
        if (this.iv == null) {
            ci.init(op_mode, key);
        } else {
            IvParameterSpec ips = new IvParameterSpec(this.iv);
            ci.init(op_mode, key, ips);
        }
    }

    private void tryLoadDefaultParams(String final_algorithm) {
        CipherMode m = this.mode;
        CipherPadding p = this.padding;
        if (m == null) {
            m = CipherMode.NONE;
        }
        if (p == null) {
            p = CipherPadding.NoPadding;
        }
        this.algorithm = final_algorithm;
        this.mode = m;
        this.padding = p;
    }

    public ByteSlice encrypt(ByteSlice plain, PublicKey key) {
        this.tryLoadDefaultParams(key.getAlgorithm());
        try {
            Cipher ci = this.createNewCipher();
            this.initCipher(ci, Cipher.ENCRYPT_MODE, key);
            byte[] result = ci.doFinal(plain.getData(), plain.getOffset(), plain.getLength());
            return new ByteSlice(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ByteSlice encrypt(ByteSlice plain, SecretKey key) {
        this.tryLoadDefaultParams(key.getAlgorithm());
        try {
            Cipher ci = this.createNewCipher();
            this.initCipher(ci, Cipher.ENCRYPT_MODE, key);
            byte[] result = ci.doFinal(plain.getData(), plain.getOffset(), plain.getLength());
            return new ByteSlice(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ByteSlice decrypt(ByteSlice encrypted, SecretKey key) {
        this.tryLoadDefaultParams(key.getAlgorithm());
        try {
            Cipher ci = this.createNewCipher();
            this.initCipher(ci, Cipher.DECRYPT_MODE, key);
            byte[] result = ci.doFinal(encrypted.getData(), encrypted.getOffset(), encrypted.getLength());
            return new ByteSlice(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ByteSlice decrypt(ByteSlice encrypted, PrivateKey key) {
        this.tryLoadDefaultParams(key.getAlgorithm());
        try {
            Cipher ci = this.createNewCipher();
            this.initCipher(ci, Cipher.DECRYPT_MODE, key);
            byte[] result = ci.doFinal(encrypted.getData(), encrypted.getOffset(), encrypted.getLength());
            return new ByteSlice(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
