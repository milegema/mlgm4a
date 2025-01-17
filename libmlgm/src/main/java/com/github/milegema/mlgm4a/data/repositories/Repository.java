package com.github.milegema.mlgm4a.data.repositories;

import com.github.milegema.mlgm4a.data.repositories.objects.Objects;
import com.github.milegema.mlgm4a.data.repositories.refs.Refs;
import com.github.milegema.mlgm4a.data.repositories.tables.TableManager;

import java.nio.file.Path;
import java.security.PublicKey;

public interface Repository {

    Path location();

    PublicKey getPublicKey();

    RepositoryConfig config();

    Objects objects();

    Refs refs();

    TableManager tables();

}
