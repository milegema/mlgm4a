package com.github.milegema.mlgm4a.utils;

import java.io.IOException;
import java.nio.file.Path;

public interface FileIO {

    byte[] readBinary(Path file) throws IOException;

    String readText(Path file) throws IOException;

    void writeBinary(byte[] data, Path file, FileOptions flags) throws IOException;

    void writeBinary(byte[] data, int offset, int length, Path file, FileOptions flags) throws IOException;

    void writeText(String text, Path file, FileOptions flags) throws IOException;

}
