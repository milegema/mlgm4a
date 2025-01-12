package com.github.milegema.mlgm4a.security;

public interface PublicKeyLoader {

    PublicKeyInstance load(PublicKeyEncoded encoded);

}
