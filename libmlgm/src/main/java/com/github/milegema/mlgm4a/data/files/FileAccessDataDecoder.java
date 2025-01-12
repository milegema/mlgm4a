package com.github.milegema.mlgm4a.data.files;

import java.io.IOException;

public interface FileAccessDataDecoder {

    void decode(FileAccessDataEncoding encoding) throws IOException;

}
