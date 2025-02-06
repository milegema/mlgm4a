package com.github.milegema.mlgm4a.services;

import android.content.Context;

import com.github.milegema.mlgm4a.contexts.ContextAgent;
import com.github.milegema.mlgm4a.contexts.ContextHolder;
import com.github.milegema.mlgm4a.contexts.RootContext;
import com.github.milegema.mlgm4a.contexts.UserContext;
import com.github.milegema.mlgm4a.data.entities.UserEntity;
import com.github.milegema.mlgm4a.data.ids.EmailAddress;
import com.github.milegema.mlgm4a.data.repositories.Repository;
import com.github.milegema.mlgm4a.data.repositories.RepositoryHolder;
import com.github.milegema.mlgm4a.data.repositories.RepositoryManager;
import com.github.milegema.mlgm4a.data.repositories.tables.DB;
import com.github.milegema.mlgm4a.network.api.AuthAPI;
import com.github.milegema.mlgm4a.network.api.BindAPI;
import com.github.milegema.mlgm4a.network.inforefs.RemoteContext;
import com.github.milegema.mlgm4a.network.inforefs.RemoteURL;
import com.github.milegema.mlgm4a.network.inforefs.Remotes;
import com.github.milegema.mlgm4a.security.KeyPairAlias;
import com.github.milegema.mlgm4a.security.KeyPairHolder;
import com.github.milegema.mlgm4a.security.KeyPairManager;

import java.io.IOException;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

public class AuthServiceImpl implements AuthService {

    private ContextAgent contextAgent;
    private KeyPairManager keyPairManager;
    private RepositoryManager repositoryManager;

    public AuthServiceImpl() {
    }


    private static class MyAuthContext {
        Context context;
        ContextHolder contextHolder;
        RemoteContext remoteContext;
        UserContext userContext;
        RootContext rootContext;


        AuthParams params;
        AuthResult result;
        AuthAPI.Request request;
        AuthAPI.Response response;


        Repository userRepo;
        UserEntity userEntity;
    }

    private interface StepFunction {
        void doStep(MyAuthContext ac) throws IOException;
    }

    @Override
    public AuthResult auth(Context ctx, AuthParams params) throws IOException {

        List<StepFunction> steps = new ArrayList<>();
        MyAuthContext auth_ctx = new MyAuthContext();
        Action action = params.action;

        auth_ctx.contextHolder = this.contextAgent.getContextHolder();
        auth_ctx.remoteContext = Remotes.getRemoteContext(ctx);
        auth_ctx.params = params;

        steps.add(this::doPrepareParams);
        steps.add(this::doInvokeAuth);
        if (Action.LOGIN.equals(action)) {
            steps.add(this::doPrepareUserContext);
            steps.add(this::doLoadOrCreateUserInfo);
            steps.add(this::doPrepareUserRepo);
            steps.add(this::doCompleteUserContext);
            steps.add(this::doInvokeBind);
        }
        steps.add(this::doMakeResult);

        for (StepFunction step : steps) {
            step.doStep(auth_ctx);
        }

        return auth_ctx.result;
    }

    private void doPrepareParams(MyAuthContext auth_ctx) {
        AuthParams params = auth_ctx.params;
        if (params.url == null) {
            params.url = this.loadDefaultURL();
        }
    }

    private void doInvokeAuth(MyAuthContext auth_ctx) throws IOException {

        // call api
        AuthParams params = auth_ctx.params;
        AuthAPI api = new AuthAPI(auth_ctx.remoteContext);
        //  AuthAPI.Response resp;
        AuthAPI.Request req = new AuthAPI.Request();

        req.method = params.method;
        req.mechanism = params.mechanism;
        req.credentials = params.credentials;
        auth_ctx.response = api.invoke(req);
    }

    private void doLoadOrCreateUserInfo(MyAuthContext ac) {
        Repository repo = ac.rootContext.getRepository();
        DB db = repo.tables().db();

        // want
        UserEntity want = new UserEntity();
        want.setEmail(ac.params.user);
        want.setRemote(ac.params.url);

        // try load have
        DB.Query<UserEntity> q = new DB.Query<>(UserEntity.class);
        db.query(q);
        List<UserEntity> all = q.results;
        for (UserEntity have : all) {
            if (same(want, have)) {
                ac.userEntity = have;
                return;
            }
        }

        // create new
        want = db.create(want);
        db.commit();
        ac.userEntity = want;
    }


    private static boolean same(UserEntity u1, UserEntity u2) {
        if (u1 == null || u2 == null) {
            return false;
        }
        boolean b1 = EmailAddress.equal(u1.getEmail(), u2.getEmail());
        boolean b2 = RemoteURL.equal(u1.getRemote(), u2.getRemote());
        return b1 && b2;
    }


    private void doPrepareUserContext(MyAuthContext ac) {


        ac.rootContext = ac.contextHolder.getRoot();
        ac.userContext = ac.contextHolder.getUser();


    }

    private void doPrepareUserRepo(MyAuthContext ac) throws IOException {

        UserEntity user_info = ac.userEntity;
        KeyPairAlias kp_alias = KeyPairAlias.forAlias(user_info.getUuid());

        KeyPairHolder kp_holder = this.keyPairManager.get(kp_alias);
        if (!kp_holder.exists()) {
            kp_holder.create();
        }
        KeyPair kp = kp_holder.fetch();


        RepositoryHolder repo_holder = this.repositoryManager.get(kp);
        if (!repo_holder.exists()) {
            repo_holder.create();
        }

        ac.userRepo = repo_holder.open();
    }

    private void doCompleteUserContext(MyAuthContext auth_ctx) {
    }

    private void doInvokeBind(MyAuthContext auth_ctx) throws IOException {

        BindAPI api = new BindAPI(auth_ctx.remoteContext);
        BindAPI.Request req = new BindAPI.Request();
        api.invoke(req);
    }

    private void doMakeResult(MyAuthContext auth_ctx) {
        AuthAPI.Response resp = auth_ctx.response;
        AuthResult result = new AuthResult();
        result.status = resp.status;
        result.challenges = resp.challenges;
        auth_ctx.result = result;
    }


    private RemoteURL loadDefaultURL() {
        ContextHolder ch = this.contextAgent.getContextHolder();
        return ch.getRoot().getDefaultLocation();
    }

    private KeyPair loadDefaultKeyPair() {
        ContextHolder ch = this.contextAgent.getContextHolder();
        return ch.getRoot().getContextKeyPair();
    }

    public RepositoryManager getRepositoryManager() {
        return repositoryManager;
    }

    public void setRepositoryManager(RepositoryManager repositoryManager) {
        this.repositoryManager = repositoryManager;
    }

    public KeyPairManager getKeyPairManager() {
        return keyPairManager;
    }

    public void setKeyPairManager(KeyPairManager keyPairManager) {
        this.keyPairManager = keyPairManager;
    }

    public ContextAgent getContextAgent() {
        return contextAgent;
    }

    public void setContextAgent(ContextAgent contextAgent) {
        this.contextAgent = contextAgent;
    }
}
