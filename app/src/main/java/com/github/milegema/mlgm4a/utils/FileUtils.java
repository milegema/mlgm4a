package com.github.milegema.mlgm4a.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileUtils {

    private FileUtils() {
    }

    private static final FileIOImpl impl = new FileIOImpl();

    public static byte[] readBinary(Path file) throws IOException {
        return impl.readBinary(file);
    }


    public static String readText(Path file) throws IOException {
        return impl.readText(file);
    }


    public static void writeBinary(byte[] data, Path file, FileOptions flags) throws IOException {
        impl.writeBinary(data, file, flags);
    }


    public static void writeBinary(byte[] data, int offset, int length, Path file, FileOptions flags) throws IOException {
        impl.writeBinary(data, offset, length, file, flags);
    }


    public static void writeText(String text, Path file, FileOptions flags) throws IOException {
        impl.writeText(text, file, flags);
    }


    public static void mkdirs(Path dir) throws IOException {
        if (Files.exists(dir)) {
            return;
        }
        Files.createDirectories(dir);
    }

    public static void mkdirsForFile(Path file) throws IOException {
        mkdirs(file.getParent());
    }
}
