package com.github.milegema.mlgm4a.data.files.filters;

import com.github.milegema.mlgm4a.data.files.FileAccessFilterChain;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterChainBuilder;

public final class DefaultFilterChainFactory {

    private DefaultFilterChainFactory() {
    }

    public static FileAccessFilterChainBuilder createNewChainBuilder() {
        FileAccessFilterChainBuilder builder = new FileAccessFilterChainBuilder();

        builder.add(new PrepareFilter());
        builder.add(new TemporaryFileFilter());

        builder.add(new ContentFilter());
        builder.add(new SumFilter());
        builder.add(new CompressionFilter());
        builder.add(new EncryptionFilter());

        builder.add(new CodecFilter());
        builder.add(new PemBlockFilter());
        builder.add(new FileStorageFilter());

        return builder;
    }

    public static FileAccessFilterChain createNewChain() {
        return createNewChainBuilder().create();
    }
}
