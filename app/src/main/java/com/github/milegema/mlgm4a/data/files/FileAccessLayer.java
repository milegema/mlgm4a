package com.github.milegema.mlgm4a.data.files;

public class FileAccessLayer {

    private final FileAccessLayerPack pack;

    public FileAccessLayer() {
        this.pack = new FileAccessLayerPack();
    }

    public FileAccessLayerPack getPack() {
        return pack;
    }
}
