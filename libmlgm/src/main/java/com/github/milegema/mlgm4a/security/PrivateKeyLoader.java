package com.github.milegema.mlgm4a.security;

public interface PrivateKeyLoader {

    PrivateKeyInstance load(PrivateKeyEncoded encoded);

}
