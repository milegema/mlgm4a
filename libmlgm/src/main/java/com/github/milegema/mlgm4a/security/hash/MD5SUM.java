package com.github.milegema.mlgm4a.security.hash;

public class MD5SUM extends Sum128 {

    public MD5SUM() {
    }

    public MD5SUM(String str) {
        super(str);
    }

    public MD5SUM(byte[] bin) {
        super(bin);
    }
}
