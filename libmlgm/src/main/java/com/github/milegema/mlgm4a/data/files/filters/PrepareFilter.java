package com.github.milegema.mlgm4a.data.files.filters;

import com.github.milegema.mlgm4a.data.files.FileAccessContext;
import com.github.milegema.mlgm4a.data.files.FileAccessDataCodecGroup;
import com.github.milegema.mlgm4a.data.files.FileAccessFilter;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterChain;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterOrder;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterRegistration;
import com.github.milegema.mlgm4a.data.files.FileAccessFilterRegistry;
import com.github.milegema.mlgm4a.data.files.FileAccessRequest;
import com.github.milegema.mlgm4a.data.files.FileAccessResponse;

import java.io.IOException;

public class PrepareFilter implements FileAccessFilterRegistry, FileAccessFilter {

    public PrepareFilter() {
    }

    @Override
    public FileAccessResponse access(FileAccessRequest request, FileAccessFilterChain chain) throws IOException {
        FileAccessContext ctx = request.getContext();
        ctx.setCodecGroup(new FileAccessDataCodecGroup());
        return chain.access(request);
    }

    @Override
    public FileAccessFilterRegistration registration() {
        FileAccessFilterRegistration re = new FileAccessFilterRegistration();
        re.setOrder(FileAccessFilterOrder.Auto);
        re.setFilter(this);
        return re;
    }
}
