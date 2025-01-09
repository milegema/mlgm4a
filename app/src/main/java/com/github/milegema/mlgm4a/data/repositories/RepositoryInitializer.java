package com.github.milegema.mlgm4a.data.repositories;

import com.github.milegema.mlgm4a.data.properties.Names;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.security.KeyFingerprint;
import com.github.milegema.mlgm4a.security.SecretKeyHolder;

import java.io.IOException;
import java.security.KeyPair;

public class RepositoryInitializer {

    private final RepositoryContext context;

    public RepositoryInitializer(RepositoryContext ctx) {
        this.context = ctx;
    }

    public void init() {
        try {
            this.inner_init_secret_key();
            this.inner_init_repo_config();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void inner_init_secret_key() {
        SecretKeyHolder sk_holder = this.context.getSecretKeyHolder();
        if (!sk_holder.exists()) {
            sk_holder.create();
        }
        context.setSecretKey(sk_holder.fetch());
    }

    private void inner_init_repo_config() throws IOException {

        RepositoryAlias alias = context.getAlias();
        KeyPair pair = context.getKeyPair();
        KeyFingerprint fingerprint = KeyFingerprint.compute(pair.getPublic());
        int ver = context.getFormatVersion();

        PropertyTable pt = PropertyTable.Factory.create();
        pt.put(Names.repository_alias, String.valueOf(alias));
        pt.put(Names.repository_format_version, String.valueOf(ver));
        pt.put(Names.repository_public_key_fingerprint, String.valueOf(fingerprint));

        RepositoryConfig config = context.getConfig();
        config.write(pt);
    }
}
