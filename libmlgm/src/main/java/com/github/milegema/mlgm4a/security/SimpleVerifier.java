package com.github.milegema.mlgm4a.security;

import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

public final class SimpleVerifier {

    private PublicKey key;
    private ByteSlice content;

    /**
     * 这里的取值必须类似于 'SHA256withRSA' 的格式
     */
    private String algorithm;

    public SimpleVerifier() {
    }

    public PublicKey getKey() {
        return key;
    }

    public void setKey(PublicKey key) {
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


    public boolean check(ByteSlice signature) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature sig = Signature.getInstance(this.algorithm);
        sig.initVerify(this.key);
        sig.update(this.content.toByteArray());
        return sig.verify(signature.toByteArray());
    }

    public void verify(ByteSlice signature) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        if (check(signature)) {
            return;
        }
        throw new SignatureException("bad signature");
    }
}
