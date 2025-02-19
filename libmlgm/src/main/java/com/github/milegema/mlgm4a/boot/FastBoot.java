package com.github.milegema.mlgm4a.boot;

import android.app.Activity;

import com.github.milegema.mlgm4a.contexts.ApplicationContext;
import com.github.milegema.mlgm4a.contexts.ContextHolder;
import com.github.milegema.mlgm4a.errors.Errors;
import com.github.milegema.mlgm4a.tasks.Promise;
import com.github.milegema.mlgm4a.tasks.Result;

public final class FastBoot {

    private FastBoot() {
    }


    private static class ContextChecker {

        ApplicationContext readyAC;

        public void check(Activity ctx) {
            try {
                ContextHolder ch = ContextHolder.getInstance(ctx);
                this.readyAC = ch.getApplicationContext();
            } catch (Exception e) {
                Errors.handle(ctx, e, Errors.FLAG_LOG);
            }
        }
    }


    public static Promise<ApplicationContext> boot(Activity ctx) {

        // 先检查 Context 是否需要 boot
        final ContextChecker checker = new ContextChecker();
        checker.check(ctx);

        return Promise.init(ctx, ApplicationContext.class).Try(() -> {
            ApplicationContext ac = checker.readyAC;
            if (ac == null) {
                ac = Bootstrap.boot(ctx);
            }
            return new Result<>(ac);
        }).Then((res) -> {
            return res;
        }).Catch((res) -> {
            Errors.handle(ctx, res.getError());
            return res;
        });
    }
}
