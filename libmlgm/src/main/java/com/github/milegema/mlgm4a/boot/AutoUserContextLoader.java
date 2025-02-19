package com.github.milegema.mlgm4a.boot;

import android.content.Context;

import com.github.milegema.mlgm4a.classes.users.UserService;
import com.github.milegema.mlgm4a.components.BootOrder;
import com.github.milegema.mlgm4a.components.ComLife;
import com.github.milegema.mlgm4a.components.ComLifecycle;
import com.github.milegema.mlgm4a.components.ComponentManager;
import com.github.milegema.mlgm4a.contexts.ApplicationContext;
import com.github.milegema.mlgm4a.contexts.ContextAgent;
import com.github.milegema.mlgm4a.contexts.ContextFactory;
import com.github.milegema.mlgm4a.contexts.ContextHolder;
import com.github.milegema.mlgm4a.contexts.RootContext;
import com.github.milegema.mlgm4a.contexts.UserContext;
import com.github.milegema.mlgm4a.data.entities.UserEntity;
import com.github.milegema.mlgm4a.data.ids.EmailAddress;
import com.github.milegema.mlgm4a.data.ids.RoamingUserURN;
import com.github.milegema.mlgm4a.data.repositories.Repository;
import com.github.milegema.mlgm4a.data.repositories.RepositoryHolder;
import com.github.milegema.mlgm4a.data.repositories.RepositoryManager;
import com.github.milegema.mlgm4a.network.inforefs.RemoteURL;
import com.github.milegema.mlgm4a.security.KeyPairAlias;
import com.github.milegema.mlgm4a.security.KeyPairHolder;
import com.github.milegema.mlgm4a.security.KeyPairManager;

import java.io.IOException;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AutoUserContextLoader implements ComLifecycle {

    private ContextAgent contextAgent;
    private UserService userService;
    private KeyPairManager keyPairManager;
    private RepositoryManager repositoryManager;

    public AutoUserContextLoader() {
    }

    private static class MyLoadingContext {
        UserContext uc;
        ContextHolder ch;
        UserEntity user;
        KeyPair keyPair;
        boolean abort;
    }

    private interface MyLoadingStepFunc {
        void doStep(MyLoadingContext lc) throws IOException;
    }

    public ContextAgent getContextAgent() {
        return contextAgent;
    }

    public void setContextAgent(ContextAgent contextAgent) {
        this.contextAgent = contextAgent;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
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

    @Override
    public ComLife life() {
        ComLife cl = new ComLife();
        cl.setOrder(BootOrder.user_context);
        cl.setOnCreate(this::load);
        return cl;
    }

    public void load() throws IOException {
        MyLoadingContext lc = new MyLoadingContext();
        List<MyLoadingStepFunc> steps = new ArrayList<>();
        steps.add(this::initUserContext);
        steps.add(this::loadCurrentUserInfo);
        steps.add(this::loadUserKeys);
        steps.add(this::loadUserRepo);
        steps.add(this::complete);
        for (MyLoadingStepFunc step : steps) {
            if (lc.abort) {
                break;
            }
            step.doStep(lc);
        }
    }

    private void initUserContext(MyLoadingContext lc) {
        ContextHolder ch = this.contextAgent.getContextHolder();
        UserContext uc = ch.getUser();
        if (uc == null) {
            uc = ContextFactory.createUserContext(ch.getRoot());
        }
        lc.uc = uc;
        lc.ch = ch;
    }

    private void loadCurrentUserInfo(MyLoadingContext lc) throws IOException {

        Context ctx = lc.ch.getAndroid();
        Optional<UserEntity> user_opt = this.userService.findCurrentUser(ctx);
        if (user_opt.isEmpty()) {
            lc.abort = true;
            this.makeAnonymousUserContext(lc);
            return;
        }

        UserContext uc = lc.uc;
        UserEntity user = user_opt.get();

        RemoteURL url = user.getRemote();
        EmailAddress email = user.getEmail();

        uc.setUserID(user.getId());
        uc.setAvatar(user.getAvatar());
        uc.setDisplayName(user.getDisplayName());
        uc.setEmail(email);
        uc.setRoamingName(new RoamingUserURN(url, email));
        uc.setCurrentLocation(url);
        uc.setInitialLocation(url);

        lc.user = user;
    }

    private void makeAnonymousUserContext(MyLoadingContext lc) throws IOException {

        // ApplicationContext ac = lc.ch.getApplicationContext();
        // ComponentManager com_man = ac.components();

        RootContext root_ctx = lc.ch.getRoot();
        KeyPairManager kpm = this.keyPairManager;
        RepositoryManager repo_man = this.repositoryManager;

        // key-pair
        KeyPairAlias alias = KeyPairAlias.anonymous();
        KeyPairHolder kp_holder = kpm.get(alias);
        if (!kp_holder.exists()) {
            kp_holder.create();
        }
        KeyPair kp = kp_holder.fetch();

        // repo
        RepositoryHolder repo_holder = repo_man.get(kp);
        if (!repo_holder.exists()) {
            repo_holder.create();
        }
        Repository repo = repo_holder.open();

        // user-context
        UserContext user_ctx = ContextFactory.createUserContext(root_ctx);
        user_ctx.setRepository(repo);
        user_ctx.setContextKeyPair(kp);
        user_ctx.setContextPublicKey(kp.getPublic());
        user_ctx.setContextPrivateKey(kp.getPrivate());

        lc.ch.setUser(user_ctx);
    }

    private void loadUserKeys(MyLoadingContext lc) {
        KeyPairAlias alias = KeyPairAlias.forAlias(lc.user);
        KeyPairHolder holder = this.keyPairManager.get(alias);
        KeyPair kp = holder.fetch();
        lc.uc.setContextKeyPair(kp);
        lc.uc.setContextPublicKey(kp.getPublic());
        lc.uc.setContextPrivateKey(kp.getPrivate());
        lc.keyPair = kp;
    }

    private void loadUserRepo(MyLoadingContext lc) throws IOException {
        RepositoryHolder holder = this.repositoryManager.get(lc.keyPair);
        Repository repo = holder.open();
        lc.uc.setRepository(repo);
    }


    private void complete(MyLoadingContext lc) {
        lc.ch.setUser(lc.uc);
    }
}
