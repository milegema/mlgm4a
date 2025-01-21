package com.github.milegema.mlgm4a.security;

import com.github.milegema.mlgm4a.data.ids.Alias;
import com.github.milegema.mlgm4a.data.ids.UserID;

public class KeyPairAlias extends Alias {

    public KeyPairAlias(String str) {
        super(str);
    }

    public KeyPairAlias(KeyPairAlias src) {
        super(String.valueOf(src));
    }

    public KeyPairAlias(UserID uid) {
        super(String.valueOf(uid));
    }
}
