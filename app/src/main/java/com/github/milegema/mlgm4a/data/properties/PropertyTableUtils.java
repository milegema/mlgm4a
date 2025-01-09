package com.github.milegema.mlgm4a.data.properties;

import java.util.HashMap;
import java.util.Map;

public final class PropertyTableUtils {

    private PropertyTableUtils() {
    }

    public static PropertyTable mix(PropertyTable... sources) {
        PropertyTable dst = PropertyTable.Factory.create();
        if (sources == null) {
            return dst;
        }
        Map<String, String> tmp = new HashMap<>();
        for (PropertyTable src : sources) {
            if (src == null) {
                continue;
            }
            tmp = src.exportAll(tmp);
        }
        dst.importAll(tmp);
        return dst;
    }
}
