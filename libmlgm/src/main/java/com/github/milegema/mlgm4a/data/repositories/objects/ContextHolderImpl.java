package com.github.milegema.mlgm4a.data.repositories.objects;

import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockID;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.io.IOException;
import java.nio.file.Files;

public class ContextHolderImpl implements ObjectHolder {

    private final ObjectContext context;

    public ContextHolderImpl(ObjectContext ctx) {
        this.context = ctx;
    }

    private ObjectInfoCache inner_get_cache() {
        ObjectInfoCache cache = this.context.getCache();
        if (cache != null) {
            return cache;
        }
        try {
            cache = ObjectLoader.load(this.context);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.context.setCache(cache);
        return cache;
    }

    @Override
    public BlockID id() {
        return context.getId();
    }

    @Override
    public PropertyTable head() {
        PropertyTable src = this.inner_get_cache().getHead();
        return PropertyTable.Factory.copy(src);
    }

    @Override
    public ByteSlice body() {
        ByteSlice src = this.inner_get_cache().getBody();
        return new ByteSlice(src);
    }

    @Override
    public ObjectMeta meta() {
        ObjectMeta src = this.inner_get_cache().getMeta();
        return new ObjectMeta(src);
    }

    @Override
    public boolean exists() {
        return Files.exists(context.getFile());
    }

    @Override
    public EncodedObject export() {
        ObjectInfoCache cache = this.inner_get_cache();
        EncodedObject eo = new EncodedObject();
        eo.setId(this.context.getId());
        eo.setEncoded(cache.getEncoded());
        return eo;
    }
}
