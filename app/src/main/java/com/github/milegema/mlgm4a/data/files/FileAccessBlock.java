package com.github.milegema.mlgm4a.data.files;

import com.github.milegema.mlgm4a.data.files.layers.CompressionLayer;
import com.github.milegema.mlgm4a.data.files.layers.ContentLayer;
import com.github.milegema.mlgm4a.data.files.layers.EncryptionLayer;
import com.github.milegema.mlgm4a.data.files.layers.FileStorageLayer;
import com.github.milegema.mlgm4a.data.files.layers.PemLayer;
import com.github.milegema.mlgm4a.data.files.layers.SumLayer;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockID;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockType;

public class FileAccessBlock {

    private BlockID id;
    private BlockType type;

    // layers

    private final ContentLayer contentLayer;
    private final SumLayer sumLayer;
    private final CompressionLayer compressionLayer;
    private final EncryptionLayer encryptionLayer;
    private final PemLayer pemLayer;

    // private final FileStorageLayer storageLayer; // 不需要这一层

    public FileAccessBlock() {
        this.contentLayer = new ContentLayer();
        this.sumLayer = new SumLayer();
        this.compressionLayer = new CompressionLayer();
        this.encryptionLayer = new EncryptionLayer();
        this.pemLayer = new PemLayer();
        //   this.storageLayer = new FileStorageLayer();
    }

    public BlockID getId() {
        return id;
    }

    public void setId(BlockID id) {
        this.id = id;
    }

    public ContentLayer getContentLayer() {
        return contentLayer;
    }

    public SumLayer getSumLayer() {
        return sumLayer;
    }

    public CompressionLayer getCompressionLayer() {
        return compressionLayer;
    }

    public EncryptionLayer getEncryptionLayer() {
        return encryptionLayer;
    }

    public PemLayer getPemLayer() {
        return pemLayer;
    }

    public BlockType getType() {
        return type;
    }

    public void setType(BlockType type) {
        this.type = type;
    }
}
