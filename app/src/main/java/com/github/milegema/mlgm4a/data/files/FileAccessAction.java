package com.github.milegema.mlgm4a.data.files;

public enum FileAccessAction {


    READ_MIN,
    READ_ALL, READ_KEY,
    READ_MAX,


    WRITE_MIN,
    REWRITE, CREATE, APPEND,
    WRITE_MAX,

    MAX;


    public static boolean isRead(FileAccessAction action) {
        if (action == null) {
            return false;
        }
        int o = action.ordinal();
        return ((READ_MIN.ordinal() < o) && (o < READ_MAX.ordinal()));
    }

    public static boolean isWrite(FileAccessAction action) {
        if (action == null) {
            return false;
        }
        int o = action.ordinal();
        return ((WRITE_MIN.ordinal() < o) && (o < WRITE_MAX.ordinal()));
    }

}
