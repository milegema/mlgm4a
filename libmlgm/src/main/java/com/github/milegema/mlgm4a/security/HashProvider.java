package com.github.milegema.mlgm4a.security;

import com.github.milegema.mlgm4a.security.hash.Hash;

public interface HashProvider extends AlgorithmProvider {

    String algorithm();

    Hash hash();

}
