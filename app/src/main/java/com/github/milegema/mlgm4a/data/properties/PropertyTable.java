package com.github.milegema.mlgm4a.data.properties;

import java.util.Map;

public interface PropertyTable {

    // 表示这个值已被删除
    String REMOVED_VALUE = "REMOVED_VALUE_92aa082e7373086f0a1267b";

    class Factory {
        public static PropertyTable create() {
            return new PropertyTableImpl();
        }
    }

    void put(String name, String value);

    String get(String name);

    void remove(String name);

    void clear();

    int size();

    String[] names();

    Map<String, String> exportAll(Map<String, String> dst);

    void importAll(Map<String, String> src);

}
