package com.github.milegema.mlgm4a.data.files;

import java.io.IOException;

public interface FileAccessFilterChain {

    FileAccessResponse access(FileAccessRequest request) throws IOException;

    FileAccessFilter getNextFilter();

}
