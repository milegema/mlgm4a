package com.github.milegema.mlgm4a.data.files.filters;

import com.github.milegema.mlgm4a.data.files.FileAccessBlock;
import com.github.milegema.mlgm4a.data.files.FileAccessContext;
import com.github.milegema.mlgm4a.data.files.FileAccessDataCodec;
import com.github.milegema.mlgm4a.data.files.FileAccessDataCodecGroup;
import com.github.milegema.mlgm4a.data.files.FileAccessDataEncoding;
import com.github.milegema.mlgm4a.data.files.FileAccessDataState;
import com.github.milegema.mlgm4a.data.files.FileAccessDataStateListener;
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
import com.github.milegema.mlgm4a.data.files.layers.PemLayer;
import com.github.milegema.mlgm4a.data.properties.Names;
import com.github.milegema.mlgm4a.data.properties.PropertyGetter;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.io.IOException;
import java.util.List;

public class CodecFilter implements FileAccessFilterRegistry, FileAccessFilter {

    public CodecFilter() {
    }

    @Override
    public FileAccessResponse access(FileAccessRequest request, FileAccessFilterChain chain) throws IOException {
        if (FileAccessRequest.isWrite(request)) {
            this.doEncode(request);
        }
        FileAccessResponse response = chain.access(request);
        if (FileAccessRequest.isRead(request)) {
            this.doDecode(request, response);
        }
        return response;
    }

    private void doDecode(FileAccessRequest request, FileAccessResponse response) throws IOException {

        FileAccessContext ctx = request.getContext();
        List<FileAccessBlock> block_list = response.getBlocks();
        FileAccessDataEncoding encoding = new FileAccessDataEncoding();
        FileAccessDataCodecGroup codec_group = ctx.getCodecGroup();
        FileAccessDataStateListener data_state_listener = ctx.getDataStateListener();

        final int ttl_max = codec_group.all(false).length;

        encoding.setContext(ctx);
        encoding.setRequest(request);
        encoding.setResponse(response);

        for (FileAccessBlock block : block_list) {
            PemLayer pl = block.getPemLayer();
            ByteSlice encoded = pl.getData();
            encoding.setBlock(block);
            this.dispatch_data_state_change_event(FileAccessDataState.decode_block_begin, encoding, data_state_listener);
            for (int ttl = ttl_max; ttl > 0; ttl--) {
                FileAccessLayerPack pack = FileAccessLayerPack.decode(encoded);
                PropertyGetter pGetter = new PropertyGetter(pack.getHead());
                pGetter.setRequired(false);
                FileAccessLayerClass layer_class = pGetter.getFileAccessLayerClass(Names.layer_class, null);
                if (layer_class == null) {
                    break;
                }

                encoding.setPack(pack);
                encoding.setEncoded(encoded);
                encoding.setLayerClass(layer_class);

                FileAccessDataCodec codec = codec_group.findCodec(layer_class);
                if (codec == null) {
                    throw new FileAccessException("unsupported file-access-layer class:" + layer_class);
                }

                this.dispatch_data_state_change_event(FileAccessDataState.decode_layer_begin, encoding, data_state_listener);
                codec.decode(encoding); // call codec
                this.dispatch_data_state_change_event(FileAccessDataState.decode_layer_end, encoding, data_state_listener);

                encoded = pack.getBody();
            }
            this.dispatch_data_state_change_event(FileAccessDataState.decode_block_end, encoding, data_state_listener);
        }
    }

    private void doEncode(FileAccessRequest request) throws IOException {

        FileAccessContext ctx = request.getContext();
        FileAccessDataCodecGroup codec_group = ctx.getCodecGroup();
        FileAccessLayerClass[] classes = codec_group.all(false);
        List<FileAccessBlock> block_list = request.getBlocks();
        FileAccessDataEncoding encoding = new FileAccessDataEncoding();
        FileAccessDataStateListener listener = ctx.getDataStateListener();

        encoding.setContext(ctx);
        encoding.setRequest(request);
        encoding.setResponse(null);

        for (FileAccessBlock block : block_list) {
            ByteSlice encoded = new ByteSlice();
            encoding.setBlock(block);
            this.dispatch_data_state_change_event(FileAccessDataState.encode_block_begin, encoding, listener);
            for (FileAccessLayerClass cl : classes) {
                FileAccessDataCodec codec = codec_group.findCodec(cl);
                FileAccessLayerPack pack = new FileAccessLayerPack();
                PropertyTable head = PropertyTable.Factory.create();
                head.put(Names.layer_class, String.valueOf(cl));
                // head.put(Names.layer_index, String.valueOf(index));
                pack.setHead(head);
                pack.setBody(encoded);

                encoding.setPack(pack);
                encoding.setEncoded(null);
                encoding.setLayerClass(cl);

                this.dispatch_data_state_change_event(FileAccessDataState.encode_layer_begin, encoding, listener);
                codec.encode(encoding); // call codec
                this.dispatch_data_state_change_event(FileAccessDataState.encode_layer_end, encoding, listener);

                encoded = getEncodeResult(encoding);
            }
            PemLayer pl = block.getPemLayer();
            pl.setType("MILEGEMA BLOCK");
            pl.setData(encoded);
            this.dispatch_data_state_change_event(FileAccessDataState.encode_block_end, encoding, listener);
        }
    }

    private void dispatch_data_state_change_event(FileAccessDataState state, FileAccessDataEncoding encoding, FileAccessDataStateListener listener) {
        if (state == null || encoding == null || listener == null) {
            return;
        }
        listener.onDataStateChange(state, encoding);
    }

    private static ByteSlice getEncodeResult(FileAccessDataEncoding encoding) throws IOException {
        ByteSlice encoded = encoding.getEncoded();
        if (encoded != null) {
            return encoded;
        }
        FileAccessLayerPack pack = encoding.getPack();
        if (pack != null) {
            return FileAccessLayerPack.encode(pack);
        }
        return new ByteSlice();
    }

    @Override
    public FileAccessFilterRegistration registration() {
        FileAccessFilterRegistration re = new FileAccessFilterRegistration();
        re.setOrder(FileAccessFilterOrder.Auto);
        re.setFilter(this);
        return re;
    }
}
