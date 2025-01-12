package com.github.milegema.mlgm4a.utils;

import com.github.milegema.mlgm4a.logs.Logs;

public class Time {

    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Logs.warn("", e);
        }
    }
}
