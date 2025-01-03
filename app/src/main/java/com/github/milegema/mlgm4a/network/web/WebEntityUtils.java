package com.github.milegema.mlgm4a.network.web;

import com.github.milegema.mlgm4a.utils.ByteSlice;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;

public final class WebEntityUtils {

    private WebEntityUtils() {
    }

    public static WebEntity toJsonEntity(Object pojo) {
        Gson gs = new Gson();
        String json = gs.toJson(pojo);
        byte[] data = json.getBytes(StandardCharsets.UTF_8);
        WebEntity entity = new WebEntity();
        entity.setContent(new ByteSlice(data));
        entity.setContentType("application/json");
        return entity;
    }

    public static WebEntity toTextEntity(String text) {
        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        WebEntity entity = new WebEntity();
        entity.setContent(new ByteSlice(data));
        entity.setContentType("text/plain");
        return entity;
    }

    public static WebEntity toBinaryEntity(byte[] data) {
        WebEntity entity = new WebEntity();
        entity.setContent(new ByteSlice(data));
        entity.setContentType("application/octet-stream");
        return entity;
    }

    public static WebEntity toBinaryEntity(byte[] data, int off, int len) {
        WebEntity entity = new WebEntity();
        entity.setContent(new ByteSlice(data, off, len));
        entity.setContentType("application/octet-stream");
        return entity;
    }

    public static byte[] fromBinaryEntity(WebEntity entity) {
        return entity.getContent().toByteArray();
    }

    public static String fromTextEntity(WebEntity entity) {
        byte[] data = entity.getContent().toByteArray();
        return new String(data, StandardCharsets.UTF_8);
    }

    public static <T> T fromJsonEntity(WebEntity entity, Class<T> t) {
        byte[] data = entity.getContent().toByteArray();
        String str = new String(data, StandardCharsets.UTF_8);
        Gson gs = new Gson();
        return gs.fromJson(str, t);
    }
}
