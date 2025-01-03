package com.github.milegema.mlgm4a.network.web;

import java.io.IOException;

public interface WebFilter {

    WebResponse execute(WebRequest request, WebFilterChain next) throws IOException;

}
