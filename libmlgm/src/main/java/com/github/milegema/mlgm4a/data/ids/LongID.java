package com.github.milegema.mlgm4a.data.ids;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LongID implements EntityID {

    private final long value;

    public LongID(long n) {
        this.value = n;
    }

    public long number() {
        return this.value;
    }

    public static long numberOf(EntityID id) {
        if (id instanceof LongID) {
            LongID long_id = (LongID) id;
            return long_id.number();
        }
        return 0;
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other instanceof LongID) {
            LongID o2 = (LongID) other;
            return this.value == o2.value;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.value);
    }

    @NonNull
    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
