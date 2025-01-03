package com.github.milegema.mlgm4a.security.hash;


import androidx.annotation.NonNull;

public class SumWrapper implements Sum {

    private final Sum mInner;

    public SumWrapper(Sum inner) {
        if (inner == null) {
            inner = new Sum256();
        }
        this.mInner = inner;
    }

    @Override
    public byte[] toByteArray() {
        return mInner.toByteArray();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj == this) {
            return true;
        } else if (obj instanceof Sum) {
            String s1 = this.toString();
            String s2 = obj.toString();
            return s1.equalsIgnoreCase(s2);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return mInner.hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        return mInner.toString();
    }
}
