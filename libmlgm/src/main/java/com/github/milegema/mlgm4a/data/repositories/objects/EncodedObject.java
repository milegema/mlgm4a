package com.github.milegema.mlgm4a.data.repositories.objects;

import com.github.milegema.mlgm4a.data.repositories.RepositoryException;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockID;
import com.github.milegema.mlgm4a.security.hash.Hash;
import com.github.milegema.mlgm4a.security.hash.HashUtils;
import com.github.milegema.mlgm4a.utils.ByteSlice;
import com.github.milegema.mlgm4a.utils.Hex;

import java.util.Arrays;

public class EncodedObject {

    private BlockID id;


    /**
     * sum_layer.body (content_layer.encoded)
     */
    private ByteSlice encoded;

    public EncodedObject() {
    }

    // verify

    public boolean check() {
        RepositoryException err = this.inner_check();
        return (err == null);
    }

    public void verify() {
        RepositoryException err = this.inner_check();
        if (err != null) {
            throw err;
        }
    }

    private RepositoryException inner_check() {
        if (id == null) {
            throw new RepositoryException("object id is null");
        }
        if (this.encoded == null) {
            throw new RepositoryException("object data (encoded) is null");
        }
        byte[] sum_want = this.id.toByteArray();
        byte[] sum_have = HashUtils.sum(this.encoded, Hash.SHA256);
        if (!Arrays.equals(sum_want, sum_have)) {
            String want = Hex.stringify(sum_want);
            String have = Hex.stringify(sum_have);
            String msg = "bad sum of object, want:" + want + " have:" + have;
            throw new RepositoryException(msg);
        }
        return null;
    }


    // getters & setters

    public BlockID getId() {
        return id;
    }

    public void setId(BlockID id) {
        this.id = id;
    }

    public ByteSlice getEncoded() {
        return encoded;
    }

    public void setEncoded(ByteSlice encoded) {
        this.encoded = encoded;
    }
}
