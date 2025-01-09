package com.github.milegema.mlgm4a.data.repositories.objects;

import com.github.milegema.mlgm4a.data.repositories.RepositoryContext;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockID;

public class DefaultObjectManager implements Objects {

    public DefaultObjectManager(RepositoryContext ctx) {
    }

    @Override
    public ObjectHolder get(BlockID id) {
        return null;
    }
}
