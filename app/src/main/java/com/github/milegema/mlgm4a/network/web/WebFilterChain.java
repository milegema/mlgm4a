package com.github.milegema.mlgm4a.network.web;

import java.io.IOException;

public interface WebFilterChain {

    WebResponse execute(WebRequest request) throws IOException;

}
