package com.github.milegema.mlgm4a.data.repositories;

import java.security.KeyPair;

public interface RepositoryManager {

    RepositoryHolder get(KeyPair kp);

}
