package com.github.milegema.mlgm4a.data.pem;


import com.github.milegema.mlgm4a.utils.FileOptions;
import com.github.milegema.mlgm4a.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Path;

public final class PEMUtils {

    private PEMUtils() {
    }

    public static PEMDocument read(Path file) throws IOException {
        String text = FileUtils.readText(file);
        return decode(text);
    }

    public static void write(PEMDocument doc, Path dst, FileOptions opt) throws IOException {
        String text = encode(doc);
        FileUtils.writeText(text, dst, opt);
    }

    public static String encode(PEMDocument doc) {
        return PEMEncoder.encode(doc);
    }

    public static PEMDocument decode(String text) {
        return PEMDecoder.decode(text);
    }
}
