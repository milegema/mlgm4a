package com.github.milegema.mlgm4a.data.repositories.objects;

import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.utils.ByteSlice;

public class ObjectInfoCache {

    private final ObjectContext context;
    private ObjectMeta meta;

    /**
     * 这是 content 层的 head
     */
    private PropertyTable head;

    /**
     * 这是 content 层的 body
     */
    private ByteSlice body;


    /**
     * 这是经过编码的内容,也就是 sum 层的 body
     */
    private ByteSlice encoded;

    public ObjectInfoCache(ObjectContext ctx) {
        this.context = ctx;
    }

    public ByteSlice getEncoded() {
        return encoded;
    }

    public void setEncoded(ByteSlice encoded) {
        this.encoded = encoded;
    }

    public ObjectContext getContext() {
        return context;
    }

    public ObjectMeta getMeta() {
        return meta;
    }

    public void setMeta(ObjectMeta meta) {
        this.meta = meta;
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
