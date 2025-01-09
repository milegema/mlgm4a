package com.github.milegema.mlgm4a.data.files.filters;

import com.github.milegema.mlgm4a.data.files.FileAccessBlock;
import com.github.milegema.mlgm4a.data.files.FileAccessFilter;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterChain;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterOrder;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterRegistration;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterRegistry;
import com.github.milegema.mlgm4a.data.files.FileAccessRequest;
import com.github.milegema.mlgm4a.data.files.FileAccessResponse;
import com.github.milegema.mlgm4a.data.files.layers.PemLayer;
import com.github.milegema.mlgm4a.data.pem.PEMBlock;
import com.github.milegema.mlgm4a.data.pem.PEMDocument;
import com.github.milegema.mlgm4a.data.pem.PEMUtils;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PemBlockFilter implements FileAccessFilterRegistry, FileAccessFilter {

    public PemBlockFilter() {
    }

    @Override
    public FileAccessResponse access(FileAccessRequest request, FileAccessFilterChain chain) throws IOException {
        if (FileAccessRequest.isWrite(request)) {
            onWrite(request);
        }
        FileAccessResponse resp = chain.access(request);
        if (FileAccessRequest.isRead(request)) {
            onRead(request, resp);
        }
        return resp;
    }

    private void onRead(FileAccessRequest req, FileAccessResponse resp) {
        byte[] raw = resp.getRaw().toByteArray();
        String pem_text = new String(raw, StandardCharsets.UTF_8);
        PEMDocument doc = PEMUtils.decode(pem_text);
        PEMBlock[] src = doc.getBlocks();
        List<FileAccessBlock> dst = new ArrayList<>();
        for (PEMBlock b1 : src) {
            if (b1 == null) {
                continue;
            }
            FileAccessBlock b2 = this.decode_block(b1);
            dst.add(b2);
        }
        resp.setBlocks(dst);
    }

    private void onWrite(FileAccessRequest req) {
        List<FileAccessBlock> src = req.getBlocks();
        PEMBlock[] dst = new PEMBlock[src.size()];
        for (int i = 0; i < dst.length; ++i) {
            FileAccessBlock block1 = src.get(i);
            PEMBlock block2 = this.encode_block(block1);
            dst[i] = block2;
        }
        PEMDocument doc = new PEMDocument();
        doc.setBlocks(dst);
        String pem_text = PEMUtils.encode(doc);
        req.setRaw(new ByteSlice(pem_text.getBytes(StandardCharsets.UTF_8)));
    }

    private PEMBlock encode_block(FileAccessBlock src) {
        PemLayer pl = src.getPemLayer();
        PEMBlock dst = new PEMBlock();
        dst.setType(pl.getType());
        dst.setData(pl.getData());
        return dst;
    }

    private FileAccessBlock decode_block(PEMBlock src) {
        FileAccessBlock dst = new FileAccessBlock();
        PemLayer pl = dst.getPemLayer();
        pl.setType(src.getType());
        pl.setData(src.getData());
        return dst;
    }

    @Override
    public FileAccessFilterRegistration registration() {
        FileAccessFilterRegistration re = new FileAccessFilterRegistration();
        re.setOrder(FileAccessFilterOrder.Auto);
        re.setFilter(this);
        return re;
    }
}
