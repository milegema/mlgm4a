package com.github.milegema.mlgm4a.security;

import javax.crypto.SecretKey;

public interface SecretKeyHolder {


    SecretKeyAlias alias();

    SecretKey fetch();

    boolean create();

    boolean exists();

}
