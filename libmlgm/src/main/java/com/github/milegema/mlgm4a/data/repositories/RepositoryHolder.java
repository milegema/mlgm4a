package com.github.milegema.mlgm4a.data.repositories;

import java.io.IOException;

public interface RepositoryHolder {

    RepositoryAlias alias();

    boolean exists();

    boolean create();

    Repository open() throws IOException;
}
