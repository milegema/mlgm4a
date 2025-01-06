package com.github.milegema.mlgm4a.data.files;

import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.io.IOException;

public class FileAccessLayer {

    private ByteSlice encoded; // = head + body
    private PropertyTable head;
    private ByteSlice body;

    public FileAccessLayer() {
    }

    public ByteSlice getEncoded() {
        return encoded;
    }

    public void setEncoded(ByteSlice encoded) {
        this.encoded = encoded;
    }

    public PropertyTable getHead() {
        return head;
    }

    public void setHead(PropertyTable head) {
        this.head = head;
    }

    public ByteSlice getBody() {
        return body;
    }

    public void setBody(ByteSlice body) {
        this.body = body;
    }

    public void encode() throws IOException {
        FileAccessLayerLS.encode(this);
    }

    public void decode() throws IOException {
        FileAccessLayerLS.decode(this);
    }
}
