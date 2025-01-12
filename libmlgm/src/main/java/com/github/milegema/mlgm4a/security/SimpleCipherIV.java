package com.github.milegema.mlgm4a.security;

import java.security.Key;

import javax.crypto.Cipher;

final class SimpleCipherIV {

    private SimpleCipher parent;
    private Cipher cipher;
    private byte[] iv;

    public SimpleCipherIV() {
    }

    public SimpleCipher getParent() {
        return parent;
    }

    public void setParent(SimpleCipher parent) {
        this.parent = parent;
    }

    public Cipher getCipher() {
        return cipher;
    }

    public void setCipher(Cipher cipher) {
        this.cipher = cipher;
    }

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }


    public void apply() {
        if (is_iv_required(this.cipher)) {
            this.iv = prepare_iv(this.iv, this.cipher);
        } else {
            this.iv = null;
        }
    }

    //////////////////////////////////////////////////
    // private

    private static byte[] prepare_iv(byte[] iv, Cipher ci) {
        if (iv != null) {
            return iv;
        }
        int size = ci.getBlockSize();
        iv = new byte[size];
        SecurityRandom.getRandom().nextBytes(iv);
        return iv;
    }

    private static boolean is_iv_required(Cipher ci) {
        final String alg = ci.getAlgorithm().toLowerCase();
        final String[] req_list = {"/cbc/", "/cfb/"};
        for (String item : req_list) {
            if (alg.contains(item)) {
                return true;
            }
        }
        return false;
    }
}
