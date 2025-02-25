package com.github.milegema.mlgm4a.libmlgm;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.github.milegema.mlgm4a.data.files.FileAccessFilterChainBuilder;
import com.github.milegema.mlgm4a.data.files.RepositoryFileContext;
import com.github.milegema.mlgm4a.data.files.filters.CompressionFilter;
import com.github.milegema.mlgm4a.data.files.filters.ContentFilter;
import com.github.milegema.mlgm4a.data.files.filters.EncryptionFilter;
import com.github.milegema.mlgm4a.data.files.filters.FileStorageFilter;
import com.github.milegema.mlgm4a.data.files.filters.SumFilter;
import com.github.milegema.mlgm4a.security.KeyPairAlias;
import com.github.milegema.mlgm4a.security.KeyPairHolder;
import com.github.milegema.mlgm4a.security.KeyPairManager;
import com.github.milegema.mlgm4a.security.KeyPairManagerImpl;

import java.nio.file.Path;

public final class AndroidTestComFactory {

    public static Context getApplicationContext() {
        return ApplicationProvider.getApplicationContext();
    }


    public static RepositoryFileContext createFileRepositoryContext(Context ctx) {

        // init key-pair
        KeyPairManager kpm = createKeyPairManager(ctx);
        KeyPairHolder key_pair_h = kpm.get(KeyPairAlias.parse("test"));
        if (!key_pair_h.exists()) {
            key_pair_h.create();
        }

        // init folder
        Path file_dir = ctx.getFilesDir().toPath();

        // init chain
        FileAccessFilterChainBuilder chain_builder = new FileAccessFilterChainBuilder();
        chain_builder.add(new ContentFilter());
        chain_builder.add(new SumFilter());
        chain_builder.add(new CompressionFilter());
        chain_builder.add(new EncryptionFilter());
        chain_builder.add(new FileStorageFilter());

        // make context
        RepositoryFileContext file_repo_ctx = new RepositoryFileContext();
        file_repo_ctx.setKeyPair(key_pair_h.fetch());
        file_repo_ctx.setFolder(file_dir.resolve("tmp/test/repo/dir"));
        file_repo_ctx.setChain(chain_builder.create());
        return file_repo_ctx;
    }


    public static KeyPairManager createKeyPairManager(Context ctx) {
        return new KeyPairManagerImpl();
    }

}
