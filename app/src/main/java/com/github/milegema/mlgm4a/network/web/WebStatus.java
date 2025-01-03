package com.github.milegema.mlgm4a.network.web;

import androidx.annotation.NonNull;

import java.net.HttpURLConnection;

public final class WebStatus {

    public static final int OK = HttpURLConnection.HTTP_OK;
    public static final int NOT_FOUND = HttpURLConnection.HTTP_NOT_FOUND;

    public WebStatus() {
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String messageOf(int code) {
        switch (code) {
            case OK:
                return "OK";
            case NOT_FOUND:
                return "NOT FOUND";
            default:
                break;
        }
        return "undefined";
    }

    public static String messageOf(WebStatus status) {
        if (status == null) {
            return "undefined";
        }
        return messageOf(status.code);
    }

    @NonNull
    @Override
    public String toString() {
        int c = this.code;
        String m = messageOf(c);
        return "HTTP " + c + ' ' + m;
    }
}
