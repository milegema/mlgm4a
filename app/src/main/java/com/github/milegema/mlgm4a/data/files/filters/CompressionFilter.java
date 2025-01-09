package com.github.milegema.mlgm4a.data.files.filters;

import com.github.milegema.mlgm4a.data.files.FileAccessBlock;
import com.github.milegema.mlgm4a.data.files.FileAccessDataCodec;
import com.github.milegema.mlgm4a.data.files.FileAccessDataCodecGroup;
import com.github.milegema.mlgm4a.data.files.FileAccessDataEncoding;
import com.github.milegema.mlgm4a.data.files.FileAccessException;
import com.github.milegema.mlgm4a.data.files.FileAccessFilter;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterChain;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterOrder;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterRegistration;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterRegistry;
import com.github.milegema.mlgm4a.data.files.FileAccessLayerClass;
import com.github.milegema.mlgm4a.data.files.FileAccessLayerPack;
import com.github.milegema.mlgm4a.data.files.FileAccessRequest;
import com.github.milegema.mlgm4a.data.files.FileAccessResponse;
import com.github.milegema.mlgm4a.data.files.layers.CompressionLayer;
import com.github.milegema.mlgm4a.data.properties.Names;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.utils.ByteSlice;
import com.github.milegema.mlgm4a.utils.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

public class CompressionFilter implements FileAccessFilterRegistry, FileAccessFilter {

    private final static String COMPRESSION_ALGORITHM = "deflate";

    private final MyCodec mCodec = new MyCodec();

    public CompressionFilter() {
    }

    private static class MyCodec implements FileAccessDataCodec {
        @Override
        public void decode(FileAccessDataEncoding encoding) throws IOException {
            onDecode(encoding);
        }

        @Override
        public void encode(FileAccessDataEncoding encoding) throws IOException {
            onEncode(encoding);
        }
    }


    @Override
    public FileAccessResponse access(FileAccessRequest request, FileAccessFilterChain chain) throws IOException {
        FileAccessDataCodecGroup.prepareRequest(request, FileAccessLayerClass.COMPRESSION, this.mCodec);
        return chain.access(request);
    }


    private static void onDecode(FileAccessDataEncoding encoding) throws IOException {
        // expand
        final FileAccessBlock block = encoding.getBlock();
        final FileAccessLayerPack pack = encoding.getPack();

        final CompressionLayer current = block.getCompressionLayer();
        final PropertyTable head = pack.getHead();
        final ByteSlice src = pack.getBody();
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        final OutputStream out = new InflaterOutputStream(buffer);
        final String algorithm = head.get(Names.compression_algorithm);

        if (!COMPRESSION_ALGORITHM.equalsIgnoreCase(algorithm)) {
            throw new FileAccessException("unsupported_compression_algorithm:" + algorithm);
        }
        try {
            out.write(src.getData(), src.getOffset(), src.getLength());
            out.flush();
        } finally {
            IOUtils.close(out);
            IOUtils.close(buffer);
        }
        final ByteSlice dst = new ByteSlice(buffer.toByteArray());

        // hold
        pack.setBody(dst);
        current.setDeflatedSize(src.getLength());
        current.setInflatedSize(dst.getLength());
        current.getPack().setHead(pack.getHead());
        current.getPack().setBody(pack.getBody());
    }

    private static void onEncode(FileAccessDataEncoding encoding) throws IOException {
        // compress
        final FileAccessBlock block = encoding.getBlock();
        final FileAccessLayerPack pack = encoding.getPack();

        final CompressionLayer current = block.getCompressionLayer();
        final PropertyTable head = pack.getHead();
        final ByteSlice src = pack.getBody();
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        final OutputStream out = new DeflaterOutputStream(buffer);

        try {
            out.write(src.getData(), src.getOffset(), src.getLength());
            out.flush();
        } finally {
            IOUtils.close(out);
            IOUtils.close(buffer);
        }
        final ByteSlice dst = new ByteSlice(buffer.toByteArray());

        // hold
        head.put(Names.compression_algorithm, COMPRESSION_ALGORITHM);
        pack.setBody(dst);
        current.setInflatedSize(src.getLength());
        current.setDeflatedSize(dst.getLength());
        current.getPack().setHead(pack.getHead());
        current.getPack().setBody(pack.getBody());
    }


    @Override
    public FileAccessFilterRegistration registration() {
        FileAccessFilterRegistration re = new FileAccessFilterRegistration();
        re.setOrder(FileAccessFilterOrder.Auto);
        re.setFilter(this);
        return re;
    }
}
