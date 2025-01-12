package com.github.milegema.mlgm4a.security;

public interface AlgorithmManager {

    AlgorithmProvider findProvider(String algorithm);

    KeyPairProvider findKeyPairProvider(String algorithm);

    SecretKeyProvider findSecretKeyProvider(String algorithm);

}
