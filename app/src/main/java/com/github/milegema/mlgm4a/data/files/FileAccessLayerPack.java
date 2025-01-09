package com.github.milegema.mlgm4a.data.files;

import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.io.IOException;

public final class FileAccessLayerPack {

    private PropertyTable head;
    private ByteSlice body;

    public FileAccessLayerPack() {
    }

    public FileAccessLayerPack(PropertyTable h, ByteSlice b) {
        this.head = h;
        this.body = b;
    }

    public static FileAccessLayerPack decode(ByteSlice src) throws IOException {
        return FileAccessLayerLS.decodePack(src);
    }

    public static ByteSlice encode(FileAccessLayerPack src) throws IOException {
        return FileAccessLayerLS.encode(src);
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
}
