package com.github.milegema.mlgm4a.security;

import java.security.KeyPair;

public interface KeyPairHolder {

//    KeyPairProvider provider();

    boolean create();

    boolean delete();

    boolean exists();

    KeyPair fetch();

    KeyPairAlias alias();

    KeyFingerprint fingerprint();

}
