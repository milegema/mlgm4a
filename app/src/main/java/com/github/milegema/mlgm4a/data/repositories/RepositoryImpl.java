package com.github.milegema.mlgm4a.data.repositories;

import com.github.milegema.mlgm4a.data.repositories.objects.Objects;
import com.github.milegema.mlgm4a.data.repositories.refs.Refs;

import java.nio.file.Path;
import java.security.PublicKey;

public class RepositoryImpl implements Repository {

    private final RepositoryContext context;

    public RepositoryImpl(RepositoryContext ctx) {
        this.context = ctx;
    }


    @Override
    public Path location() {
        return context.getLayout().getRepository();
    }

    @Override
    public PublicKey getPublicKey() {
        return context.getKeyPair().getPublic();
    }

    @Override
    public RepositoryConfig config() {
        return context.getConfig();
    }

    @Override
    public Objects objects() {
        return context.getObjects();
    }

    @Override
    public Refs refs() {
        return context.getRefs();
    }
}
