package com.github.milegema.mlgm4a.data.types;

import com.github.milegema.mlgm4a.utils.Hex;

public class HexString extends Text {

    public HexString() {
    }

    public HexString(String str) {
        super(str);
    }

    public HexString(byte[] bin) {
        super(Hex.stringify(bin));
    }

    public byte[] toBytes() {
        String str = this.toString();
        return Hex.parse(str);
    }
}
