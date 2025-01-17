package com.github.milegema.mlgm4a.data.repositories;

import com.github.milegema.mlgm4a.data.ids.Alias;
import com.github.milegema.mlgm4a.security.KeyFingerprint;
import com.github.milegema.mlgm4a.security.KeyPairHolder;
import com.github.milegema.mlgm4a.security.hash.Hash;
import com.github.milegema.mlgm4a.security.hash.HashUtils;
import com.github.milegema.mlgm4a.utils.Hex;

import java.security.KeyPair;
import java.security.PublicKey;

public class RepositoryAlias extends Alias {

    public RepositoryAlias(String a) {
        super(a);
    }

    public static RepositoryAlias getAlias(PublicKey pk) {
        byte[] encoded = pk.getEncoded();
        byte[] sum = HashUtils.sum(encoded, Hash.SHA256);
        return inner_get_alias(sum);
    }

    public static RepositoryAlias getAlias(KeyPair kp) {
        return getAlias(kp.getPublic());
    }

    public static RepositoryAlias getAlias(KeyPairHolder kph) {
        return getAlias(kph.fingerprint());
    }

    public static RepositoryAlias getAlias(KeyFingerprint fingerprint) {
        return inner_get_alias(fingerprint.toByteArray());
    }

    public static RepositoryAlias inner_get_alias(byte[] fingerprint) {
        if (fingerprint == null) {
            fingerprint = new byte[8];
        }
        if (fingerprint.length == 0) {
            fingerprint = new byte[8];
        }
        String str = Hex.stringify(fingerprint);
        return new RepositoryAlias(str);
    }
}
