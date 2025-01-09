package com.github.milegema.mlgm4a.data.files.filters;

import com.github.milegema.mlgm4a.data.files.FileAccessBlock;
import com.github.milegema.mlgm4a.data.files.FileAccessContext;
import com.github.milegema.mlgm4a.data.files.FileAccessDataCodec;
import com.github.milegema.mlgm4a.data.files.FileAccessDataCodecGroup;
import com.github.milegema.mlgm4a.data.files.FileAccessDataEncoding;
import com.github.milegema.mlgm4a.data.files.FileAccessException;
import com.github.milegema.mlgm4a.data.files.FileAccessFilter;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterChain;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterOrder;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterRegistration;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterRegistry;
import com.github.milegema.mlgm4a.data.files.FileAccessKeyFinder;
import com.github.milegema.mlgm4a.data.files.FileAccessKeyRef;
import com.github.milegema.mlgm4a.data.files.FileAccessLayerClass;
import com.github.milegema.mlgm4a.data.files.FileAccessLayerPack;
import com.github.milegema.mlgm4a.data.files.FileAccessRequest;
import com.github.milegema.mlgm4a.data.files.FileAccessResponse;
import com.github.milegema.mlgm4a.data.files.layers.EncryptionLayer;
import com.github.milegema.mlgm4a.data.properties.Names;
import com.github.milegema.mlgm4a.data.properties.PropertyGetter;
import com.github.milegema.mlgm4a.data.properties.PropertySetter;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.security.CipherMode;
import com.github.milegema.mlgm4a.security.CipherPadding;
import com.github.milegema.mlgm4a.security.SimpleCipher;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SecretKey;

public class EncryptionFilter implements FileAccessFilterRegistry, FileAccessFilter {

    private final MyCodec mCodec = new MyCodec();

    public EncryptionFilter() {
    }

    private static class MyCodec implements FileAccessDataCodec {
        @Override
        public void decode(FileAccessDataEncoding encoding) throws IOException {
            onDecode(encoding);
        }

        @Override
        public void encode(FileAccessDataEncoding encoding) throws IOException {
            onEncode(encoding);
        }
    }

    @Override
    public FileAccessResponse access(FileAccessRequest request, FileAccessFilterChain chain) throws IOException {
        FileAccessDataCodecGroup.prepareRequest(request, FileAccessLayerClass.ENCRYPTION, this.mCodec);
        return chain.access(request);
    }


    private static void onDecode(FileAccessDataEncoding encoding) throws FileAccessException {

        // decrypt (with secret-key|private-key)

        FileAccessBlock block = encoding.getBlock();
        final EncryptionLayer current = block.getEncryptionLayer();
        FileAccessLayerPack pack = encoding.getPack();
        final PropertyTable head = pack.getHead();
        final ByteSlice src = pack.getBody();
        final PropertyGetter pGetter = new PropertyGetter(head);

        CipherPadding padding = pGetter.getPaddingMode(Names.cipher_padding, CipherPadding.NoPadding);
        CipherMode mode = pGetter.getCipherMode(Names.cipher_mode, CipherMode.NONE);
        String algorithm = pGetter.getString(Names.cipher_algorithm, "undefined");
        pGetter.setRequired(false);
        byte[] iv = pGetter.getDataAuto(Names.cipher_iv, null);

        final SimpleCipher cipher = new SimpleCipher();
        cipher.setAlgorithm(algorithm);
        cipher.setIv(iv);
        cipher.setMode(mode);
        cipher.setPadding(padding);

        final FileAccessKeyFinder.Result keys = findKeyForDecrypt(encoding, algorithm);
        final PrivateKey pk = keys.private_key; // current.getPrivateKey();
        final SecretKey sk = keys.secret_key;   // current.getSecretKey();

        if (sk != null) {
            ByteSlice dst = cipher.decrypt(src, sk);
            pack.setBody(dst);
        } else if (pk != null) {
            ByteSlice dst = cipher.decrypt(src, pk);
            pack.setBody(dst);
        } else {
            throw new FileAccessException("no key to decrypt");
        }

        // hold
        current.setIv(iv);
        current.setMode(mode);
        current.setPadding(padding);
        current.setAlgorithm(algorithm);
        current.getPack().setHead(pack.getHead());
        current.getPack().setBody(pack.getBody());
    }

