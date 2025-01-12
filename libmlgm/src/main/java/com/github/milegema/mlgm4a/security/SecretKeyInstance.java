package com.github.milegema.mlgm4a.security;

import javax.crypto.SecretKey;

public interface SecretKeyInstance {

    SecretKeyProvider provider();

    SecretKey key();

    SecretKeyEncoded export();

}
