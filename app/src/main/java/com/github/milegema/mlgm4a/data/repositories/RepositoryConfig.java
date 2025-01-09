package com.github.milegema.mlgm4a.data.repositories;

import com.github.milegema.mlgm4a.data.properties.PropertyTable;

import java.io.IOException;

public interface RepositoryConfig {

    PropertyTable read() throws IOException;

    void write(PropertyTable pt) throws IOException;

    RepositoryConfigCache load() throws IOException;

}
