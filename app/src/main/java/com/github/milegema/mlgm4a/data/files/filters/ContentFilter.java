package com.github.milegema.mlgm4a.data.files.filters;

import com.github.milegema.mlgm4a.data.files.FileAccessBlock;
import com.github.milegema.mlgm4a.data.files.FileAccessDataEncoding;
import com.github.milegema.mlgm4a.data.files.FileAccessDataCodec;
import com.github.milegema.mlgm4a.data.files.FileAccessDataCodecGroup;
import com.github.milegema.mlgm4a.data.files.FileAccessFilter;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterChain;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterOrder;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterRegistration;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterRegistry;
import com.github.milegema.mlgm4a.data.files.FileAccessLayerPack;
import com.github.milegema.mlgm4a.data.files.FileAccessLayerClass;
import com.github.milegema.mlgm4a.data.files.FileAccessRequest;
import com.github.milegema.mlgm4a.data.files.FileAccessResponse;
import com.github.milegema.mlgm4a.data.files.layers.ContentLayer;
import com.github.milegema.mlgm4a.data.properties.Names;
import com.github.milegema.mlgm4a.data.properties.PropertyGetter;
import com.github.milegema.mlgm4a.data.properties.PropertySetter;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.data.properties.PropertyTableUtils;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockType;
import com.github.milegema.mlgm4a.network.web.ContentTypes;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.io.IOException;

public class ContentFilter implements FileAccessFilterRegistry, FileAccessFilter {

    private final MyCodec mCodec = new MyCodec();

    public ContentFilter() {
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

    private static void onEncode(FileAccessDataEncoding encoding) throws IOException {

        final FileAccessBlock block = encoding.getBlock();
        final FileAccessLayerPack pack = encoding.getPack();

        ContentLayer current = block.getContentLayer();
        PropertyTable head_1 = current.getHead();
        PropertyTable head_2 = pack.getHead();
        PropertyTable head = PropertyTableUtils.mix(head_1, head_2);
        ByteSlice body = current.getBody();
        BlockType block_type = block.getType();
        String content_type = current.getContentType();
        int content_len = body.getLength();

        if (block_type == null) {
            block_type = BlockType.BLOB;
        }
        if (content_type == null) {
            content_type = ContentTypes.application_octet_stream;
        }

        PropertySetter pSetter = new PropertySetter(head);
        pSetter.put(Names.block_type, block_type);
        pSetter.put(Names.content_type, content_type);
        pSetter.put(Names.content_length, content_len);

        // hold
        block.setType(block_type);
        pack.setHead(head);
        pack.setBody(body);
        current.setContentLength(content_len);
        current.setContentType(content_type);
        current.getPack().setHead(pack.getHead());
        current.getPack().setBody(pack.getBody());
    }

    private static void onDecode(FileAccessDataEncoding encoding) throws IOException {

        final FileAccessBlock block = encoding.getBlock();
        final FileAccessLayerPack pack = encoding.getPack();

        final ContentLayer current = block.getContentLayer();
        final ByteSlice body = pack.getBody();
        final PropertyTable head = pack.getHead();

        // parse
        PropertyGetter pGetter = new PropertyGetter(head);
        BlockType block_type = pGetter.getBlockType(Names.block_type, BlockType.BLOB);
        String content_type = pGetter.getString(Names.content_type, ContentTypes.application_octet_stream);
        final int want_len = pGetter.getInt(Names.content_length, -1);

        // verify size
        final int have_len = body.getLength();
        if ((want_len != have_len) && want_len >= 0) {
            throw new NumberFormatException("bad content-length value, want:" + want_len + " have:" + have_len);
        }

        // hold
        block.setType(block_type);
        current.setContentLength(have_len);
        current.setContentType(content_type);
        current.getPack().setHead(pack.getHead());
        current.getPack().setBody(pack.getBody());
    }

    @Override
    public FileAccessResponse access(FileAccessRequest request, FileAccessFilterChain chain) throws IOException {
        FileAccessDataCodecGroup.prepareRequest(request, FileAccessLayerClass.CONTENT, this.mCodec);
        return chain.access(request);
    }

    @Override
    public FileAccessFilterRegistration registration() {
        FileAccessFilterRegistration re = new FileAccessFilterRegistration();
        re.setOrder(FileAccessFilterOrder.Auto);
        re.setFilter(this);
        return re;
    }
}
