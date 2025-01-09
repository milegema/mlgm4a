package com.github.milegema.mlgm4a.data.properties;

public final class Names {

    private Names() {
    }

    // block-meta

    public final static String block_meta_id = "id"; //
    public final static String block_meta_iv = "iv"; //
    public final static String block_meta_key = "key"; //
    public final static String block_meta_algorithm = "algorithm"; //
    public final static String block_meta_mode = "mode"; //
    public final static String block_meta_padding = "padding"; //
    public final static String block_meta_type = "type"; //


    // block-data

    public final static String block_type = "block.type";
    public final static String block_name = "block.name";
    public final static String block_ref = "block.ref";
    public final static String block_parent = "block.parent";
    public final static String block_created_at = "block.created_at";
    public final static String block_updated_at = "block.updated_at";
    public final static String block_salt = "block.salt";
    public final static String block_id = "block.id";
    public final static String block_sha256sum = "block.id"; // alias as 'block_id'
    public final static String block_signature = "block.signature";

    // cipher

    public final static String cipher_padding = "cipher.padding";
    public final static String cipher_mode = "cipher.mode";
    public final static String cipher_iv = "cipher.iv";
    public final static String cipher_algorithm = "cipher.algorithm";


    // compression

    public final static String compression_algorithm = "compression.algorithm";


    // content-data

    public final static String content_type = "content.type";
    public final static String content_length = "content.length";


    // layer

    public final static String layer_class = "layer";
    // public final static String layer_index = "layer.index";


    // secret-key

    public final static String secret_key_algorithm = "secret-key.algorithm";
    public final static String secret_key_format = "secret-key.format";
    public final static String secret_key_size = "secret-key.size";

    // signature

    public final static String signature_algorithm = "signature.algorithm";

    // {repo}/config

    public final static String repository_alias = "repository.alias";
    public final static String repository_format_version = "repository.format.version"; // current=1
    public final static String repository_public_key_fingerprint = "repository.public-key.fingerprint";

    public final static String refs_blocks_root = "refs.blocks.root"; // q-name of root-block
    public final static String refs_blocks_app = "refs.blocks.app"; // q-name of app-block
    public final static String refs_blocks_user = "refs.blocks.user"; // q-name of current-user
    public final static String user_email = "user.email"; // current user email
    public final static String user_name = "user.name";// current user name
    public final static String user_alias = "user.alias";// current user public-key alias


    public final static String user_nnn_id = "user.{{q-name}}.id";
    public final static String user_nnn_email = "user.{{q-name}}.email";
    public final static String user_nnn_name = "user.{{q-name}}.name";


    public static class QName {
        final String q;

        public QName(String qName) {
            this.q = qName;
        }

        public String name(String template) {
            return nameOf(template, this.q);
        }
    }


    public static String nameOf(String nameTemplate, String qName) {
        if (nameTemplate == null) {
            nameTemplate = "name.template.is.null";
        }
        if (qName == null) {
            qName = "null";
        }
        final String nt2 = nameTemplate.replace('{', '<').replace('}', '>');
        final String nnn = "<<q-name>>";
        return nt2.replaceFirst(nnn, qName);
    }
}
