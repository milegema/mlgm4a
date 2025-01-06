package com.github.milegema.mlgm4a.data.pem;

import com.github.milegema.mlgm4a.utils.Base64;

public final class PEMEncoder {

    private PEMEncoder() {
    }

    public static String encode(PEMDocument doc) {
        if (doc == null) {
            return "";
        }
        PEMBlock[] blocks = doc.getBlocks();
        if (blocks == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (PEMBlock block : blocks) {
            encodeBlock(block, builder);
        }
        return builder.toString();
    }

    private static void encodeBlock(PEMBlock block, StringBuilder builder) {
        if (block == null || builder == null) {
            return;
        }

        byte[] data = block.getData();
        String type = block.getType();
        final char[] b64array = Base64.encode(data).toCharArray();
        final int b64length = b64array.length;
        final int width = 80;
        final String bar = "-----";
        final char nl = '\n';

        builder.append(bar).append("BEGIN ").append(type).append(bar).append(nl);
        for (int offset = 0; offset < b64length; offset += width) {
            final int len = Math.min(width, b64length - offset);
            builder.append(b64array, offset, len).append(nl);
        }
        builder.append(bar).append("END ").append(type).append(bar).append(nl);
    }

}
