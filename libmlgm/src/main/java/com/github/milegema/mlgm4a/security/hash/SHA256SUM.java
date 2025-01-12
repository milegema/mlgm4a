package com.github.milegema.mlgm4a.security.hash;

public class SHA256SUM extends Sum256 {

    public SHA256SUM() {
    }

    public SHA256SUM(String str) {
        super(str);
    }

    public SHA256SUM(byte[] bin) {
        super(bin);
    }
}
