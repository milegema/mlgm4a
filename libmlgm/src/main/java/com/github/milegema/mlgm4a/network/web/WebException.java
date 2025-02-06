package com.github.milegema.mlgm4a.network.web;

import java.io.IOException;

public class WebException extends IOException {

    public WebException(int status) {
        super("HTTP " + status);
    }

}
