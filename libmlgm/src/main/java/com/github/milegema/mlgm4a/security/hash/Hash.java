package com.github.milegema.mlgm4a.security.hash;

import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.io.InputStream;

public interface Hash {

    String MD5 = "MD5";
    String SHA1 = "SHA-1";
    String SHA256 = "SHA-256";
    String SHA512 = "SHA-512";

    String algorithm();

    Sum compute(byte[] data);

    Sum compute(ByteSlice data);

    Sum compute(InputStream data);

}
