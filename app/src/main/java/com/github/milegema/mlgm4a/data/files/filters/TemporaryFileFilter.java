package com.github.milegema.mlgm4a.data.files.filters;

import com.github.milegema.mlgm4a.data.files.FileAccessAction;
import com.github.milegema.mlgm4a.data.files.FileAccessContext;
import com.github.milegema.mlgm4a.data.files.FileAccessFilter;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterChain;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterOrder;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterRegistration;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterRegistry;
import com.github.milegema.mlgm4a.data.files.FileAccessRequest;
import com.github.milegema.mlgm4a.data.files.FileAccessResponse;
import com.github.milegema.mlgm4a.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TemporaryFileFilter implements FileAccessFilterRegistry, FileAccessFilter {

    private final long mStartedAt;

    public TemporaryFileFilter() {
        this.mStartedAt = System.currentTimeMillis();
    }

    @Override
    public FileAccessResponse access(FileAccessRequest request, FileAccessFilterChain chain) throws IOException {
        if (!need_tmp_file(request)) {
            return chain.access(request); // bypass
        }
        final FileAccessContext ctx = request.getContext();
        final Path dst = ctx.getFile();
        final Path tmp = init_tmp_file(request);
        try {
            FileUtils.mkdirsForFile(tmp);
            FileUtils.mkdirsForFile(dst);
            ctx.setFile(tmp);
            FileAccessResponse resp = chain.access(request);
            copy_or_move(tmp, dst);
            return resp;
        } finally {
            ctx.setFile(dst);
            Files.deleteIfExists(tmp);
        }
    }

    private static boolean need_tmp_file(FileAccessRequest req) {
        FileAccessAction action = req.getAction();
        if (action == null) {
            return false;
        }
        switch (action) {
            case CREATE:
            case REWRITE:
                return true;
            default:
                break;
        }
        return false;
    }

    private static void copy_or_move(Path src, Path dst) throws IOException {
        if (src == null || dst == null) {
            return;
        }
        if (!Files.isRegularFile(src)) {
            return;
        }
        Files.deleteIfExists(dst);
        Files.move(src, dst);
    }

    private Path init_tmp_file(FileAccessRequest req) throws IOException {
        final long now = System.currentTimeMillis();
        Path dir = req.getContext().getFile().getParent();
        String prefix = "tmp" + this.mStartedAt + "-" + now + "-";
        String suffix = ".tmp~";
        FileUtils.mkdirs(dir);
        Path tmp = Files.createTempFile(dir, prefix, suffix);
        Files.deleteIfExists(tmp);
        return tmp;
    }

    @Override
    public FileAccessFilterRegistration registration() {
        FileAccessFilterRegistration re = new FileAccessFilterRegistration();
        re.setOrder(FileAccessFilterOrder.Auto);
        re.setFilter(this);
        return re;
    }
}
