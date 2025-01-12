package com.github.milegema.mlgm4a.data.repositories;

public final class RepositoryFormatVersion {

    public static final int CURRENT = 1;

    public static boolean check(int version) {
        return (version == CURRENT);
    }

    public static void verify(int version) {
        if (check(version)) {
            return;
        }
        String have = String.valueOf(version);
        String want = String.valueOf(CURRENT);
        String msg = "bad MLGM repository format version, want:" + want + " have:" + have;
        throw new RepositoryException(msg);
    }
}
