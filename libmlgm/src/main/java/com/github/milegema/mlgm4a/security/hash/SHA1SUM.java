package com.github.milegema.mlgm4a.security.hash;

public class SHA1SUM extends Sum160 {

    public SHA1SUM() {
    }

    public SHA1SUM(String str) {
        super(str);
    }

    public SHA1SUM(byte[] bin) {
        super(bin);
    }
}
