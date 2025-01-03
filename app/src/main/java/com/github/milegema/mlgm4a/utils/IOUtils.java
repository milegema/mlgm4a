package com.github.milegema.mlgm4a.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class IOUtils {

    private IOUtils() {
    }

    public static void close(Closeable c) {
        if (c == null) {
            return;
        }
        try {
            c.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public static long copyAll(InputStream src, OutputStream dst, byte[] buffer) throws IOException {
        if (src == null || dst == null) {
            return 0;
        }
        if (buffer == null) {
            buffer = new byte[1024 * 4];
        }
        long total = 0;
        for (; ; ) {
            int cb = src.read(buffer);
            if (cb < 0) {
                break;
            } else if (cb == 0) {
                Time.sleep(3);
                continue;
            }
            dst.write(buffer, 0, cb);
            total += cb;
        }
        return total;
    }

    public static long copyAll(InputStream src, OutputStream dst) throws IOException {
        return copyAll(src, dst, null);
    }
}
