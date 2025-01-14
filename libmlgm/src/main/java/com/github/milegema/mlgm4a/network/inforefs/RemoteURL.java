package com.github.milegema.mlgm4a.network.inforefs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class RemoteURL {

    private final String url;

    public RemoteURL(String _url) {
        if (!checkURL(_url)) {
            throw new NumberFormatException("bad URL: " + _url);
        }
        this.url = _url;
    }

    private static boolean checkURL(String url) {
        if (url == null) {
            return false;
        }
        return (url.startsWith("http://") || url.startsWith("https://"));
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof RemoteURL) {
            RemoteURL o2 = (RemoteURL) obj;
            String s1 = this.toString();
            String s2 = o2.toString();
            return s1.equals(s2);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.url.hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        return this.url;
    }
}
