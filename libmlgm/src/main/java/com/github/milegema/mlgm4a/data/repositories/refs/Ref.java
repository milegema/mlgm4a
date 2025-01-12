package com.github.milegema.mlgm4a.data.repositories.refs;

import com.github.milegema.mlgm4a.data.repositories.blocks.BlockID;

import java.io.IOException;

public interface Ref {

    RefName name();

    boolean exists();

    BlockID read() throws IOException;

    void write(BlockID value) throws IOException;

}
