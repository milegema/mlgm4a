package com.github.milegema.mlgm4a.data.repositories.objects;

import com.github.milegema.mlgm4a.data.files.FileAccessBlock;
import com.github.milegema.mlgm4a.data.files.FileAccessDataEncoding;
import com.github.milegema.mlgm4a.data.files.FileAccessDataState;
import com.github.milegema.mlgm4a.data.files.FileAccessLayerPack;
import com.github.milegema.mlgm4a.data.files.RepositoryFileCallback;
import com.github.milegema.mlgm4a.data.files.RepositoryFileReader;
import com.github.milegema.mlgm4a.data.files.layers.ContentLayer;
import com.github.milegema.mlgm4a.data.files.layers.SumLayer;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.data.repositories.RepositoryContext;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.io.IOException;

final class ObjectLoader {

    public static ObjectInfoCache load(ObjectContext ctx) throws IOException {

        RepositoryContext repo_ctx = ctx.getParent();
        RepositoryFileReader reader = new RepositoryFileReader(repo_ctx);
        MyResultHandler result_handler = new MyResultHandler();
        ObjectInfoCache cache = new ObjectInfoCache(ctx);
        ObjectMeta meta = new ObjectMeta();

        reader.setCallback(result_handler);
        reader.readBinary(ctx.getFile()); // access

        // todo ...

        FileAccessBlock block = result_handler.block;
        ContentLayer content_layer = block.getContentLayer();
        SumLayer sum_layer = block.getSumLayer();

        meta.setId(block.getId());
        meta.setBlockType(block.getType());
        meta.setContentType(content_layer.getContentType());
        meta.setContentLength(content_layer.getContentLength());
        meta.setCreatedAt(content_layer.getCreatedAt());

        cache.setHead(content_layer.getHead());
        cache.setBody(content_layer.getBody());
        cache.setMeta(meta);
        cache.setEncoded(sum_layer.getEntity());
        return cache;
    }

    private static class MyResultHandler extends RepositoryFileCallback {

        FileAccessBlock block;

        @Override
        public void onDecodeBlockEnd(FileAccessDataState state, FileAccessDataEncoding encoding) {
            this.block = encoding.getBlock();
            super.onDecodeBlockEnd(state, encoding);
        }
    }
}
