package com.github.milegema.mlgm4a.data.files.layers;

import com.github.milegema.mlgm4a.data.files.FileAccessLayer;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.utils.ByteSlice;

public class ContentLayer extends FileAccessLayer {

    private int contentLength; // content-length
    private String contentType; // content-type

    // private PropertyTable head;
    // private ByteSlice body;

    public ContentLayer() {
    }

    public PropertyTable getHead() {
        return this.getPack().getHead();
    }

    public void setHead(PropertyTable head) {
        this.getPack().setHead(head);
    }

    public ByteSlice getBody() {
        return this.getPack().getBody();
    }

    public void setBody(ByteSlice body) {
        this.getPack().setBody(body);
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
