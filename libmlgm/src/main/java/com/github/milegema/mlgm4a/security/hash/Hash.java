package com.github.milegema.mlgm4a.security.hash;

import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.io.InputStream;

public interface Hash {

    String MD5 = "MD5";
    String SHA1 = "SHA1";
    String SHA256 = "SHA256";
    String SHA512 = "SHA512";

    String algorithm();

    Sum compute(byte[] data);

    Sum compute(ByteSlice data);

    Sum compute(InputStream data);

}
