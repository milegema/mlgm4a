package com.github.milegema.mlgm4a.contexts;

import android.app.Application;
import android.content.Context;

public final class ContextHolder {

    private Context android;
    private Application app;
    private ApplicationContext applicationContext;

    // stack
    private RootContext root;
    private UserContext user;
    private DomainContext domain;
    private AccountContext account;
    private SceneContext scene;
    private WordContext passcode;

    public ContextHolder() {
    }


    public static ContextHolder getInstance(Context ctx) {
        ContextAgent agent = (ContextAgent) ctx.getApplicationContext();
        return agent.getContextHolder();
    }


    public Application getApp() {
        return app;
    }

    public void setApp(Application app) {
        this.app = app;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Context getAndroid() {
        return android;
    }

    public void setAndroid(Context android) {
        this.android = android;
    }

    public RootContext getRoot() {
        return root;
    }

    public void setRoot(RootContext root) {
        this.root = root;
    }

    public UserContext getUser() {
        return user;
    }

    public void setUser(UserContext user) {
        this.user = user;
    }

    public DomainContext getDomain() {
        return domain;
    }

    public void setDomain(DomainContext domain) {
        this.domain = domain;
    }

    public AccountContext getAccount() {
        return account;
    }

    public void setAccount(AccountContext account) {
        this.account = account;
    }

    public SceneContext getScene() {
        return scene;
    }

    public void setScene(SceneContext scene) {
        this.scene = scene;
    }

    public WordContext getPasscode() {
        return passcode;
    }

    public void setPasscode(WordContext passcode) {
        this.passcode = passcode;
    }
}