    private static void onEncode(FileAccessDataEncoding encoding) throws FileAccessException {

        // encrypt (with secret-key|public-key)

        final FileAccessBlock block = encoding.getBlock();
        final FileAccessLayerPack pack = encoding.getPack();

        final EncryptionLayer current = block.getEncryptionLayer();
        final ByteSlice src = pack.getBody();
        final PropertyTable head = pack.getHead();
        final FileAccessKeyFinder.Result keys = findKeyForEncrypt(encoding);

        CipherMode mode = current.getMode();
        CipherPadding padding = current.getPadding();
        byte[] iv = current.getIv();

        final SimpleCipher cipher = new SimpleCipher();
        cipher.setIv(iv);
        cipher.setMode(mode);
        cipher.setPadding(padding);
        cipher.setProvider(null);

        final SecretKey secret_key = keys.secret_key;
        final PublicKey public_key = keys.public_key;
        String algorithm;

        if (secret_key != null) {
            algorithm = secret_key.getAlgorithm();
            cipher.setAlgorithm(algorithm);
            ByteSlice dst = cipher.encrypt(src, secret_key);
            pack.setBody(dst);
        } else if (public_key != null) {
            algorithm = public_key.getAlgorithm();
            cipher.setAlgorithm(algorithm);
            ByteSlice dst = cipher.encrypt(src, public_key);
            pack.setBody(dst);
        } else {
            throw new FileAccessException("no key to encrypt");
        }

        iv = cipher.getIv();
        mode = cipher.getMode();
        padding = cipher.getPadding();
        algorithm = cipher.getAlgorithm();

        // hold
        final PropertySetter pSetter = new PropertySetter(head);
        pSetter.put(Names.cipher_algorithm, algorithm);
        pSetter.put(Names.cipher_iv, iv);
        pSetter.put(Names.cipher_mode, mode);
        pSetter.put(Names.cipher_padding, padding);
        current.setAlgorithm(algorithm);
        current.getPack().setHead(pack.getHead());
        current.getPack().setBody(pack.getBody());
    }


    private static FileAccessKeyFinder.Result findKeyForEncrypt(FileAccessDataEncoding encoding) {

        FileAccessContext ctx = encoding.getContext();
        FileAccessRequest req = encoding.getRequest();
        FileAccessBlock block = encoding.getBlock();

        FileAccessKeyRef ak1 = block.getEncryptionLayer().getAccessKey();
        FileAccessKeyRef ak2 = req.getAccessKey();
        FileAccessKeyRef ak3 = ctx.getAccessKey();

        return FileAccessKeyFinder.findOneKey(ak1, ak2, ak3);
    }

    private static FileAccessKeyFinder.Result findKeyForDecrypt(FileAccessDataEncoding encoding, String algorithm) {
        FileAccessKeyFinder.Result res = new FileAccessKeyFinder.Result();
        if (algorithm == null) {
            return res;
        }
        FileAccessContext ctx = encoding.getContext();
        KeyPair pair = ctx.getKeyPair();
        SecretKey sk = ctx.getSecretKey();
        if (sk != null) {
            if (algorithm.equalsIgnoreCase(sk.getAlgorithm())) {
                res.secret_key = sk;
            }
        }
        if (pair != null) {
            PrivateKey pk = pair.getPrivate();
            if (pk != null) {
                if (algorithm.equalsIgnoreCase(pk.getAlgorithm())) {
                    res.private_key = pk;
                }
            }
        }
        return res;
    }

    @Override
    public FileAccessFilterRegistration registration() {
        FileAccessFilterRegistration re = new FileAccessFilterRegistration();
        re.setOrder(FileAccessFilterOrder.Auto);
        re.setFilter(this);
        return re;
    }
}
