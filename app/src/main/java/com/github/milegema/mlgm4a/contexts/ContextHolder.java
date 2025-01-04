package com.github.milegema.mlgm4a.contexts;

import android.content.Context;

import com.github.milegema.mlgm4a.MilegemaApp;

public final class ContextHolder {

    private Context android;
    private MilegemaApp app;
    private ApplicationContext applicationContext;

    // stack
    private RootContext root;
    private UserContext user;
    private DomainContext domain;
    private AccountContext account;
    private SceneContext scene;
    private PasscodeContext passcode;

    public ContextHolder() {
    }

    public MilegemaApp getApp() {
        return app;
    }

    public void setApp(MilegemaApp app) {
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

    public PasscodeContext getPasscode() {
        return passcode;
    }

    public void setPasscode(PasscodeContext passcode) {
        this.passcode = passcode;
    }
}
