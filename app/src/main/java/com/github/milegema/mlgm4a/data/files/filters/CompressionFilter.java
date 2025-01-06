package com.github.milegema.mlgm4a.data.files.filters;

import com.github.milegema.mlgm4a.data.files.FileAccessBlock;
import com.github.milegema.mlgm4a.data.files.FileAccessException;
import com.github.milegema.mlgm4a.data.files.FileAccessFilter;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterChain;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterOrder;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterRegistration;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterRegistry;
import com.github.milegema.mlgm4a.data.files.FileAccessRequest;
import com.github.milegema.mlgm4a.data.files.FileAccessResponse;
import com.github.milegema.mlgm4a.data.files.FileAccessUtils;
import com.github.milegema.mlgm4a.data.files.layers.CompressionLayer;
import com.github.milegema.mlgm4a.data.files.layers.EncryptionLayer;
import com.github.milegema.mlgm4a.data.files.layers.LayerGlue;
import com.github.milegema.mlgm4a.data.properties.Names;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.utils.ByteSlice;
import com.github.milegema.mlgm4a.utils.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

public class CompressionFilter implements FileAccessFilterRegistry, FileAccessFilter {

    private final static String COMPRESSION_ALGORITHM = "deflate";


    public CompressionFilter() {
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
                onWrite(block);
                LayerGlue glue = getGlue(block);
                glue.encode();
            });
        }
    }


    private LayerGlue getGlue(FileAccessBlock block) {
        final CompressionLayer current_layer = block.getCompressionLayer();
        final EncryptionLayer next_layer = block.getEncryptionLayer();
        LayerGlue glue = new LayerGlue(block);
        glue.setCurrentLayer(current_layer);
        glue.setNextLayer(next_layer);
        return glue;
    }

    private void onRead(FileAccessBlock block) throws IOException {
        // expand

        final CompressionLayer current = block.getCompressionLayer();
        final PropertyTable head = current.getHead();
        final ByteSlice src = current.getBody();
        final ByteArrayOutputStream dst = new ByteArrayOutputStream();
        final OutputStream out = new InflaterOutputStream(dst);

        String algorithm = head.get(Names.compression_algorithm);
        if (!COMPRESSION_ALGORITHM.equalsIgnoreCase(algorithm)) {
            throw new FileAccessException("unsupported_compression_algorithm:" + algorithm);
        }


        try {
            out.write(src.getData(), src.getOffset(), src.getLength());
            out.flush();
        } finally {
            IOUtils.close(out);
            IOUtils.close(dst);
        }

        current.setBody(new ByteSlice(dst.toByteArray()));
    }

    private void onWrite(FileAccessBlock block) throws IOException {
        // compress

        final CompressionLayer current = block.getCompressionLayer();
        final PropertyTable head = current.getHead();
        final ByteSlice src = current.getBody();
        final ByteArrayOutputStream dst = new ByteArrayOutputStream();
        final OutputStream out = new DeflaterOutputStream(dst);

        try {
            out.write(src.getData(), src.getOffset(), src.getLength());
            out.flush();
        } finally {
            IOUtils.close(out);
            IOUtils.close(dst);
        }

        current.setBody(new ByteSlice(dst.toByteArray()));
        head.put(Names.compression_algorithm, COMPRESSION_ALGORITHM);
    }


    @Override
    public FileAccessFilterRegistration registration() {
        FileAccessFilterRegistration re = new FileAccessFilterRegistration();
        re.setOrder(FileAccessFilterOrder.Auto);
        re.setFilter(this);
        return re;
    }
}
