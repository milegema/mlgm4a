package com.github.milegema.mlgm4a.data.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PropertyTableUtils {

    private PropertyTableUtils() {
    }


    public static String[] listSegmentNames(PropertyTable pt, String prefix, String suffix) {
        if (pt == null || prefix == null || suffix == null) {
            return new String[]{};
        }
        if (!prefix.endsWith(".")) {
            prefix = prefix + '.';
        }
        if (!suffix.startsWith(".")) {
            suffix = '.' + suffix;
        }
        List<String> dst = new ArrayList<>();
        Map<String, String> src = pt.exportAll(null);
        for (String full_name : src.keySet()) {
            if (full_name.startsWith(prefix) && full_name.endsWith(suffix)) {
                int i1 = prefix.length();
                int i2 = full_name.length() - suffix.length();
                String name = full_name.substring(i1, i2);
                dst.add(name);
            }
        }
        return dst.toArray(new String[0]);
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
