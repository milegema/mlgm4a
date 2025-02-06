package com.github.milegema.mlgm4a.network.web;

public interface HTTP {

    String method_get = "GET";
    String method_post = "POST";
    String method_put = "PUT";
    String method_delete = "DELETE";

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // header fields

    String header_authorization = "Authorization";
    String header_content_length = "Content-Length";
    String header_content_type = "Content-Type";

    String header_www_authenticate = "WWW-Authenticate";

    String header_x_token = "X-Token";
    String header_x_set_token = "X-Set-Token";


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // status code

    int status_ok = 200;

}
