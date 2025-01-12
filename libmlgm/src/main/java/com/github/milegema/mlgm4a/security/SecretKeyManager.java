package com.github.milegema.mlgm4a.security;

public interface SecretKeyManager {

    SecretKeyHolder get(SecretKeyAlias alias, SecretKeyConfig cfg);

    SecretKeyConfig getDefaultConfig();

}
