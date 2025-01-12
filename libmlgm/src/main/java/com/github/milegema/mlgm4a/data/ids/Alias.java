package com.github.milegema.mlgm4a.data.ids;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Alias {

    private final String text;

    public Alias(String a) {
        this.text = normalizeAlias(a);
    }

    private static String normalizeAlias(String str) {
        if (str == null) {
            return "";
        }
        return str.trim();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        } else if (obj == this) {
            return true;
        } else if (obj instanceof Alias) {
            final Alias a2 = (Alias) obj;
            final Class<?> c1 = this.getClass();
            final Class<?> c2 = a2.getClass();
            return (c1.equals(c2) && a2.text.equals(this.text));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.text.hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        return this.text;
    }
}
