package com.github.milegema.mlgm4a.security.rsa;

import com.github.milegema.mlgm4a.security.KeyPairAlias;
import com.github.milegema.mlgm4a.security.PrivateKeyEncoded;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.nio.charset.StandardCharsets;

final class RsaKeyAliasCodec {

    private final static String FORMAT = "android-rsa-key-pair-alias";

    public static PrivateKeyEncoded encode(KeyPairAlias alias) {


        //    String algorithm = this.context.getParent().getAlgorithm();
        //  KeyPairAlias alias = this.context.getAlias();

        byte[] bin = String.valueOf(alias).getBytes(StandardCharsets.UTF_8);
        PrivateKeyEncoded encoded = new PrivateKeyEncoded();
        encoded.setAlgorithm("RSA");
        encoded.setFormat(FORMAT);
        encoded.setEncoded(new ByteSlice(bin));
        return encoded;
    }


    public static KeyPairAlias decode(PrivateKeyEncoded src) {
        String fmt = src.getFormat();
        if (!FORMAT.equalsIgnoreCase(fmt)) {
            throw new SecurityException("unsupported RSA private key format: " + fmt);
        }
        byte[] bin = src.getEncoded().toByteArray();
        String alias = new String(bin, StandardCharsets.UTF_8);
        return new KeyPairAlias(alias);
    }

}
