package com.github.milegema.mlgm4a.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

class FileIOImpl implements FileIO {

    public FileIOImpl() {
    }


    @Override
    public byte[] readBinary(Path file) throws IOException {
        return Files.readAllBytes(file);
    }

    @Override
    public String readText(Path file) throws IOException {
        byte[] data = readBinary(file);
        return new String(data, StandardCharsets.UTF_8);
    }


    @Override
    public void writeBinary(byte[] data, Path file, FileOptions flags) throws IOException {
        innerWriteBinary(data, file, flags);
    }

    @Override
    public void writeBinary(byte[] data, int offset, int length, Path file, FileOptions flags) throws IOException {
        ByteArrayOutputStream tmp = new ByteArrayOutputStream();
        tmp.write(data, offset, length);
        innerWriteBinary(tmp.toByteArray(), file, flags);
    }

    @Override
    public void writeText(String text, Path file, FileOptions flags) throws IOException {
        if (text == null) {
            text = "";
        }
        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        innerWriteBinary(data, file, flags);
    }


    private FileOptions innerPrepareFileOptions(FileOptions opt) {
        if (opt == null) {
            opt = new FileOptions();
        }
        return opt;
    }

    private OpenOption[] innerPrepareOpenOptions(FileOptions opt) {
        List<OpenOption> list = new ArrayList<>();
        if (opt.create) {
            list.add(StandardOpenOption.CREATE);
        }
        if (opt.createNew) {
            list.add(StandardOpenOption.CREATE_NEW);
        }
        if (opt.append) {
            list.add(StandardOpenOption.APPEND);
        }
        if (opt.truncate) {
            list.add(StandardOpenOption.TRUNCATE_EXISTING);
        }
        if (opt.read) {
            list.add(StandardOpenOption.READ);
        }
        if (opt.write) {
            list.add(StandardOpenOption.WRITE);
        }
        return list.toArray(new OpenOption[0]);
    }


    private void innerWriteBinary(byte[] data, Path file, FileOptions opt) throws IOException {
        opt = innerPrepareFileOptions(opt);
        OpenOption[] oo = innerPrepareOpenOptions(opt);
        if (opt.mkdirs) {
            Path dir = file.getParent();
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
        }
        Files.write(file, data, oo);
    }
}
