package com.github.milegema.mlgm4a.security;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class Token {

    private final String text;

    public Token(String str) {
        this.text = normalize(str);
    }

    private static String normalize(String str) {
        if (str == null) {
            return "";
        }
        return str.trim();
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (o instanceof Token) {
            Token other = (Token) o;
            return this.text.equals(other.text);
        }
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        return text;
    }
}
