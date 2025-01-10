package com.github.milegema.mlgm4a.data.repositories.objects;

import com.github.milegema.mlgm4a.data.repositories.blocks.BlockID;

import java.io.IOException;

public interface Objects {

    ObjectHolder get(BlockID id);

    ObjectHolder importObject(EncodedObject eo) throws IOException;

    ObjectHolder createObject(ObjectBuilder builder) throws IOException;

}
