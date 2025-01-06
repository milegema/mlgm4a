package com.github.milegema.mlgm4a.data.files.filters;

import com.github.milegema.mlgm4a.data.files.FileAccessBlock;
import com.github.milegema.mlgm4a.data.files.FileAccessFilter;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterChain;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterOrder;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterRegistration;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterRegistry;
import com.github.milegema.mlgm4a.data.files.FileAccessRequest;
import com.github.milegema.mlgm4a.data.files.FileAccessResponse;
import com.github.milegema.mlgm4a.data.files.FileAccessUtils;
import com.github.milegema.mlgm4a.data.files.layers.CompressionLayer;
import com.github.milegema.mlgm4a.data.files.layers.FileStorageLayer;
import com.github.milegema.mlgm4a.data.files.layers.LayerGlue;
import com.github.milegema.mlgm4a.data.files.layers.PemLayer;
import com.github.milegema.mlgm4a.data.files.layers.SignatureLayer;

import java.io.IOException;

public class PemBlockFilter implements FileAccessFilterRegistry, FileAccessFilter {


    public PemBlockFilter() {
    }

    @Override
    public FileAccessResponse access(FileAccessRequest request, FileAccessFilterChain chain) throws IOException {
        this.tryWrite(request);
        FileAccessResponse resp = chain.access(request);
        this.tryRead(request, resp);
        return resp;
    }

    private void tryRead(FileAccessRequest request, FileAccessResponse resp) {
        if (FileAccessRequest.isRead(request)) {
            // decode
            FileAccessUtils.forEachBlock(resp, null, (block) -> {
                LayerGlue glue = getGlue(block);
                glue.decode();
                onRead(block);
            });
        }
    }

    private void tryWrite(FileAccessRequest request) {
        if (FileAccessRequest.isWrite(request)) {
            // encode
            FileAccessUtils.forEachBlock(request, null, (block) -> {
                LayerGlue glue = getGlue(block);
                glue.encode();
            });
        }
    }

    private LayerGlue getGlue(FileAccessBlock block) {
        final PemLayer current_layer = block.getPemLayer();
        final FileStorageLayer next_layer = block.getStorageLayer();
        LayerGlue glue = new LayerGlue(block);
        glue.setCurrentLayer(current_layer);
        glue.setNextLayer(next_layer);
        return glue;
    }

    private void onRead(FileAccessBlock block) {
    }

    private void onWrite(FileAccessBlock block) {
    }


    @Override
    public FileAccessFilterRegistration registration() {
        FileAccessFilterRegistration re = new FileAccessFilterRegistration();
        re.setOrder(FileAccessFilterOrder.Auto);
        re.setFilter(this);
        return re;
    }
}
