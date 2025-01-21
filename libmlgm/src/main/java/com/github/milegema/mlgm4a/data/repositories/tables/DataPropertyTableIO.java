package com.github.milegema.mlgm4a.data.repositories.tables;

import com.github.milegema.mlgm4a.data.files.FileAccessAction;
import com.github.milegema.mlgm4a.data.files.FileAccessBlock;
import com.github.milegema.mlgm4a.data.files.FileAccessDataEncoding;
import com.github.milegema.mlgm4a.data.files.FileAccessDataState;
import com.github.milegema.mlgm4a.data.files.FileAccessResponse;
import com.github.milegema.mlgm4a.data.files.RepositoryFileCallback;
import com.github.milegema.mlgm4a.data.files.RepositoryFileReader;
import com.github.milegema.mlgm4a.data.files.RepositoryFileWriter;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.data.properties.PropertyTableLS;
import com.github.milegema.mlgm4a.data.repositories.RepositoryContext;
import com.github.milegema.mlgm4a.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DataPropertyTableIO {

    private final DatabaseCache cache;

    public DataPropertyTableIO(DatabaseContext ctx) {
        this.cache = new DatabaseCache(ctx);
    }

    private static class DatabaseCache {

        final Map<Path, TableCache> tables;
        final DatabaseContext context;


        DatabaseCache(DatabaseContext ctx) {
            this.tables = new HashMap<>();
            this.context = ctx;
        }

        void reset() {
        }
    }

    private static class FileAccessResponseHolder {
        public FileAccessResponse response;

        public int countBlocks() {
            FileAccessResponse resp = this.response;
            if (resp == null) {
                return 0;
            }
            List<FileAccessBlock> blocks = resp.getBlocks();
            if (blocks == null) {
                return 0;
            }
            return blocks.size();
        }
    }

    private static class TableCache {
        final Path file;
        final DatabaseCache parent;
        PropertyTable inputBuffer; // for reader
        PropertyTable outputBuffer; // for writer

        boolean dirty;
        boolean wantRewrite;

        TableCache(Path _file, DatabaseCache _parent) {
            this.parent = _parent;
            this.file = _file;
        }

        void load(PropertyTable buffer) throws IOException {
            if (!Files.exists(this.file)) {
                return;
            }
            FileAccessResponseHolder fa_holder = new FileAccessResponseHolder();
            RepositoryContext repo_ctx = this.parent.context.getParent();
            RepositoryFileReader reader = new RepositoryFileReader(repo_ctx);
            reader.setCallback(new RepositoryFileCallback() {
                @Override
                public void onDecodeBlockEnd(FileAccessDataState state, FileAccessDataEncoding encoding) {
                    super.onDecodeBlockEnd(state, encoding);
                    fa_holder.response = encoding.getResponse();
                }
            });
            if (fa_holder.countBlocks() > 32) {
                this.wantRewrite = true;
            }
            String text = reader.readText(this.file);
            PropertyTable tmp = PropertyTableLS.parse(text);
            buffer.importAll(tmp.exportAll(null));
        }

        void store(PropertyTable buffer, boolean rewrite) throws IOException {
            if (buffer == null) {
                return;
            }
            if (buffer.size() == 0) {
                return;
            }
            RepositoryContext repo_ctx = this.parent.context.getParent();
            RepositoryFileWriter writer = new RepositoryFileWriter(repo_ctx);
            String text = PropertyTableLS.stringify(buffer);
            if (Files.exists(this.file)) {
                writer.setAction(rewrite ? FileAccessAction.REWRITE : FileAccessAction.APPEND);
            } else {
                writer.setAction(FileAccessAction.CREATE);
                FileUtils.mkdirsForFile(this.file);
            }
            writer.write(text, this.file);
        }

        PropertyTable fetch(boolean reload) {
            PropertyTable buffer = this.inputBuffer;
            if (buffer != null && !reload) {
                return buffer;
            }
            buffer = PropertyTable.Factory.create();
            try {
                this.load(buffer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.inputBuffer = buffer;
            return buffer;
        }

        void put(PropertyTable pt) {
            if (pt == null) {
                return;
            }
            PropertyTable older = this.fetch(false);
            PropertyTable append = this.outputBuffer;
            if (append == null) {
                append = PropertyTable.Factory.create();
                this.outputBuffer = append;
            }
            Map<String, String> tmp = pt.exportAll(null);
            if (tmp.isEmpty()) {
                return;
            }
            older.importAll(tmp);
            append.importAll(tmp);
            this.dirty = true;
        }

        void flush() {
            final boolean rewrite = this.wantRewrite;
            PropertyTable buffer = this.outputBuffer;
            if (buffer == null) {
                return;
            }
            if (!this.dirty) {
                return;
            }
            if (rewrite) {
                PropertyTable[] src_list = {this.inputBuffer, buffer};
                Map<String, String> tmp = new HashMap<>();
                for (PropertyTable src : src_list) {
                    if (src == null) {
                        continue;
                    }
                    tmp = src.exportAll(tmp);
                }
                buffer = PropertyTable.Factory.create();
                buffer.importAll(tmp);
            }
            try {
                this.store(buffer, rewrite);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.wantRewrite = false;
            this.outputBuffer = null;
            this.dirty = false;
        }
    }

    private TableCache getTableCache(Path file, boolean create) {
        TableCache item = this.cache.tables.get(file);
        if (item == null && create) {
            item = new TableCache(file, this.cache);
            this.cache.tables.put(file, item);
        }
        return item;
    }

    public PropertyTable read(Path file) {
        TableCache table = getTableCache(file, true);
        return table.fetch(false);
    }

    public void write(PropertyTable data, Path file) {
        TableCache table = getTableCache(file, true);
        table.put(data);
    }

    public void flush() {
        Collection<TableCache> all = this.cache.tables.values();
        for (TableCache table : all) {
            table.flush();
        }
    }

    public void clearInputCache() {
        Collection<TableCache> all = this.cache.tables.values();
        for (TableCache table : all) {
            table.inputBuffer = null;
        }
    }
}
