package com.github.milegema.mlgm4a.security;

import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

public class SimpleSigner {

    private PrivateKey key;
    private ByteSlice content;


    /**
     * 这里的取值必须类似于 'SHA256withRSA' 的格式
     */
    private String algorithm;

    public SimpleSigner() {
    }

    public PrivateKey getKey() {
        return key;
    }

    public void setKey(PrivateKey key) {
        this.key = key;
    }


    public ByteSlice getContent() {
        return content;
    }

    public void setContent(ByteSlice content) {
        this.content = content;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public ByteSlice sign() throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        Signature signature = Signature.getInstance(this.algorithm);
        signature.initSign(this.key);
        signature.update(this.content.toByteArray());
        byte[] result = signature.sign();
        return new ByteSlice(result);
    }
}
