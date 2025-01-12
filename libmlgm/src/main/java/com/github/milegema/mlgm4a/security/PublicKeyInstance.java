package com.github.milegema.mlgm4a.security;

import java.security.PublicKey;

public interface PublicKeyInstance {

    KeyPairProvider provider();

    PublicKey key();

    PublicKeyEncoded export();

}
