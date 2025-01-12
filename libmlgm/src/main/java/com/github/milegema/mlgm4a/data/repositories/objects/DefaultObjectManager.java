package com.github.milegema.mlgm4a.data.repositories.objects;

import com.github.milegema.mlgm4a.data.repositories.RepositoryContext;
import com.github.milegema.mlgm4a.data.repositories.RepositoryException;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockID;

import java.io.IOException;
import java.nio.file.Path;

public class DefaultObjectManager implements Objects {

    private final RepositoryContext context;

    public DefaultObjectManager(RepositoryContext ctx) {
        this.context = ctx;
    }

    private Path locate_object_file(BlockID id) {
        final int sep_at = 2;
        String str = String.valueOf(id);
        String p1 = str.substring(0, sep_at);
        String p2 = str.substring(sep_at);
        String path = p1 + '/' + p2;
        return this.context.getLayout().getObjects().resolve(path);
    }

    private ObjectContext inner_get_object(BlockID id) {
        ObjectContext ctx = new ObjectContext();
        ctx.setId(id);
        ctx.setFile(this.locate_object_file(id));
        ctx.setHolder(new ContextHolderImpl(ctx));
        ctx.setParent(this.context);
        ctx.setCache(null);
        return ctx;
    }

    @Override
    public ObjectHolder get(BlockID id) {
        ObjectContext ctx = inner_get_object(id);
        return ctx.getHolder();
    }

    @Override
    public ObjectHolder importObject(EncodedObject eo) throws IOException {
        // eo.verify();
        ObjectMaker maker = new ObjectMaker();
        try {
            maker.init(this.context);
            final BlockID id_want = eo.getId();
            final BlockID id_have = maker.doImport(eo);
            ObjectContext ctx = this.inner_get_object(id_have);
            if (!BlockID.equals(id_want, id_have)) {
                String msg = "bad object id, want:" + id_want + " have:" + id_have;
                throw new RepositoryException(msg);
            }
            maker.complete(ctx.getFile());
            return ctx.getHolder();
        } finally {
            maker.finish();
        }
    }

    @Override
    public ObjectHolder createObject(ObjectBuilder builder) throws IOException {
        ObjectMaker maker = new ObjectMaker();
        try {
            maker.init(this.context);
            BlockID id = maker.doCreate(builder);
            ObjectContext ctx = this.inner_get_object(id);
            maker.complete(ctx.getFile());
            return ctx.getHolder();
        } finally {
            maker.finish();
        }
    }
}
