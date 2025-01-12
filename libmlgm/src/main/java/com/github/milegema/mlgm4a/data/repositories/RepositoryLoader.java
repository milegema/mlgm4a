package com.github.milegema.mlgm4a.data.repositories;

import com.github.milegema.mlgm4a.data.properties.Names;
import com.github.milegema.mlgm4a.data.properties.PropertyGetter;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.security.KeyFingerprint;
import com.github.milegema.mlgm4a.security.SecretKeyHolder;

import java.io.IOException;
import java.security.PublicKey;

import javax.crypto.SecretKey;

public class RepositoryLoader {

    private final RepositoryContext context;

    public RepositoryLoader(RepositoryContext ctx) {
        this.context = ctx;
    }

    public void load() throws IOException {
        Repository repo = context.getRepository();
        this.load_secret_key(repo);
        this.load_repo_config(repo);
    }

    private void load_secret_key(Repository repo) {
        SecretKeyHolder secret_key_holder = context.getSecretKeyHolder();
        SecretKey sk = secret_key_holder.fetch();
        context.setSecretKey(sk);
    }

    private void load_repo_config(Repository repo) throws IOException {
        RepositoryConfig config = repo.config();
        RepositoryConfigCache cache = config.load();
        PropertyTable pt = cache.properties();

        PropertyGetter pGetter = new PropertyGetter(pt);
        int ver = pGetter.getInt(Names.repository_format_version, 0);
        byte[] fingerprint_bin = pGetter.getDataAuto(Names.repository_public_key_fingerprint, new byte[0]);

        // 检查仓库版本号是否匹配
        RepositoryFormatVersion.verify(ver);

        // 检查公钥指纹是否匹配
        PublicKey pk = context.getKeyPair().getPublic();
        KeyFingerprint want = KeyFingerprint.compute(pk);
        KeyFingerprint have = new KeyFingerprint(fingerprint_bin);
        if (!have.equals(want)) {
            String msg = ", want:" + want + " have:" + have;
            throw new RepositoryException("bad repository public key fingerprint" + msg);
        }

        context.setFormatVersion(ver);
        context.setConfiguration(cache);
    }
}
