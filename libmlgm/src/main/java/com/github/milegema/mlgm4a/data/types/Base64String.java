package com.github.milegema.mlgm4a.data.types;

import com.github.milegema.mlgm4a.utils.Base64;

public class Base64String extends Text {

    public Base64String() {
        super(Base64.encode(new byte[0]));
    }

    public Base64String(String str) {
        super(str);
    }

    public Base64String(byte[] bin) {
        super(Base64.encode(bin));
    }

    public byte[] toBytes() {
        String str = this.toString();
        return Base64.decode(str);
    }

}
