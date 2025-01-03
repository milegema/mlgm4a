package com.github.milegema.mlgm4a.logs;

public class LogItemBuilder {

    public String tag;
    public String message;
    public long time;
    public LogLevel level;
    public Throwable error;


    public LogItemBuilder() {
    }


    public LogItem create() {
        this.time = System.currentTimeMillis();
        return new LogItem(this);
    }
}
