package com.github.milegema.mlgm4a.data.files;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileAccessDataCodecGroup {

    private final List<FileAccessLayerClass> keys;
    private final Map<FileAccessLayerClass, FileAccessDataCodec> table;

    public FileAccessDataCodecGroup() {
        this.keys = new ArrayList<>();
        this.table = new HashMap<>();
    }

    public void add(FileAccessLayerClass t, FileAccessDataCodec codec) {
        this.keys.add(t);
        this.table.put(t, codec);
    }

    public FileAccessDataCodec findCodec(FileAccessLayerClass t) {
        return this.table.get(t);
    }

    public FileAccessLayerClass[] all(boolean reverse) {
        FileAccessLayerClass[] array = this.keys.toArray(new FileAccessLayerClass[0]);
        if (reverse) {
            int i1 = 0;
            int i2 = array.length - 1;
            while (i1 < i2) {
                final FileAccessLayerClass t1 = array[i1];
                final FileAccessLayerClass t2 = array[i2];
                array[i2] = t1;
                array[i1] = t2;
                i1++;
                i2--;
            }
        }
        return array;
    }

    public static void prepareRequest(FileAccessRequest req, FileAccessLayerClass cl, FileAccessDataCodec codec) {
        req.getContext().getCodecGroup().add(cl, codec);
    }
}
