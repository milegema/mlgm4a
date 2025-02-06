package com.github.milegema.mlgm4a.security;

import com.github.milegema.mlgm4a.data.entities.UserEntity;
import com.github.milegema.mlgm4a.data.ids.Alias;
import com.github.milegema.mlgm4a.data.ids.UUID;
import com.github.milegema.mlgm4a.data.ids.UserID;

public final class KeyPairAlias extends Alias {

    private KeyPairAlias(String str) {
        super(str);
    }

    public static KeyPairAlias root() {
        return new KeyPairAlias("root");
    }

    public static KeyPairAlias parse(String str) {
        if ("root".equalsIgnoreCase(str)) {
            return root();
        }
        UUID uuid = new UUID(str);
        return new KeyPairAlias(uuid.toString());
    }

    public static KeyPairAlias forAlias(KeyPairAlias src) {
        return new KeyPairAlias(String.valueOf(src));
    }

    public static KeyPairAlias forAlias(UUID user_uuid) {
        return new KeyPairAlias(String.valueOf(user_uuid));
    }

    public static KeyPairAlias forAlias(UserEntity user) {
        return forAlias(user.getUuid());
    }
}
