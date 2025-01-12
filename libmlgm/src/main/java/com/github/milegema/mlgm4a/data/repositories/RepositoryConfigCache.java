package com.github.milegema.mlgm4a.data.repositories;

import com.github.milegema.mlgm4a.data.properties.PropertyTable;

import java.io.IOException;

public interface RepositoryConfigCache {

    RepositoryConfig source();

    PropertyTable properties();

    long loadedAt(); // timestamp

}
