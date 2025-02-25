package com.github.milegema.mlgm4a.network.inforefs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

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

    public static boolean equal(RemoteURL r1, RemoteURL r2) {
        if (r1 == null || r2 == null) {
            return false;
        }
        return r1.equals(r2);
    }

    public URL toURL() {
        try {
            return new URL(this.url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public URI toURI() {
        return URI.create(this.url);
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
