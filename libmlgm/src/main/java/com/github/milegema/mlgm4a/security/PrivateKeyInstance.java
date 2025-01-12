package com.github.milegema.mlgm4a.security;

import java.security.KeyPair;

public interface PrivateKeyInstance {

    KeyPairProvider provider();

    PublicKeyInstance getPublic();

    KeyPair pair();

    PrivateKeyEncoded export();

}
