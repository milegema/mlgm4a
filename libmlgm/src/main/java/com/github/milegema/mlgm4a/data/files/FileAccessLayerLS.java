package com.github.milegema.mlgm4a.data.files;

import com.github.milegema.mlgm4a.data.properties.Names;
import com.github.milegema.mlgm4a.data.properties.PropertyGetter;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.data.properties.PropertyTableLS;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class FileAccessLayerLS {

    private FileAccessLayerLS() {
    }

    /*

    public static ByteSlice encode(FileAccessLayer src) throws IOException {
        FileAccessLayerPack pack = src.getPack();
        PropertyTable head = pack.getHead();
        if (head == null) {
            head = PropertyTable.Factory.create();
            pack.setHead(head);
        }
        head.put(Names.layer_class, String.valueOf(src.getLayerClass()));
        // head.put(Names.layer_index, String.valueOf(src.getLayerIndex()));
        return encode(pack);
    }



     */


    public static ByteSlice encode(FileAccessLayerPack src) throws IOException {
        if (src == null) {
            return null;
        }
        PropertyTable src_head = src.getHead();
        ByteSlice src_body = src.getBody();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] head_bin = PropertyTableLS.encode(src_head);
        buffer.write(head_bin);
        buffer.write(0);
        if (src_body != null) {
            buffer.write(src_body.getData(), src_body.getOffset(), src_body.getLength());
        }
        return new ByteSlice(buffer.toByteArray());
    }

    /*
    public static FileAccessLayer decodeLayer(ByteSlice src) throws IOException {
        FileAccessLayerPack pack = decodePack(src);
        FileAccessLayer layer = new FileAccessLayer();
        PropertyGetter pGetter = new PropertyGetter(pack.getHead());
        FileAccessLayerClass layer_class = pGetter.getFileAccessLayerClass(Names.layer_class, FileAccessLayerClass.UNKNOWN);
        int layer_index = pGetter.getInt(Names.layer_index, 0);
        layer.setLayerClass(layer_class);
        layer.setLayerIndex(layer_index);
        return layer;
    }

     */


    public static FileAccessLayerPack decodePack(ByteSlice src) throws IOException {
        if (src == null) {
            return null;
        }
        final byte[] buffer_in = src.getData();
        final int i_start = src.getOffset();
        final int i_end = src.getOffset() + src.getLength();
        final ByteArrayOutputStream buffer1 = new ByteArrayOutputStream();
        final ByteArrayOutputStream buffer2 = new ByteArrayOutputStream();
        ByteArrayOutputStream buffer_out = buffer1;
        boolean end_of_head = false;
        for (int i = i_start; i < i_end; i++) {
            final int b = buffer_in[i];
            if (!end_of_head) {
                if (b == 0) {
                    end_of_head = true;
                    buffer_out = buffer2;
                } else {
                    buffer_out.write(b);
                }
            } else {
                buffer_out.write(b);
            }
        }
        PropertyTable head = PropertyTableLS.decode(buffer1.toByteArray());
        ByteSlice body = new ByteSlice(buffer2.toByteArray());
        FileAccessLayerPack dst = new FileAccessLayerPack();
        dst.setHead(head);
        dst.setBody(body);
        return dst;
    }
}
