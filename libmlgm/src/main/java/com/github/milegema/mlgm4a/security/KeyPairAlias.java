package com.github.milegema.mlgm4a.security;

import com.github.milegema.mlgm4a.data.ids.Alias;

public class KeyPairAlias extends Alias {

    public KeyPairAlias(String str) {
        super(str);
    }

    public KeyPairAlias(KeyPairAlias src) {
        super(String.valueOf(src));
    }

}
