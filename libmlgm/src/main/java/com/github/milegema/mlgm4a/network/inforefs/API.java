package com.github.milegema.mlgm4a.network.inforefs;

import java.io.IOException;

public interface API<REQ, RESP> {

    RemoteService getService();

    RESP invoke(REQ req) throws IOException;

}
