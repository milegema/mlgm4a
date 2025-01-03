package com.github.milegema.mlgm4a.logs;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalField;
import java.util.Date;

public class JavaLogger implements Logger {

    public JavaLogger() {
    }

    @Override
    public void log(LogItem item) {
        if (item == null) {
            return;
        }
        System.out.println(this.stringify(item));
        Throwable err = item.getError();
        if (err != null) {
            System.err.println(err.getMessage());
        }
    }

    private String stringifyTime(long t) {
        Instant i = Instant.ofEpochMilli(t);
        LocalDateTime ldt = LocalDateTime.ofInstant(i, ZoneId.systemDefault());
        DateTimeFormatter f = DateTimeFormatter.ISO_DATE_TIME;
        return f.format(ldt).replace('T', ' ');
    }

    private String stringifyLevel(LogLevel level) {
        final int want_len = 5;
        final StringBuilder b = new StringBuilder();
        b.append(level);
        while (b.length() < want_len) {
            b.append(' ');
        }
        return b.toString();
    }

    private String stringify(LogItem item) {
        String time = stringifyTime(item.getTimestamp());
        String msg = item.getMessage();
        String level = stringifyLevel(item.getLevel());
        String tag = item.getTag();
        return time + " (" + tag + ") [" + level + "] " + msg;
    }
}
