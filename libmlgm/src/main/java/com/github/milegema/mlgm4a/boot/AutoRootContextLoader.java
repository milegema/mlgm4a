package com.github.milegema.mlgm4a.boot;

import com.github.milegema.mlgm4a.components.BootOrder;
import com.github.milegema.mlgm4a.components.ComLife;
import com.github.milegema.mlgm4a.components.ComLifecycle;
import com.github.milegema.mlgm4a.contexts.ContextAgent;
import com.github.milegema.mlgm4a.contexts.ContextFactory;
import com.github.milegema.mlgm4a.contexts.ContextHolder;
import com.github.milegema.mlgm4a.contexts.RootContext;
import com.github.milegema.mlgm4a.data.ids.UserID;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.data.repositories.Repository;
import com.github.milegema.mlgm4a.data.repositories.RepositoryConfig;
import com.github.milegema.mlgm4a.data.repositories.RepositoryConfigCache;
import com.github.milegema.mlgm4a.data.repositories.RepositoryHolder;
import com.github.milegema.mlgm4a.data.repositories.RepositoryManager;
import com.github.milegema.mlgm4a.network.inforefs.RemoteURL;
import com.github.milegema.mlgm4a.security.KeyPairAlias;
import com.github.milegema.mlgm4a.security.KeyPairHolder;
import com.github.milegema.mlgm4a.security.KeyPairManager;

import java.io.IOException;
import java.security.KeyPair;

public class AutoRootContextLoader implements ComLifecycle {

    private ContextAgent contextAgent;
    private String defaultRemoteURL;
    private KeyPairManager keyPairManager;
    private RepositoryManager repositoryManager;


    public AutoRootContextLoader() {
    }

    public KeyPairManager getKeyPairManager() {
        return keyPairManager;
    }

    public void setKeyPairManager(KeyPairManager keyPairManager) {
        this.keyPairManager = keyPairManager;
    }

    public RepositoryManager getRepositoryManager() {
        return repositoryManager;
    }

    public void setRepositoryManager(RepositoryManager repositoryManager) {
        this.repositoryManager = repositoryManager;
    }

    public String getDefaultRemoteURL() {
        return defaultRemoteURL;
    }

    public void setDefaultRemoteURL(String defaultRemoteURL) {
        this.defaultRemoteURL = defaultRemoteURL;
    }

    public ContextAgent getContextAgent() {
        return contextAgent;
    }

    public void setContextAgent(ContextAgent contextAgent) {
        this.contextAgent = contextAgent;
    }

    @Override
    public ComLife life() {
        ComLife cl = new ComLife();
        cl.setOrder(BootOrder.root_context);
        cl.setOnCreate(this::onCreate);
        return cl;
    }

    public void onCreate() {
        ContextHolder ch = this.contextAgent.getContextHolder();
        RootContext root = ch.getRoot();
        if (root == null) {
            root = ContextFactory.createRootContext();
            ch.setRoot(root);
        }
        root.setDefaultLocation(new RemoteURL(this.defaultRemoteURL));

        this.loadKeyPair(ch);
        this.loadContextRepo(ch);
        this.checkRepoConfig(ch);
    }

    private void loadKeyPair(ContextHolder ch) {
        // UserID uid = UserID.root();
        KeyPairAlias alias = KeyPairAlias.root();
        KeyPairHolder holder = this.keyPairManager.get(alias);
        if (!holder.exists()) {
            holder.create();
        }
        KeyPair kp = holder.fetch();
        RootContext root_ctx = ch.getRoot();
        root_ctx.setContextKeyPair(kp);
        root_ctx.setContextPublicKey(kp.getPublic());
        root_ctx.setContextPrivateKey(kp.getPrivate());
    }

    private void loadContextRepo(ContextHolder ch) {
        KeyPair kp = ch.getRoot().getContextKeyPair();
        RepositoryHolder holder = this.repositoryManager.get(kp);
        if (!holder.exists()) {
            holder.create();
        }
        Repository repo;
        try {
            repo = holder.open();
            ch.getRoot().setRepository(repo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkRepoConfig(ContextHolder ch) {
        Repository repo = ch.getRoot().getRepository();
        RepositoryConfig config = repo.config();
        try {
            RepositoryConfigCache cache = config.load();
            PropertyTable pt = cache.properties();
            pt.exportAll(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
