package com.github.milegema.mlgm4a.network.web;

import androidx.annotation.NonNull;

public class WebContentType {

    private final String text;

    public WebContentType() {
        this.text = "application/octet-stream";
    }

    public WebContentType(String str) {
        this.text = str;
    }

    @NonNull
    @Override
    public String toString() {
        return this.text;
    }
}
