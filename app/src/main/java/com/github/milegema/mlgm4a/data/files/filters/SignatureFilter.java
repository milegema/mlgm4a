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
import com.github.milegema.mlgm4a.data.files.layers.LayerGlue;
import com.github.milegema.mlgm4a.data.files.layers.SignatureLayer;
import com.github.milegema.mlgm4a.data.properties.Names;
import com.github.milegema.mlgm4a.data.properties.PropertyGetter;
import com.github.milegema.mlgm4a.data.properties.PropertySetter;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.security.hash.Hash;
import com.github.milegema.mlgm4a.utils.ByteSlice;
import com.github.milegema.mlgm4a.utils.Hex;

import java.io.IOException;
import java.util.Arrays;

public class SignatureFilter implements FileAccessFilterRegistry, FileAccessFilter {


    public SignatureFilter() {
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


    private void onRead(FileAccessBlock block) throws FileAccessException {

        final SignatureLayer current = block.getSignatureLayer();
        final PropertyTable head = current.getHead();
        final ByteSlice body = current.getBody();


        PropertyGetter pGetter = new PropertyGetter(head);


        // verify hash
        byte[] sum_want = pGetter.getDataAuto(Names.block_sha256sum, new byte[0]);
        byte[] sum_have = Hash.sum(body, Hash.SHA256);
        if (!Arrays.equals(sum_want, sum_have)) {
            String str_want = Hex.stringify(sum_want);
            String str_have = Hex.stringify(sum_have);
            throw new FileAccessException("bad block.id (sha256sum), want:" + str_want + " have:" + str_have);
        }


        // verify signature (todo...)


    }

    private void onWrite(FileAccessBlock block) {

        final SignatureLayer current = block.getSignatureLayer();
        PropertyTable head = current.getHead();
        ByteSlice body = current.getBody();

        if (head == null) {
            head = PropertyTable.Factory.create();
        }
        if (body == null) {
            body = new ByteSlice();
        }

        PropertySetter pSetter = new PropertySetter(head);

        // hash
        byte[] sum = Hash.sum(body, Hash.SHA256);
        pSetter.put(Names.block_sha256sum, sum);

        // sign (todo...)
        pSetter.put(Names.block_signature, "todo...");

        // hold
        current.setHead(head);
        current.setBody(body);
    }


    private LayerGlue getGlue(FileAccessBlock block) {
        final SignatureLayer current_layer = block.getSignatureLayer();
        final CompressionLayer next_layer = block.getCompressionLayer();
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
