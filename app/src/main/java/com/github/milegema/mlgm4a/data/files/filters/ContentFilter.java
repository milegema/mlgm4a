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
import com.github.milegema.mlgm4a.data.files.layers.ContentLayer;
import com.github.milegema.mlgm4a.data.files.layers.EncryptionLayer;
import com.github.milegema.mlgm4a.data.files.layers.LayerGlue;
import com.github.milegema.mlgm4a.data.files.layers.SignatureLayer;
import com.github.milegema.mlgm4a.data.properties.Names;
import com.github.milegema.mlgm4a.data.properties.PropertyGetter;
import com.github.milegema.mlgm4a.data.properties.PropertySetter;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockType;
import com.github.milegema.mlgm4a.network.web.ContentTypes;
import com.github.milegema.mlgm4a.network.web.WebContentType;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.io.IOException;

public class ContentFilter implements FileAccessFilterRegistry, FileAccessFilter {


    public ContentFilter() {
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

    private void onWrite(FileAccessBlock block) {

        final ContentLayer layer = block.getContentLayer();
        PropertyTable head = layer.getHead();
        ByteSlice body = layer.getBody();
        BlockType block_type = block.getType();
        String content_type = layer.getContentType();

        if (head == null) {
            head = PropertyTable.Factory.create();
        }

        if (body == null) {
            body = new ByteSlice();
        }

        if (block_type == null) {
            block_type = BlockType.BLOB;
        }

        if (content_type == null) {
            content_type = ContentTypes.application_octet_stream;
        }

        int content_len = body.getLength();
        PropertySetter pSetter = new PropertySetter(head);

        pSetter.put(Names.block_type, block_type);
        pSetter.put(Names.content_type, content_type);
        pSetter.put(Names.content_length, content_len);

        layer.setHead(head);
        layer.setBody(body);
        layer.setContentLength(content_len);
        layer.setContentType(content_type);
        block.setType(block_type);
    }

    private void onRead(FileAccessBlock block) {

        final ContentLayer layer = block.getContentLayer();
        PropertyTable head = layer.getHead();
        PropertyGetter pGetter = new PropertyGetter(head);
        ByteSlice body = layer.getBody();

        // parse
        BlockType block_type = pGetter.getBlockType(Names.block_type, BlockType.BLOB);
        String content_type = pGetter.getString(Names.content_type, ContentTypes.application_octet_stream);
        final int want_len = pGetter.getInt(Names.content_length, -1);

        // verify
        final int have_len = body.getLength();
        if ((want_len != have_len) && want_len >= 0) {
            throw new NumberFormatException("bad content-length value, want:" + want_len + " have:" + have_len);
        }

        // hold
        layer.setContentLength(have_len);
        layer.setContentType(content_type);
        block.setType(block_type);
    }


    private LayerGlue getGlue(FileAccessBlock block) {
        final ContentLayer current_layer = block.getContentLayer();
        final SignatureLayer next_layer = block.getSignatureLayer();
        LayerGlue glue = new LayerGlue(block);
        glue.setCurrentLayer(current_layer);
        glue.setNextLayer(next_layer);
        return glue;
    }


    @Override
    public FileAccessFilterRegistration registration() {
        FileAccessFilterRegistration re = new FileAccessFilterRegistration();
        re.setOrder(FileAccessFilterOrder.Auto);
        re.setFilter(this);
        return re;
    }
}
