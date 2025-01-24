package com.github.milegema.mlgm4a.contexts;

public final class ContextFactory {

    private ContextFactory() {
    }


    public static RootContext createRootContext() {

        RootContext rc = new RootContext();

        rc.setRemote(null);
        rc.setScope(ContextScope.ROOT);
        rc.setOwnerRepository(null);

        return rc;
    }

    public static UserContext createUserContext(RootContext parent) {
        UserContext uc = new UserContext();

        uc.setScope(ContextScope.USER);
        uc.setParent(parent);
        uc.setOwnerRepository(null);

        return uc;
    }

}
