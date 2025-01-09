package com.github.milegema.mlgm4a.data.repositories;

import com.github.milegema.mlgm4a.data.files.FileAccessAction;
import com.github.milegema.mlgm4a.data.files.RepositoryFileReader;
import com.github.milegema.mlgm4a.data.files.RepositoryFileWriter;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.data.properties.PropertyTableLS;
import com.github.milegema.mlgm4a.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RepoConfigImpl implements RepositoryConfig {

    private final RepositoryContext context;

    public RepoConfigImpl(RepositoryContext ctx) {
        this.context = ctx;
    }

    private class MyCache implements RepositoryConfigCache {

        private final PropertyTable m_cache_data;
        private final long m_loaded_at;

        MyCache(PropertyTable pt) {
            this.m_cache_data = pt;
            this.m_loaded_at = System.currentTimeMillis();
        }

        @Override
        public RepositoryConfig source() {
            return RepoConfigImpl.this;
        }

        @Override
        public PropertyTable properties() {
            return this.m_cache_data;
        }

        @Override
        public long loadedAt() {
            return this.m_loaded_at;
        }
    }


    private Path getFile() {
        return context.getLayout().getConfig();
    }

    @Override
    public PropertyTable read() throws IOException {
        Path file = getFile();
        RepositoryFileReader reader = new RepositoryFileReader(context);
        String text = reader.readText(file);
        return PropertyTableLS.parse(text);
    }

    @Override
    public void write(PropertyTable pt) throws IOException {

        Path file = getFile();
        RepositoryFileWriter writer = new RepositoryFileWriter(context);

        if (Files.exists(file)) {
            // re-write
            writer.setAction(FileAccessAction.REWRITE);
        } else {
            // create
            writer.setAction(FileAccessAction.CREATE);
            FileUtils.mkdirsForFile(file);
        }

        String text = PropertyTableLS.stringify(pt);
        writer.write(text, file);
    }

    @Override
    public RepositoryConfigCache load() throws IOException {
        PropertyTable pt = this.read();
        return new MyCache(pt);
    }
}
