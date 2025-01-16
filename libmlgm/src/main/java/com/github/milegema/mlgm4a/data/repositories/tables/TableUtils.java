package com.github.milegema.mlgm4a.data.repositories.tables;

public final class TableUtils {

    private TableUtils() {
    }

    public static String normalizeName(String name) {
        if (name == null) {
            name = "";
        }
        name = name.trim();
        if (name.isEmpty()) {
            name = "undefined";
        }
        name = name.replace('.', '_');
        return name.toLowerCase();
    }
}
