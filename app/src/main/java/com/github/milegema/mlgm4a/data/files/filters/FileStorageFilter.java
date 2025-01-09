package com.github.milegema.mlgm4a.data.files.filters;

import com.github.milegema.mlgm4a.data.files.FileAccessAction;
import com.github.milegema.mlgm4a.data.files.FileAccessFilter;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterChain;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterOrder;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterRegistration;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterRegistry;
import com.github.milegema.mlgm4a.data.files.FileAccessRequest;
import com.github.milegema.mlgm4a.data.files.FileAccessResponse;
import com.github.milegema.mlgm4a.utils.ByteSlice;
import com.github.milegema.mlgm4a.utils.FileOptions;
import com.github.milegema.mlgm4a.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileStorageFilter implements FileAccessFilterRegistry, FileAccessFilter {

    public FileStorageFilter() {
    }

    @Override
    public FileAccessResponse access(FileAccessRequest request, FileAccessFilterChain chain) throws IOException {
        if (FileAccessRequest.isWrite(request)) {
            this.onWrite(request);
        }
        FileAccessResponse resp = chain.access(request);
        if (resp == null) {
            resp = new FileAccessResponse();
            resp.setContext(request.getContext());
            resp.setBlocks(new ArrayList<>());
            resp.setRaw(new ByteSlice());
            resp.setRequest(request);
        }
        if (FileAccessRequest.isRead(request)) {
            this.onRead(request, resp);
        }
        return resp;
    }

    private void onRead(FileAccessRequest request, FileAccessResponse resp) throws IOException {
        Path file = request.getContext().getFile();
        byte[] data = FileUtils.readBinary(file);
        resp.setRaw(new ByteSlice(data));
    }


    private void onWrite(FileAccessRequest request) throws IOException {
        FileOptions op = new FileOptions();
        FileAccessAction action = request.getAction();
        Path file = request.getContext().getFile();
        byte[] data = request.getRaw().toByteArray();
        switch (action) {
            case REWRITE:
                op.truncate = true;
                break;
            case CREATE:
                op.createNew = true;
                op.mkdirs = true;
                break;
            case APPEND:
                op.append = true;
                break;
            default:
                break;
        }
        op.write = true;
        FileUtils.writeBinary(data, file, op);
    }

    @Override
    public FileAccessFilterRegistration registration() {
        FileAccessFilterRegistration re = new FileAccessFilterRegistration();
        re.setOrder(FileAccessFilterOrder.Auto);
        re.setFilter(this);
        return re;
    }
}
