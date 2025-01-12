package com.github.milegema.mlgm4a.data.repositories.blocks;

import com.github.milegema.mlgm4a.security.hash.SHA256SUM;

public final class BlockID extends SHA256SUM {

    public BlockID() {
    }

    public BlockID(String str) {
        super(str);
    }

    public BlockID(byte[] bin) {
        super(bin);
    }

    public static boolean equals(BlockID a, BlockID b) {
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }
}
