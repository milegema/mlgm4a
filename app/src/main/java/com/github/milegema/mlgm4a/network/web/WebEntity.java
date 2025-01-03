package com.github.milegema.mlgm4a.network.web;

import com.github.milegema.mlgm4a.utils.ByteSlice;

public class WebEntity {


    private String contentType;
    private ByteSlice content;

    public WebEntity() {
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public ByteSlice getContent() {
        return content;
    }

    public void setContent(ByteSlice content) {
        this.content = content;
    }
}
