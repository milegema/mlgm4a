package com.github.milegema.mlgm4a.data.repositories.refs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class RefName {

    private final String name;

    public RefName(String _name) {
        this.name = normalize(_name);
    }


    private static String normalize(String _name) {
        final String prefix = "refs/";
        if (_name == null) {
            return prefix + "null";
        }
        _name = _name.trim().toLowerCase();
        if (!_name.startsWith(prefix)) {
            _name = prefix + _name;
        }
        return _name;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof RefName) {
            RefName o2 = (RefName) obj;
            return this.name.equals(o2.name);
        }
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
