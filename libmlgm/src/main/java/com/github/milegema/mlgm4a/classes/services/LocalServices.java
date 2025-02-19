package com.github.milegema.mlgm4a.classes.services;

import android.content.Context;

import com.github.milegema.mlgm4a.classes.accounts.AccountService;
import com.github.milegema.mlgm4a.classes.authx.AuthService;
import com.github.milegema.mlgm4a.classes.domains.DomainService;
import com.github.milegema.mlgm4a.classes.pins.PinService;
import com.github.milegema.mlgm4a.classes.users.UserService;
import com.github.milegema.mlgm4a.components.ComponentManager;
import com.github.milegema.mlgm4a.contexts.ContextHolder;

public final class LocalServices {

    private LocalServices() {
    }

    public static <T> T getService(Context ctx, Class<T> t) {
        ContextHolder ch = ContextHolder.getInstance(ctx);
        ComponentManager com_man = ch.getApplicationContext().components();
        return com_man.find(t);
    }

    // 以下是获取各种服务的方法

    public static AccountService getAccountService(Context ctx) {
        return getService(ctx, AccountService.class);
    }

    public static AuthService getAuthService(Context ctx) {
        return getService(ctx, AuthService.class);
    }

    public static DomainService getDomainService(Context ctx) {
        return getService(ctx, DomainService.class);
    }

    public static PinService getPinService(Context ctx) {
        return getService(ctx, PinService.class);
    }

    public static UserService getUserService(Context ctx) {
        return getService(ctx, UserService.class);
    }

}
