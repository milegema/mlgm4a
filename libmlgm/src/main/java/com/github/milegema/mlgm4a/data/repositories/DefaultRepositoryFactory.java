package com.github.milegema.mlgm4a.data.repositories;

import com.github.milegema.mlgm4a.data.repositories.objects.DefaultObjectManager;
import com.github.milegema.mlgm4a.data.repositories.refs.DefaultRefManager;
import com.github.milegema.mlgm4a.data.repositories.tables.DataPropertyTableIO;
import com.github.milegema.mlgm4a.data.repositories.tables.DefaultTableManager;
import com.github.milegema.mlgm4a.data.repositories.tables.DatabaseContext;
import com.github.milegema.mlgm4a.data.repositories.tables.TableFileLocator;

public final class DefaultRepositoryFactory implements RepositoryFactory {

    private RepositoryFactoryContext rfc;

    public DefaultRepositoryFactory() {
    }


    @Override
    public RepositoryContext create(RepositoryContext ctx) {

        ctx = new RepositoryContext(ctx);

        ctx.setConfig(new RepoConfigImpl(ctx));
        ctx.setRepository(new RepositoryImpl(ctx));
        ctx.setObjects(new DefaultObjectManager(ctx));
        ctx.setRefs(new DefaultRefManager(ctx));

        ctx.setTables(new DefaultTableManager(ctx));
        ctx.setDatabaseContext(new DatabaseContext(ctx));
        this.init_database_context(ctx, ctx.getDatabaseContext());

        ctx.setSecretKeyHolder(new RepoSecretKeyHolder(ctx));

        return ctx;
    }

    private void init_database_context(RepositoryContext rc, DatabaseContext rdc) {
        rdc.setSchema(this.rfc.getSchema());
        rdc.setIncludeDeleted(false);
        rdc.setTablesFolder(rc.getLayout().getTables());
        rdc.setTableFileLocator(new TableFileLocator(rdc));
        rdc.setIo(new DataPropertyTableIO(rdc));
    }


    public RepositoryFactoryContext getRfc() {
        return rfc;
    }

    public void setRfc(RepositoryFactoryContext rfc) {
        this.rfc = rfc;
    }
}
