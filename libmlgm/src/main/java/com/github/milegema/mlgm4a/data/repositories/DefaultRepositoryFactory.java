package com.github.milegema.mlgm4a.data.repositories;

import com.github.milegema.mlgm4a.data.repositories.objects.DefaultObjectManager;
import com.github.milegema.mlgm4a.data.repositories.refs.DefaultRefManager;
import com.github.milegema.mlgm4a.data.repositories.tables.DefaultTableManager;

public final class DefaultRepositoryFactory implements RepositoryFactory {

    private DefaultRepositoryFactory() {
    }

    public static RepositoryFactory getInstance() {
        return new DefaultRepositoryFactory();
    }

    @Override
    public RepositoryContext create(RepositoryContext ctx) {

        ctx = new RepositoryContext(ctx);

        ctx.setConfig(new RepoConfigImpl(ctx));
        ctx.setRepository(new RepositoryImpl(ctx));
        ctx.setObjects(new DefaultObjectManager(ctx));
        ctx.setRefs(new DefaultRefManager(ctx));
        ctx.setTables(new DefaultTableManager(ctx));

        ctx.setSecretKeyHolder(new RepoSecretKeyHolder(ctx));

        return ctx;
    }
}
