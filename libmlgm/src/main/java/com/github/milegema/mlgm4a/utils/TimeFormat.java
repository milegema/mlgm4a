package com.github.milegema.mlgm4a.utils;

import java.time.Instant;

public class TimeFormat {

    public static String stringify(Time t) {
        Instant i = Instant.ofEpochMilli(t.milliseconds());
        return i.toString();
    }

}
