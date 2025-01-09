package com.github.milegema.mlgm4a.security;

import com.github.milegema.mlgm4a.data.files.layers.ContentLayer;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/***
 * 准备废弃: 使用基于仓库的 SecretKeyHolder 代替
 * */

@Deprecated

public class SecretKeyManagerImpl implements SecretKeyManager {


    @Override
    public SecretKeyHolder get(SecretKeyAlias alias, SecretKeyConfig cfg) {

        if (alias == null) {
            alias = new SecretKeyAlias("unnamed");
        }

        if (cfg == null) {
            cfg = this.getDefaultConfig();
        }

        SecretKeyStore store = cfg.getStore();
        if (store == null) {
            store = SecretKeyStore.getInstance();
        }


        SecretKeyContext ctx = new SecretKeyContext();
        ctx.setManager(this);
        ctx.setStore(store);
        ctx.setConfig(cfg);
        ctx.setAlias(alias);
        ctx.setHolder(new MyInnerKeyHolder(ctx));

        return ctx.getHolder();
    }

    @Override
    public SecretKeyConfig getDefaultConfig() {
        SecretKeyConfig cfg = new SecretKeyConfig();
        cfg.setAlgorithm("AES");
        cfg.setSize(256);
        cfg.setStore(SecretKeyStore.getInstance());
        return cfg;
    }


    private static class MyInnerKeyLS {

        static SecretKey load(SecretKeyContext ctx) {
            try {
                SecretKeyAlias alias = ctx.getAlias();
                String algorithm = ctx.getConfig().getAlgorithm();
                SecretKeyContext c2 = ctx.getStore().get(alias);
                ContentLayer content = c2.getContent();
                byte[] data = content.getBody().toByteArray();

                // SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);
                //      return factory.generateSecret(spec);

                return new SecretKeySpec(data, algorithm);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        static void store(SecretKeyContext ctx, SecretKey key) {
            byte[] data = key.getEncoded();
            SecretKeyAlias alias = ctx.getAlias();

            PropertyTable head = PropertyTable.Factory.create();
            ByteSlice body = new ByteSlice(data);

            head.put("content-type", "application/x-secret-key");
            head.put("content-length", String.valueOf(data.length));
            head.put("secret-key-algorithm", key.getAlgorithm());
            head.put("secret-key-format", key.getFormat());

            ContentLayer content = new ContentLayer();
            content.setHead(head);
            content.setBody(body);

            ctx.setContent(content);
            ctx.getStore().put(alias, ctx);
        }

        static SecretKey gen(SecretKeyContext ctx) {
            try {
                String algorithm = ctx.getConfig().getAlgorithm();
                int size = ctx.getConfig().getSize();
                KeyGenerator kg = KeyGenerator.getInstance(algorithm);
                kg.init(size);
                return kg.generateKey();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    private static class MyInnerKeyHolder implements SecretKeyHolder {

        private final SecretKeyContext context;

        public MyInnerKeyHolder(SecretKeyContext ctx) {
            this.context = ctx;
        }

        @Override
        public SecretKeyAlias alias() {
            return this.context.getAlias();
        }

        @Override
        public SecretKey fetch() {
            SecretKeyAlias alias = this.context.getAlias();
            SecretKeyStore store = this.context.getStore();
            SecretKeyContext c2 = store.get(alias);
            if (c2 == null) {
                throw new RuntimeException("no secret-key with alias: " + alias);
            }
            SecretKey sk = MyInnerKeyLS.load(c2);
            this.context.setKey(sk);
            c2.setKey(sk);
            return sk;
        }

        @Override
        public void reload() {

        }

        @Override
        public boolean create() {
            SecretKeyContext ctx = this.context;
            SecretKeyAlias alias = ctx.getAlias();
            SecretKeyContext c2 = ctx.getStore().get(alias);
            if (c2 != null) {
                return false;
            }
            SecretKey sk = MyInnerKeyLS.gen(ctx);
            MyInnerKeyLS.store(ctx, sk);
            ctx.setKey(sk);
            ctx.getStore().put(alias, ctx);
            return true;
        }

        @Override
        public boolean exists() {
            SecretKeyAlias alias = this.context.getAlias();
            SecretKeyStore store = this.context.getStore();
            SecretKeyContext ctx2 = store.get(alias);
            return (ctx2 != null);
        }
    }
}
