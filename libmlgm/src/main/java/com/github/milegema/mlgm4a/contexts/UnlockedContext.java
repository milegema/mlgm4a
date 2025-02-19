package com.github.milegema.mlgm4a.contexts;

import com.github.milegema.mlgm4a.security.Token;

public final class UnlockedContext {

    private UserContext parent;
    private Token token; // 这是解锁后的令牌
    private long startedAt; // 生效时间
    private long expiredAt; // 失效时间
    private boolean alive;

    public UnlockedContext() {
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public long getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(long startedAt) {
        this.startedAt = startedAt;
    }

    public long getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(long expiredAt) {
        this.expiredAt = expiredAt;
    }

    public UserContext getParent() {
        return parent;
    }

    public void setParent(UserContext parent) {
        this.parent = parent;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public static boolean check(ContextHolder ch) {
        if (ch == null) {
            return false;
        }
        return check(ch.getUser());
    }

    public static boolean check(UserContext uc) {
        if (uc == null) {
            return false;
        }
        return check(uc.getUnlockedContext());
    }

    public static boolean check(UnlockedContext ctx) {

        if (ctx == null) {
            return false;
        }

        final long now = System.currentTimeMillis();
        final long t1 = ctx.startedAt;
        final long t2 = ctx.expiredAt;
        final Token token = ctx.token;

        if (now < t1 || t2 < now) {
            return false;
        } else if (ctx.parent == null || token == null) {
            return false;
        } else if (token.toString().isEmpty()) {
            return false;
        }

        return ctx.alive;
    }
}
