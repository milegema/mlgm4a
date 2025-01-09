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
import com.github.milegema.mlgm4a.data.files.FileAccessOptions;
import com.github.milegema.mlgm4a.data.files.FileAccessRequest;
import com.github.milegema.mlgm4a.data.files.FileAccessResponse;
import com.github.milegema.mlgm4a.data.files.layers.SumLayer;
import com.github.milegema.mlgm4a.data.properties.Names;
import com.github.milegema.mlgm4a.data.properties.PropertyGetter;
import com.github.milegema.mlgm4a.data.properties.PropertySetter;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockID;
import com.github.milegema.mlgm4a.security.hash.Hash;
import com.github.milegema.mlgm4a.utils.ByteSlice;
import com.github.milegema.mlgm4a.utils.Hex;

import java.io.IOException;
import java.util.Arrays;

public class SumFilter implements FileAccessFilterRegistry, FileAccessFilter {

    private static final String HASH_ALGORITHM = Hash.SHA256;
    private final MyCodec mCodec = new MyCodec();

    public SumFilter() {
    }

    private static class MyCodec implements FileAccessDataCodec {
        @Override
        public void decode(FileAccessDataEncoding encoding) throws IOException {
            onDecode(encoding);
        }

        @Override
        public void encode(FileAccessDataEncoding encoding) {
            onEncode(encoding);
        }
    }

    @Override
    public FileAccessResponse access(FileAccessRequest request, FileAccessFilterChain chain) throws IOException {
        FileAccessDataCodecGroup.prepareRequest(request, FileAccessLayerClass.SUM, mCodec);
        return chain.access(request);
    }

    private static void onDecode(FileAccessDataEncoding encoding) throws FileAccessException {

        final FileAccessBlock block = encoding.getBlock();
        final FileAccessLayerPack pack = encoding.getPack();

        final PropertyTable head = pack.getHead();
        final ByteSlice body = pack.getBody();
        final PropertyGetter pGetter = new PropertyGetter(head);
        final SumLayer current = block.getSumLayer();

        final byte[] sum_want = pGetter.getDataAuto(Names.block_sha256sum, new byte[0]);
        final byte[] sum_have = Hash.sum(body, HASH_ALGORITHM);
        final BlockID block_id = new BlockID(sum_have);

        // verify hash
        if (!Arrays.equals(sum_want, sum_have)) {
            String str_want = Hex.stringify(sum_want);
            String str_have = Hex.stringify(sum_have);
            throw new FileAccessException("bad block.id (sha256sum), want:" + str_want + " have:" + str_have);
        }

        // hold
        block.setId(block_id);
        current.setId(block_id);
        current.setEntity(body);
        current.getPack().setHead(pack.getHead());
        current.getPack().setBody(pack.getBody());
    }

    private static void onEncode(FileAccessDataEncoding encoding) {

        FileAccessBlock block = encoding.getBlock();
        FileAccessLayerPack pack = encoding.getPack();
        FileAccessRequest req = encoding.getRequest();

        PropertyTable head = pack.getHead();
        ByteSlice body = pack.getBody();
        SumLayer current = block.getSumLayer();
        FileAccessOptions op = FileAccessOptions.forDefault(req, block);
        PropertySetter pSetter = new PropertySetter(head);
        BlockID id = null;

        if (op.hash) {
            // hash
            byte[] sum = Hash.sum(body, HASH_ALGORITHM);
            pSetter.put(Names.block_sha256sum, sum);
            id = new BlockID(sum);
        }

        // hold
        block.setId(id);
        current.setId(id);
        current.setEntity(body);
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
