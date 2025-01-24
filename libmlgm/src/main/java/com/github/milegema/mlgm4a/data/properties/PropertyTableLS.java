package com.github.milegema.mlgm4a.data.properties;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

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

    private static class ParserResultBuilder {

        private final Map<String, String> table;
        private String propertyNamePrefix; // like 'foo.bar.'

        public ParserResultBuilder() {
            this.table = new HashMap<>();
        }

        public PropertyTable create() {
            PropertyTable dst = PropertyTable.Factory.create();
            dst.importAll(this.table);
            return dst;
        }

        public void addKeyValue(String name, String value) {
            if (name == null || value == null) {
                return;
            }
            String prefix = this.propertyNamePrefix;
            if (prefix == null) {
                this.table.put(name, value);
            } else {
                this.table.put(prefix + name, value);
            }
        }

        public void updateSegmentPrefix(String[] parts) {
            StringBuilder b = new StringBuilder();
            for (String part : parts) {
                part = part.trim();
                if (part.isEmpty()) {
                    continue;
                }
                b.append(part).append('.');
            }
            this.propertyNamePrefix = b.toString();
        }
    }

    private static class Parser {
        public PropertyTable parse(String src) {
            ParserResultBuilder builder = new ParserResultBuilder();
            String[] rows = this.splitToRows(src);
            for (String row : rows) {
                this.parseRow(row, builder);
            }
            return builder.create();
        }

        private void parseRow(String row, ParserResultBuilder builder) {
            row = row.trim();
            if (row.startsWith("#")) {
                return; // 注释,忽略
            }
            if (row.startsWith("[") && row.endsWith("]")) {
                this.parseRowSegment(row, builder);
                return;
            }
            this.parseRowKeyValue(row, builder);
        }

        private void parseRowSegment(String row, ParserResultBuilder builder) {
            final char c2 = '\n';
            row = row.replace('[', c2);
            row = row.replace(']', c2);
            row = row.replace('"', c2);
            row = row.replace('\'', c2);
            row = row.replace('.', c2);
            String[] array = row.split(String.valueOf(c2));
            builder.updateSegmentPrefix(array);
        }

        private void parseRowKeyValue(String row, ParserResultBuilder builder) {
            int i1 = row.indexOf('=');
            if (i1 < 0) {
                return;
            }
            String name = row.substring(0, i1).trim();
            String value = row.substring(i1 + 1).trim();
            builder.addKeyValue(name, value);
        }

        private String[] splitToRows(String src) {
            if (src == null) {
                return new String[0];
            }
            return src.replace('\r', '\n').split("\n");
        }
    }
}
