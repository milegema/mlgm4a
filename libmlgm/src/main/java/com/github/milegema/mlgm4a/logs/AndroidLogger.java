package com.github.milegema.mlgm4a.logs;

import android.util.Log;

public class AndroidLogger implements Logger {


    private static final String TAG = AndroidLogger.class.getName();

    private AndroidLogger() {
    }

    public static void init() {
        Logs.setLogger(new AndroidLogger());
    }

    public static void error(String msg, Throwable err) {
        Log.e(TAG, msg, err);
    }

    @Override
    public void log(LogItem item) {
        Throwable err = item.getError();
        if (err == null) {
            this.logWithoutError(item);
        } else {
            this.logWithError(item, err);
        }
    }

    private void logWithError(LogItem item, Throwable err) {
        String tag = item.getTag();
        String msg = item.getMessage();
        switch (item.getLevel()) {
            case TRACE:
                Log.v(tag, msg, err);
                break;
            case DEBUG:
                Log.d(tag, msg, err);
                break;
            case WARN:
                Log.w(tag, msg, err);
                break;
            case FATAL:
            case ERROR:
                Log.e(tag, msg, err);
                break;
            case INFO:
            default:
                Log.i(tag, msg, err);
                break;
        }
    }

    private void logWithoutError(LogItem item) {
        String tag = item.getTag();
        String msg = item.getMessage();
        switch (item.getLevel()) {
            case TRACE:
                Log.v(tag, msg);
                break;
            case DEBUG:
                Log.d(tag, msg);
                break;
            case WARN:
                Log.w(tag, msg);
                break;
            case FATAL:
            case ERROR:
                Log.e(tag, msg);
                break;
            case INFO:
            default:
                Log.i(tag, msg);
                break;
        }
    }
}
