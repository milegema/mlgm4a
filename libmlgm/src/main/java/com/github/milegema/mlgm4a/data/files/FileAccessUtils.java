package com.github.milegema.mlgm4a.data.files;

import com.github.milegema.mlgm4a.errors.BypassErrorFilter;
import com.github.milegema.mlgm4a.errors.ErrorFilter;

import java.util.List;

public final class FileAccessUtils {

    private FileAccessUtils() {
    }


    public interface ForEachBlockCallback {
        void onBlock(FileAccessBlock block) throws Exception;
    }

    public static void forEachBlock(List<FileAccessBlock> list, ErrorFilter ef, ForEachBlockCallback callback) {
        if (list == null || callback == null) {
            return;
        }
        if (ef == null) {
            ef = new BypassErrorFilter();
        }
        for (FileAccessBlock item : list) {
            if (item == null) {
                continue;
            }
            forEachBlock_item(item, ef, callback);
        }
    }

    private static void forEachBlock_item(FileAccessBlock block, ErrorFilter err_filter, ForEachBlockCallback callback) {
        try {
            callback.onBlock(block);
        } catch (Exception e) {
            Throwable e2 = err_filter.doFilter(e);
            if (e2 != null) {
                throw new RuntimeException(e2);
            }
        }
    }


    public static void forEachBlock(FileAccessRequest req, ErrorFilter err_filter, ForEachBlockCallback callback) {
        if (req == null || callback == null) {
            return;
        }
        forEachBlock(req.getBlocks(), err_filter, callback);
    }

    public static void forEachBlock(FileAccessResponse resp, ErrorFilter err_filter, ForEachBlockCallback callback) {
        if (resp == null || callback == null) {
            return;
        }
        forEachBlock(resp.getBlocks(), err_filter, callback);
    }
}
