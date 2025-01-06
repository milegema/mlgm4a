package com.github.milegema.mlgm4a.data.pem;

import java.util.ArrayList;
import java.util.List;

public class PEMDocument {

    private PEMBlock[] blocks;

    public PEMDocument() {
        this.blocks = new PEMBlock[0];
    }


    public PEMBlock[] getBlocks() {
        return blocks;
    }

    public void setBlocks(PEMBlock[] src) {
        if (src == null) {
            return;
        }
        this.blocks = src;
    }

    public void add(PEMBlock... src) {
        List<PEMBlock> tmp = new ArrayList<>();
        copy(this.blocks, tmp);
        copy(src, tmp);
        this.blocks = tmp.toArray(new PEMBlock[0]);
    }

    private static void copy(PEMBlock[] src, List<PEMBlock> dst) {
        if (src == null || dst == null) {
            return;
        }
        for (PEMBlock item : src) {
            if (item == null) {
                continue;
            }
            dst.add(item);
        }
    }
}
