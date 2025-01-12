package com.github.milegema.mlgm4a.security;

public interface SecretKeyProvider extends AlgorithmProvider {

    String algorithm();

    SecretKeyLoader loader();

    SecretKeyGenerator generator();

}
