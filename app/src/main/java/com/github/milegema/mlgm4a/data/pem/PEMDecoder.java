package com.github.milegema.mlgm4a.data.pem;


import com.github.milegema.mlgm4a.utils.Base64;

import java.util.ArrayList;
import java.util.List;

public final class PEMDecoder {

    private PEMDecoder() {
    }

    public static PEMDocument decode(String text) {
        final PEMDocument doc = new PEMDocument();
        if (text == null) {
            return doc;
        }
        text = text + "\n";
        final char[] src = text.toCharArray();
        final StringBuilder buffer = new StringBuilder();
        BlockBuilder bb = new BlockBuilder();
        for (char ch : src) {
            if (ch == '\n' || ch == '\r') {
                final String row = buffer.toString().trim();
                buffer.setLength(0);
                bb.handleRow(row);
            } else {
                buffer.append(ch);
            }
        }
        doc.setBlocks(bb.toBlocks());
        return doc;
    }

    private static class BlockBuilder {

        final List<PEMBlock> list;
        final StringBuilder blockText;
        private String currentBlockType;

        BlockBuilder() {
            this.list = new ArrayList<>();
            this.blockText = new StringBuilder();
        }

        void handleBlockEdgeRow(String row) {
            final String _begin = "BEGIN";
            final String _end = "END";
            String str = row.replace('-', '\t').trim().toUpperCase();
            if (str.startsWith(_begin)) {
                str = str.substring(_begin.length()).trim();
                this.currentBlockType = str;
                this.blockText.setLength(0);
            } else if (str.startsWith(_end)) {
                str = str.substring(_end.length()).trim();
                this.onBlockClose(str);
            } else {
                throw new NumberFormatException("bad PEM block: " + row);
            }
        }

        private void onBlockClose(String str) {
            if (!str.equals(this.currentBlockType)) {
                throw new NumberFormatException("bad closing of PEM block: " + str);
            }
            final String text = this.blockText.toString();
            final byte[] data = Base64.decode(text);
            final PEMBlock block = new PEMBlock();
            block.setType(str);
            block.setData(data);
            this.list.add(block);
        }

        void handleBlockDataRow(String row) {
            this.blockText.append(row);
        }

        public void handleRow(String row) {
            final String bar = "----";
            if (row.startsWith(bar) && row.endsWith(bar)) {
                handleBlockEdgeRow(row);
            } else if (!row.isEmpty()) {
                handleBlockDataRow(row);
            }
        }

        public PEMBlock[] toBlocks() {
            return list.toArray(new PEMBlock[0]);
        }
    }
}
