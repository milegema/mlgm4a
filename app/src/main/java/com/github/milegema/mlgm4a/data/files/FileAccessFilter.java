package com.github.milegema.mlgm4a.data.files;

import java.io.IOException;

public interface FileAccessFilter {

    FileAccessResponse access(FileAccessRequest request, FileAccessFilterChain chain) throws IOException;

}
