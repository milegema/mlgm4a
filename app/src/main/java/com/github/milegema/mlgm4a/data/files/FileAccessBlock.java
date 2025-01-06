package com.github.milegema.mlgm4a.data.files;

import com.github.milegema.mlgm4a.data.files.layers.CompressionLayer;
import com.github.milegema.mlgm4a.data.files.layers.ContentLayer;
import com.github.milegema.mlgm4a.data.files.layers.EncryptionLayer;
import com.github.milegema.mlgm4a.data.files.layers.FileStorageLayer;
import com.github.milegema.mlgm4a.data.files.layers.PemLayer;
import com.github.milegema.mlgm4a.data.files.layers.SignatureLayer;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockID;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockType;

public class FileAccessBlock {

    private BlockID id;
    private BlockType type;

    private final ContentLayer contentLayer;
    private final SignatureLayer signatureLayer;
    private final CompressionLayer compressionLayer;
    private final EncryptionLayer encryptionLayer;
    private final PemLayer pemLayer;
    private final FileStorageLayer storageLayer;

    public FileAccessBlock() {
        this.contentLayer = new ContentLayer();
        this.signatureLayer = new SignatureLayer();
        this.compressionLayer = new CompressionLayer();
        this.encryptionLayer = new EncryptionLayer();
        this.pemLayer = new PemLayer();
        this.storageLayer = new FileStorageLayer();
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

    public SignatureLayer getSignatureLayer() {
        return signatureLayer;
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

    public FileStorageLayer getStorageLayer() {
        return storageLayer;
    }

    public BlockType getType() {
        return type;
    }

    public void setType(BlockType type) {
        this.type = type;
    }
}
