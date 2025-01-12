package com.github.milegema.mlgm4a.security;

public interface SecretKeyLoader {

    SecretKeyInstance load(SecretKeyEncoded ske);

}
