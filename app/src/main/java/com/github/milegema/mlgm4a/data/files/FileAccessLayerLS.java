package com.github.milegema.mlgm4a.data.files;

import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.data.properties.PropertyTableLS;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class FileAccessLayerLS {

    private FileAccessLayerLS() {
    }

    public static void encode(FileAccessLayer layer) throws IOException {
        if (layer == null) {
            return;
        }
        PropertyTable src_head = layer.getHead();
        ByteSlice src_body = layer.getBody();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] head_bin = PropertyTableLS.encode(src_head);
        buffer.write(head_bin);
        buffer.write(0);
        if (src_body != null) {
            buffer.write(src_body.getData(), src_body.getOffset(), src_body.getLength());
        }
        layer.setEncoded(new ByteSlice(buffer.toByteArray()));
    }

    public static void decode(FileAccessLayer layer) throws IOException {
        if (layer == null) {
            return;
        }
        final ByteSlice src = layer.getEncoded();
        if (src == null) {
            return;
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
        layer.setHead(head);
        layer.setBody(body);
    }
}
