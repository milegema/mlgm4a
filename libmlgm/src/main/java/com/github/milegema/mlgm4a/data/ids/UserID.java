package com.github.milegema.mlgm4a.data.ids;

public class UserID extends LongID {

    public UserID(long n) {
        super(n);
    }

    public static UserID root() {
        return new UserID(0); // root 的 UID 固定为 0
    }
}
