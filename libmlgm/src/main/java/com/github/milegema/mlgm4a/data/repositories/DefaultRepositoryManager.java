package com.github.milegema.mlgm4a.data.repositories;

import com.github.milegema.mlgm4a.data.files.FileAccessFilterChain;
import com.github.milegema.mlgm4a.data.files.filters.DefaultFilterChainFactory;
import com.github.milegema.mlgm4a.data.files.RepositoryFileContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;

public class DefaultRepositoryManager implements RepositoryManager {

    private RepositoriesFolder repositoriesFolder;
    private RepositoryFactory repositoryFactory;

    public DefaultRepositoryManager() {
    }

    public RepositoryFactory getRepositoryFactory() {
        return repositoryFactory;
    }

    public void setRepositoryFactory(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
    }

    public RepositoriesFolder getRepositoriesFolder() {
        return repositoriesFolder;
    }

    public void setRepositoriesFolder(RepositoriesFolder repositoriesFolder) {
        this.repositoriesFolder = repositoriesFolder;
    }

    private RepositoryFileContext createFileRepositoryContext(RepositoryContext src) {

        FileAccessFilterChain chain = DefaultFilterChainFactory.createNewChain();
        RepositoryFileContext fr_ctx = new RepositoryFileContext();

        fr_ctx.setKeyPair(src.getKeyPair());
        fr_ctx.setFolder(src.getLocation());
        fr_ctx.setChain(chain);

        return fr_ctx;
    }


    @Override
    public RepositoryHolder get(KeyPair kp) {

        RepositoryContext ctx = new RepositoryContext();
        RepositoryAlias alias = RepositoryAlias.getAlias(kp);
        Path location = this.getLocationByAlias(alias);
        RepositoryFactory factory = this.repositoryFactory;

        if (factory == null) {
            throw new RepositoryException("repository-factory is null");
        }

        ctx.setKeyPair(kp);
        ctx.setFactory(factory);

        ctx.setAlias(alias);
        ctx.setHolder(new MyHolder(ctx));
        ctx.setLocation(location);

        ctx.setLayout(null);
        ctx.setRfc(this.createFileRepositoryContext(ctx));

        ctx.setConfig(null);
        ctx.setRefs(null);
        ctx.setRepository(null);
        ctx.setTables(null);

        return ctx.getHolder();
    }

    private Path getLocationByAlias(RepositoryAlias alias) {
        Path folder = this.repositoriesFolder.folder();
        return folder.resolve(String.valueOf(alias));
    }

    private static class MyHolder implements RepositoryHolder {

        private final RepositoryContext context;

        MyHolder(RepositoryContext ctx) {
            this.context = ctx;
        }

        @Override
        public RepositoryAlias alias() {
            return context.getAlias();
        }

        @Override
        public boolean exists() {
            Path p = context.getLocation();
            return Files.exists(p);
        }

        @Override
        public boolean create() {

            RepositoryContext ctx = new RepositoryContext(this.context);

            // locate
            Path at = ctx.getLocation();
            Path pwd = at.getParent();
            String name = String.valueOf(at.getFileName());
            RepositoryLocator locator = new RepositoryLocator();
            RepositoryLayout layout = locator.init(pwd, name);
            ctx.setLayout(layout);

            // check exists
            Path repo_dir = layout.getRepository();
            if (Files.exists(repo_dir)) {
                return false;
            }

            // create new context
            ctx = this.context.getFactory().create(ctx);

            // init repo
            RepositoryInitializer initializer = new RepositoryInitializer(ctx);
            initializer.init();

            return true;
        }

        @Override
        public Repository open() throws IOException {

            RepositoryContext ctx = new RepositoryContext(this.context);

            // locate
            Path at = ctx.getLocation();
            RepositoryLocator locator = new RepositoryLocator();
            RepositoryLayout layout = locator.locate(at);
            ctx.setLayout(layout);

            // create new context
            RepositoryFactory factory = this.context.getFactory();
            ctx = factory.create(ctx);

            // load repo
            RepositoryLoader loader = new RepositoryLoader(ctx);
            loader.load();

            return ctx.getRepository();
        }
    }
}
