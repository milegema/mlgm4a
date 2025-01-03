package com.github.milegema.mlgm4a.logs;

public class LogItem {

    private String tag;
    private String message;
    private Throwable error;
    private LogLevel level;
    private long timestamp;

    public LogItem() {
    }

    public LogItem(LogItemBuilder b) {
        if (b != null) {
            this.tag = b.tag;
            this.message = b.message;
            this.level = b.level;
            this.error = b.error;
            this.timestamp = b.time;
        }
    }

    public static LogItem newInstance(String tag, LogLevel level, String msg, Throwable err) {
        LogItemBuilder b = new LogItemBuilder();
        b.level = level;
        b.tag = tag;
        b.message = msg;
        b.error = err;
        return b.create();
    }


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
