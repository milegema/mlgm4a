package com.github.milegema.mlgm4a.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.milegema.mlgm4a.logs.Logs;

public final class Time {

    private final long stamp; // ms from 1970-01-01 00:00:00 UTC

    public Time(long ms_from_1970) {
        this.stamp = ms_from_1970;
    }


    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Logs.warn("", e);
        }
    }

    public static Time now() {
        long ms = System.currentTimeMillis();
        return new Time(ms);
    }


    public long milliseconds() {
        return this.stamp;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.stamp);
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (o instanceof Time) {
            Time o2 = (Time) o;
            return this.stamp == o2.stamp;
        }
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        return TimeFormat.stringify(this);
    }
}
