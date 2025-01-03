package com.github.milegema.mlgm4a.data.properties;

import java.nio.charset.StandardCharsets;

public final class PropertyTableLS {

    private PropertyTableLS() {
    }

    public static byte[] encode(PropertyTable pt) {
        String text = stringify(pt);
        return text.getBytes(StandardCharsets.UTF_8);
    }

    public static PropertyTable decode(byte[] src) {
        if (src == null) {
            return PropertyTable.Factory.create();
        }
        String text = new String(src, StandardCharsets.UTF_8);
        return parse(text);
    }

    public static PropertyTable parse(String src) {
        Parser p = new Parser();
        return p.parse(src);
    }

    public static String stringify(PropertyTable pt) {
        if (pt == null) {
            return "";
        }
        StringBuilder b = new StringBuilder();
        String[] ids = pt.names();
        for (String name : ids) {
            String value = pt.get(name);
            if (value == null) {
                continue;
            } else if (value.equals(PropertyTable.REMOVED_VALUE)) {
                continue;
            }
            b.append(name);
            b.append('=');
            b.append(value);
            b.append('\n');
        }
        return b.toString();
    }

    //////////// private

    private static class Parser {
        public PropertyTable parse(String src) {
            PropertyTable dst = PropertyTable.Factory.create();
            String[] rows = this.splitToRows(src);
            for (String row : rows) {
                this.parseRow(row, dst);
            }
            return dst;
        }

        private void parseRow(String row, PropertyTable dst) {
            int i1 = row.indexOf('=');
            if (i1 < 0) {
                return;
            }
            String name = row.substring(0, i1).trim();
            String value = row.substring(i1 + 1).trim();
            dst.put(name, value);
        }

        private String[] splitToRows(String src) {
            if (src == null) {
                return new String[0];
            }
            return src.replace('\r', '\n').split("\n");
        }
    }
}
