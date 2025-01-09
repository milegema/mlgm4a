package com.github.milegema.mlgm4a.data.repositories;

import android.content.Context;

import com.github.milegema.mlgm4a.data.files.FileAccessFilterChain;
import com.github.milegema.mlgm4a.data.files.filters.DefaultFilterChainFactory;
import com.github.milegema.mlgm4a.data.files.RepositoryFileContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;

public class AndroidRepositoryManager implements RepositoryManager {

    private final Path mRepositoriesDir;

    public AndroidRepositoryManager(Context ctx) {
        this.mRepositoriesDir = ctx.getDataDir().toPath().resolve("repositories");
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

        ctx.setKeyPair(kp);


        ctx.setAlias(alias);
        ctx.setHolder(new MyHolder(ctx));
        ctx.setLayout(null);
        ctx.setFiles(this.createFileRepositoryContext(ctx));
        ctx.setLocation(location);

        ctx.setConfig(null);
        ctx.setRefs(null);
        ctx.setRepository(null);
        ctx.setTables(null);


        return ctx.getHolder();
    }

    private Path getLocationByAlias(RepositoryAlias alias) {
        return this.mRepositoriesDir.resolve(String.valueOf(alias));
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
            ctx = DefaultRepositoryFactory.getInstance().create(ctx);

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
            ctx = DefaultRepositoryFactory.getInstance().create(ctx);

            // load repo
            RepositoryLoader loader = new RepositoryLoader(ctx);
            loader.load();

            return ctx.getRepository();
        }
    }
}
