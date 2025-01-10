package com.github.milegema.mlgm4a.data.repositories;

import com.github.milegema.mlgm4a.data.files.FileAccessAction;
import com.github.milegema.mlgm4a.data.files.FileAccessBlock;
import com.github.milegema.mlgm4a.data.files.FileAccessContext;
import com.github.milegema.mlgm4a.data.files.FileAccessDataEncoding;
import com.github.milegema.mlgm4a.data.files.FileAccessDataState;
import com.github.milegema.mlgm4a.data.files.FileAccessDataStateListener;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterChain;
import com.github.milegema.mlgm4a.data.files.FileAccessKeyProxy;
import com.github.milegema.mlgm4a.data.files.FileAccessOptions;
import com.github.milegema.mlgm4a.data.files.FileAccessRequest;
import com.github.milegema.mlgm4a.data.files.FileAccessResponse;
import com.github.milegema.mlgm4a.data.files.RepositoryFileContext;
import com.github.milegema.mlgm4a.data.files.layers.ContentLayer;
import com.github.milegema.mlgm4a.data.files.layers.EncryptionLayer;
import com.github.milegema.mlgm4a.data.properties.Names;
import com.github.milegema.mlgm4a.data.properties.PropertyGetter;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockID;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockType;
import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.security.CipherMode;
import com.github.milegema.mlgm4a.security.CipherPadding;
import com.github.milegema.mlgm4a.security.SecretKeyAlias;
import com.github.milegema.mlgm4a.security.SecretKeyHolder;
import com.github.milegema.mlgm4a.security.SimpleSigner;
import com.github.milegema.mlgm4a.security.SimpleVerifier;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class RepoSecretKeyHolder implements SecretKeyHolder {

    private final RepositoryContext context;

    public RepoSecretKeyHolder(RepositoryContext ctx) {
        this.context = ctx;
    }

    private static class MyKeyLoader implements FileAccessDataStateListener {

        private final RepositoryContext context;
        private SecretKey loaded_secret_key;
        private FileAccessBlock loaded_signature_block;
        private FileAccessBlock loaded_key_block;

        public MyKeyLoader(RepositoryContext ctx) {
            this.context = ctx;
        }

        public SecretKey load(Path file) throws IOException {

            FileAccessContext ctx = new FileAccessContext();
            FileAccessOptions op = new FileAccessOptions();
            FileAccessRequest req = new FileAccessRequest();
            FileAccessFilterChain chain = this.context.getRfc().getChain();

            ctx.setFile(file);
            ctx.setChain(chain);
            ctx.setKeyPair(this.context.getKeyPair());
            ctx.setDataStateListener(this);

            req.setAction(FileAccessAction.READ_ALL);
            req.setContext(ctx);
            req.setOptions(op);

            FileAccessResponse resp = chain.access(req);
            Logs.info("done: " + resp);
            this.verify_signature();
            return this.loaded_secret_key;
        }

        private void verify_signature() {

            KeyPair pair = this.context.getKeyPair();
            PublicKey pk = pair.getPublic();
            ByteSlice signature = this.loaded_signature_block.getContentLayer().getBody();
            PropertyTable head = this.loaded_signature_block.getContentLayer().getHead();
            byte[] content = this.loaded_key_block.getId().toByteArray();

            PropertyGetter pGetter = new PropertyGetter(head);
            String algorithm = pGetter.getString(Names.signature_algorithm, "");

            SimpleVerifier verifier = new SimpleVerifier();
            verifier.setAlgorithm(algorithm);
            verifier.setKey(pk);
            verifier.setContent(new ByteSlice(content));

            try {
                verifier.verify(signature);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onDataStateChange(FileAccessDataState state, FileAccessDataEncoding encoding) {
            if (FileAccessDataState.decode_block_end.equals(state)) {
                if (this.is_secret_key_block(encoding)) {
                    SecretKey sk = this.parse_secret_key(encoding);
                    encoding.getContext().setSecretKey(sk);
                    this.loaded_key_block = encoding.getBlock();
                    this.loaded_secret_key = sk;
                }
                if (this.is_secret_key_signature_block(encoding)) {
                    this.loaded_signature_block = encoding.getBlock();
                }
            }
            Logs.debug("onDataStateChange: " + state);
        }

        private SecretKey parse_secret_key(FileAccessDataEncoding encoding) {
            try {
                ContentLayer content = encoding.getBlock().getContentLayer();
                PropertyTable head = content.getHead();
                PropertyGetter pGetter = new PropertyGetter(head);
                String algorithm = pGetter.getString(Names.secret_key_algorithm, "");
                byte[] key_data = content.getBody().toByteArray();
                return new SecretKeySpec(key_data, algorithm);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private boolean is_secret_key_block(FileAccessDataEncoding encoding) {
            BlockType bt = encoding.getBlock().getType();
            return BlockType.SecretKey.equals(bt);
        }

        private boolean is_secret_key_signature_block(FileAccessDataEncoding encoding) {
            BlockType bt = encoding.getBlock().getType();
            return BlockType.SecretKeySignature.equals(bt);
        }
    }

    private static class MyKeyMaker implements FileAccessDataStateListener {

        private final RepositoryContext context;
        private final FileAccessBlock secret_key_block;
        private final FileAccessBlock signature_block;

        public MyKeyMaker(RepositoryContext ctx) {
            this.context = new RepositoryContext(ctx);
            this.secret_key_block = new FileAccessBlock();
            this.signature_block = new FileAccessBlock();
        }

        void make() throws NoSuchAlgorithmException, IOException, SignatureException, InvalidKeyException {
            this.gen_key();
            this.store_blocks();
        }

        private void gen_key() throws NoSuchAlgorithmException {

            final String algorithm = "AES";
            final int size = 256;
            KeyGenerator generator = KeyGenerator.getInstance(algorithm);
            generator.init(size);
            SecretKey sk = generator.generateKey();

            // block
            FileAccessBlock block = this.secret_key_block;
            PropertyTable head = PropertyTable.Factory.create();
            ByteSlice body = new ByteSlice(sk.getEncoded());
            head.put(Names.secret_key_algorithm, sk.getAlgorithm());
            head.put(Names.secret_key_format, sk.getFormat());
            head.put(Names.secret_key_size, String.valueOf(body.getLength() * 8));

            ContentLayer content_layer = block.getContentLayer();
            content_layer.setContentType("application/x-secret-key");
            content_layer.setContentLength(body.getLength());
            content_layer.setHead(head);
            content_layer.setBody(body);

            block.setType(BlockType.SecretKey);

            EncryptionLayer encryption = block.getEncryptionLayer();
            encryption.setPadding(CipherPadding.PKCS1Padding);
            encryption.setMode(CipherMode.NONE);

            this.context.setSecretKey(sk);
        }

        private void make_signature(BlockID secret_key_block_id) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

            final KeyPair key_pair = this.context.getKeyPair();
            final String algorithm_key = key_pair.getPublic().getAlgorithm();
            final String algorithm_hash = "SHA256";
            final String algorithm = algorithm_hash + "with" + algorithm_key;

            byte[] block_id_bin = secret_key_block_id.toByteArray();
            ByteSlice data = new ByteSlice(block_id_bin);
            SimpleSigner signer = new SimpleSigner();
            signer.setAlgorithm(algorithm);
            signer.setKey(key_pair.getPrivate());
            signer.setContent(data);
            ByteSlice signature = signer.sign();

            // make block

            FileAccessBlock block = this.signature_block;
            PropertyTable head = PropertyTable.Factory.create();
            ByteSlice body = new ByteSlice(signature);
            head.put(Names.signature_algorithm, algorithm);

            ContentLayer content = block.getContentLayer();
            content.setContentType("application/x-signature");
            content.setContentLength(body.getLength());
            content.setHead(head);
            content.setBody(body);

            block.setType(BlockType.SecretKeySignature);

            EncryptionLayer encryption = block.getEncryptionLayer();
            encryption.setPadding(CipherPadding.PKCS7Padding);
            encryption.setMode(CipherMode.CBC);
        }

        private void store_blocks() throws IOException {

            final Path file = this.context.getLayout().getKey();
            final KeyPair key_pair = this.context.getKeyPair();
            final SecretKey sk = this.context.getSecretKey();

            // blocks
            List<FileAccessBlock> block_list = new ArrayList<>();
            FileAccessBlock block1 = this.secret_key_block;
            FileAccessBlock block2 = this.signature_block;

            block1.getEncryptionLayer().setAccessKey(new FileAccessKeyProxy(key_pair.getPublic()));
            block1.getEncryptionLayer().setMode(CipherMode.NONE);
            block1.getEncryptionLayer().setPadding(CipherPadding.PKCS1Padding);
            block_list.add(block1);

            block2.getEncryptionLayer().setAccessKey(new FileAccessKeyProxy(sk));
            block2.getEncryptionLayer().setMode(CipherMode.CBC);
            block2.getEncryptionLayer().setPadding(CipherPadding.PKCS7Padding);
            block_list.add(block2);

            // request
            RepositoryFileContext rfc = this.context.getRfc();
            FileAccessContext fa_ctx = new FileAccessContext();
            FileAccessRequest req = new FileAccessRequest();

            fa_ctx.setFile(file);
            fa_ctx.setChain(rfc.getChain());
            fa_ctx.setAccessKey(new FileAccessKeyProxy(sk)); // sk
            fa_ctx.setKeyPair(key_pair);
            fa_ctx.setSecretKey(sk);
            fa_ctx.setDataStateListener(this);

            req.setAction(FileAccessAction.CREATE);
            req.setContext(fa_ctx);
            req.setBlocks(block_list);
            req.setOptions(FileAccessOptions.forRegularBlock());

            FileAccessResponse resp = fa_ctx.getChain().access(req);
            Logs.info("done: " + resp);
        }

        public void clean() {
            try {
                RepositoryLayout layout = this.context.getLayout();
                List<Path> file_list = new ArrayList<>();
                file_list.add(layout.getKey());
                for (Path file : file_list) {
                    if (file == null) {
                        continue;
                    }
                    Files.deleteIfExists(file);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Logs.info(this + ".clean() done");
        }

        @Override
        public void onDataStateChange(FileAccessDataState state, FileAccessDataEncoding encoding) {
            if (is_secret_key_block_encoded_done(state, encoding)) {
                try {
                    BlockID block_id = this.secret_key_block.getSumLayer().getId();
                    this.make_signature(block_id);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        private boolean is_secret_key_block_encoded_done(FileAccessDataState state, FileAccessDataEncoding encoding) {
            boolean a = FileAccessDataState.encode_block_end.equals(state);
            boolean b = this.secret_key_block.equals(encoding.getBlock());
            return (a && b);
        }
    }

    private Path get_file() {
        return context.getLayout().getKey();
    }

    @Override
    public SecretKeyAlias alias() {
        String str = String.valueOf(context.getAlias());
        final int want_len = 8;
        if (str.length() > want_len) {
            str = str.substring(0, want_len);
        }
        return new SecretKeyAlias(str);
    }

    @Override
    public SecretKey fetch() {
        SecretKey sk = this.context.getSecretKey();
        if (sk != null) {
            return sk;
        }
        // load
        Path file = get_file();
        MyKeyLoader loader = new MyKeyLoader(this.context);
        try {
            sk = loader.load(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.context.setSecretKey(sk);
        return sk;
    }

    @Override
    public void reload() {
        this.context.setSecretKey(null);
    }

    @Override
    public boolean create() {
        final Path file = get_file();
        if (Files.exists(file)) {
            return false; // 如果文件存在,则直接返回
        }
        MyKeyMaker maker = new MyKeyMaker(this.context);
        try {
            maker.make();
        } catch (Exception e) {
            maker.clean();
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean exists() {
        Path file = get_file();
        return Files.exists(file);
    }
}
