package com.github.milegema.mlgm4a.security;

public interface KeyPairProvider extends AlgorithmProvider {

    String algorithm();

    PrivateKeyLoader loader();

    PublicKeyLoader publicKeyLoader();

    PrivateKeyGenerator generator();

}
