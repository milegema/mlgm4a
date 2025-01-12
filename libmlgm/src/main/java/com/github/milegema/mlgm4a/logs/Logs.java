package com.github.milegema.mlgm4a.logs;

public class Logs {

    private static final String TAG = Logs.class.getName();

    private static Logger logger;

    private Logs() {
    }

    public static Logger getLogger() {
        Logger l = logger;
        if (l == null) {
            l = initLogger();
            logger = l;
        }
        return l;
    }

    public static void setLogger(Logger l) {
        if (l == null) {
            return;
        }
        logger = l;
    }

    private static Logger initLogger() {
        return new JavaLogger();
    }


    public static void debug(String msg) {
        getLogger().log(LogItem.newInstance(TAG, LogLevel.DEBUG, msg, null));
    }

    public static void debug(String msg, Throwable err) {
        getLogger().log(LogItem.newInstance(TAG, LogLevel.DEBUG, msg, err));
    }

    public static void info(String msg) {
        getLogger().log(LogItem.newInstance(TAG, LogLevel.INFO, msg, null));
    }

    public static void info(String msg, Throwable err) {
        getLogger().log(LogItem.newInstance(TAG, LogLevel.INFO, msg, err));
    }


    public static void warn(String msg) {
        getLogger().log(LogItem.newInstance(TAG, LogLevel.WARN, msg, null));
    }

    public static void warn(String msg, Throwable err) {
        getLogger().log(LogItem.newInstance(TAG, LogLevel.WARN, msg, err));
    }


    public static void error(String msg) {
        getLogger().log(LogItem.newInstance(TAG, LogLevel.ERROR, msg, null));
    }

    public static void error(String msg, Throwable err) {
        getLogger().log(LogItem.newInstance(TAG, LogLevel.ERROR, msg, err));
    }
}
