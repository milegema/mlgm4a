package com.github.milegema.mlgm4a.security;

import javax.crypto.SecretKey;

public interface SecretKeyHolder {

    SecretKeyAlias alias();

    SecretKey fetch();

    void reload();

    boolean create();

    boolean exists();
}
