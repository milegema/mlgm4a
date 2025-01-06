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
import com.github.milegema.mlgm4a.data.files.layers.EncryptionLayer;
import com.github.milegema.mlgm4a.data.files.layers.LayerGlue;
import com.github.milegema.mlgm4a.data.files.layers.PemLayer;
import com.github.milegema.mlgm4a.data.properties.Names;
import com.github.milegema.mlgm4a.data.properties.PropertyGetter;
import com.github.milegema.mlgm4a.data.properties.PropertySetter;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.security.CipherMode;
import com.github.milegema.mlgm4a.security.CipherPadding;
import com.github.milegema.mlgm4a.security.SimpleCipher;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SecretKey;

public class EncryptionFilter implements FileAccessFilterRegistry, FileAccessFilter {


    public EncryptionFilter() {
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

    private LayerGlue getGlue(FileAccessBlock block) {
        final EncryptionLayer current_layer = block.getEncryptionLayer();
        final PemLayer next_layer = block.getPemLayer();
        LayerGlue glue = new LayerGlue(block);
        glue.setCurrentLayer(current_layer);
        glue.setNextLayer(next_layer);
        return glue;
    }

    private void onRead(FileAccessBlock block) throws FileAccessException {

        // decrypt

        final EncryptionLayer current = block.getEncryptionLayer();
        final PropertyTable head = current.getHead();
        final ByteSlice src = current.getBody();
        final PropertyGetter pGetter = new PropertyGetter(head);
        final SimpleCipher cipher = new SimpleCipher();

        CipherPadding padding = pGetter.getPaddingMode(Names.cipher_padding, null);
        CipherMode mode = pGetter.getCipherMode(Names.cipher_mode, null);
        byte[] iv = pGetter.getDataAuto(Names.cipher_iv, null);
        String algorithm = pGetter.getString(Names.cipher_algorithm, null);

        current.setIv(iv);
        current.setMode(mode);
        current.setPadding(padding);
        current.setAlgorithm(algorithm);

        cipher.setAlgorithm(algorithm);
        cipher.setIv(iv);
        cipher.setMode(mode);
        cipher.setPadding(padding);

        final PrivateKey pk = current.getPrivateKey();
        final SecretKey sk = current.getSecretKey();

        if (sk != null) {
            ByteSlice dst = cipher.decrypt(src, sk);
            current.setBody(dst);
        } else if (pk != null) {
            ByteSlice dst = cipher.decrypt(src, pk);
            current.setBody(dst);
        } else {
            throw new FileAccessException("no key to decrypt");
        }
    }

    private void onWrite(FileAccessBlock block) throws FileAccessException {

        // encrypt

        final EncryptionLayer current = block.getEncryptionLayer();
        final PropertyTable head = current.getHead();
        final ByteSlice src = current.getBody();
        final PropertySetter pSetter = new PropertySetter(head);
        final SimpleCipher cipher = new SimpleCipher();

        CipherMode mode = current.getMode();
        CipherPadding padding = current.getPadding();
        String algorithm = current.getAlgorithm();
        byte[] iv = current.getIv();

        pSetter.put(Names.cipher_algorithm, algorithm);
        pSetter.put(Names.cipher_iv, iv);
        pSetter.put(Names.cipher_mode, mode);
        pSetter.put(Names.cipher_padding, padding);

        cipher.setAlgorithm(algorithm);
        cipher.setIv(iv);
        cipher.setMode(mode);
        cipher.setPadding(padding);
        cipher.setProvider(null);

        PublicKey pk = current.getPublicKey();
        SecretKey sk = current.getSecretKey();

        if (sk != null) {
            ByteSlice dst = cipher.encrypt(src, sk);
            current.setBody(dst);
        } else if (pk != null) {
            ByteSlice dst = cipher.encrypt(src, pk);
            current.setBody(dst);
        } else {
            throw new FileAccessException("no key to encrypt");
        }
    }


    @Override
    public FileAccessFilterRegistration registration() {
        FileAccessFilterRegistration re = new FileAccessFilterRegistration();
        re.setOrder(FileAccessFilterOrder.Auto);
        re.setFilter(this);
        return re;
    }
}
