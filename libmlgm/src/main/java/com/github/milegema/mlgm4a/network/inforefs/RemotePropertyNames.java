package com.github.milegema.mlgm4a.network.inforefs;

public interface RemotePropertyNames {


    // common

    String public_key_fingerprint = "key.public.fingerprint";
    String public_key_algorithm = "key.public.algorithm";
    String public_key_encoded = "key.public.encoded"; // optional

    String sign_algorithm = "sign.algorithm";
    String sign_signature = "sign.signature";
    String sign_nonce = "sign.nonce";
    String sign_time = "sign.time";
    String sign_sn = "sign.sn";
    String sign_properties = "sign.properties"; // 参与签名的属性名称列表


    // auth (login)

    String auth_user = "auth.user";
    String auth_secret = "auth.secret";
    String auth_url = "auth.url";
    String auth_action = "auth.action"; // [send|verify|...]
    String auth_mechanism  = "auth.mechanism"; // [EMAIL|...]

}
