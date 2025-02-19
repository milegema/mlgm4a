package com.github.milegema.mlgm4a.network.api;

import com.github.milegema.mlgm4a.data.pem.PEMBlock;
import com.github.milegema.mlgm4a.data.pem.PEMDocument;
import com.github.milegema.mlgm4a.data.pem.PEMUtils;
import com.github.milegema.mlgm4a.data.properties.Names;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.data.properties.PropertyTableLS;
import com.github.milegema.mlgm4a.network.inforefs.RemoteAPI;
import com.github.milegema.mlgm4a.network.inforefs.RemoteContext;
import com.github.milegema.mlgm4a.network.inforefs.RemoteServices;
import com.github.milegema.mlgm4a.network.web.WebEntity;
import com.github.milegema.mlgm4a.network.web.WebRequest;
import com.github.milegema.mlgm4a.network.web.WebResponse;
import com.github.milegema.mlgm4a.security.KeyFingerprint;
import com.github.milegema.mlgm4a.security.SimpleSigner;
import com.github.milegema.mlgm4a.security.Token;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;

public final class BindAPI extends RemoteAPI<BindAPI.Request, BindAPI.Response> {

    public BindAPI(RemoteContext ctx) {
        super(ctx, RemoteServices.BIND);
    }

    public static class Request {
        public KeyPair keyPair;
        public PropertyTable properties;
        public Token token;
    }

    public static class Response {
    }

    private static class RequestBuilder {

        ByteSlice signature;
        ByteSlice keyPublic;
        ByteSlice properties; // content

        void init(KeyPair kp, PropertyTable pt, Token token) {

            PublicKey pub_key = kp.getPublic();
            byte[] pub_key_encoded = pub_key.getEncoded();
            KeyFingerprint fp = KeyFingerprint.compute(pub_key);

            // properties
            pt.put(Names.public_key_algorithm, pub_key.getAlgorithm());
            pt.put(Names.public_key_format, pub_key.getFormat());
            pt.put(Names.public_key_fingerprint, fp.toString());
            pt.put(Names.token, token.toString());


            byte[] props = PropertyTableLS.encode(pt);

            // data
            this.properties = new ByteSlice(props);
            this.keyPublic = new ByteSlice(pub_key_encoded);
        }

        void sign(KeyPair kp) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

            String algorithm = "SHA256with" + kp.getPublic().getAlgorithm();
            SimpleSigner ss = new SimpleSigner();

            ss.setContent(this.properties);
            ss.setKey(kp.getPrivate());
            ss.setAlgorithm(algorithm);

            this.signature = ss.sign();
        }

        public void applyTo(WebRequest dst) {

            // create a PEM content

            PEMDocument doc = new PEMDocument();
            PEMBlock block_props = new PEMBlock();
            PEMBlock block_pub_key = new PEMBlock();
            PEMBlock block_signature = new PEMBlock();


            block_pub_key.setType("PUBLIC KEY");
            block_pub_key.setData(this.keyPublic);

            block_props.setType("PROPERTIES");
            block_props.setData(this.properties);

            block_signature.setType("SIGNATURE");
            block_signature.setData(this.signature);

            doc.add(block_pub_key, block_props, block_signature);
            String content = PEMUtils.encode(doc);
            WebEntity entity = new WebEntity();
            entity.setContentType("application/x-pem");
            entity.setContent(new ByteSlice(content));
            dst.setEntity(entity);
        }
    }

    @Override
    protected void encode(Request src, WebRequest dst) throws IOException {
        RequestBuilder builder = new RequestBuilder();
        try {
            builder.init(src.keyPair, src.properties, src.token);
            builder.sign(src.keyPair);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        builder.applyTo(dst);
    }

    @Override
    protected void decode(WebResponse src, Response dst) {

    }

    @Override
    public Response invoke(Request req) throws IOException {
        Response resp = new Response();
        this.execute(req, resp);
        return resp;
    }
}
