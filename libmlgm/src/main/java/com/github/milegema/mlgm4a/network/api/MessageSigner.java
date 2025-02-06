package com.github.milegema.mlgm4a.network.api;

import com.github.milegema.mlgm4a.data.properties.PropertySetter;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.network.inforefs.RemotePropertyNames;
import com.github.milegema.mlgm4a.security.HashProvider;
import com.github.milegema.mlgm4a.security.KeyFingerprint;
import com.github.milegema.mlgm4a.security.SecurityRandom;
import com.github.milegema.mlgm4a.security.SimpleSigner;
import com.github.milegema.mlgm4a.security.hash.Hash;
import com.github.milegema.mlgm4a.security.hash.SHA256Provider;
import com.github.milegema.mlgm4a.utils.Base64;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class MessageSigner {

    private PropertyTable properties;
    private Set<String> names;
    private KeyPair keyPair;
    private Hash hash;
    private PublicKey encodedPublicKey; // keyPair 的公钥部分; 如果这个字段不为 null, 公钥的 encoded 将被添加到结果中

    public MessageSigner() {
    }

    private static class Indexer {

        long sn;

        public synchronized long nextSN() {
            this.sn++;
            return this.sn;
        }

        public synchronized ByteSlice nextNonce() {
            byte[] buffer = new byte[32];
            SecurityRandom.getRandom().nextBytes(buffer);
            return new ByteSlice(buffer);
        }
    }


    private final static Indexer indexer = new Indexer();


    private static class Computer {

        private final long time;
        private final long sn;
        private final ByteSlice nonce;
        private final PrivateKey privateKey;
        private final PublicKey publicKey;
        private final ByteSlice encodedPublicKey;

        private ByteSlice content;
        private ByteSlice signature;
        private String signAlgorithm;
        private PropertyTable properties;
        private Set<String> names;
        private Hash hash;

        public Computer(MessageSigner o) {

            this.encodedPublicKey = tryEncodePublicKey(o.encodedPublicKey);

            this.names = o.names;
            this.properties = o.properties;

            this.privateKey = o.keyPair.getPrivate();
            this.publicKey = o.keyPair.getPublic();
            this.hash = o.hash;

            this.time = System.currentTimeMillis();
            this.sn = indexer.nextSN();
            this.nonce = indexer.nextNonce();
        }

        private static ByteSlice tryEncodePublicKey(PublicKey pk) {
            if (pk == null) {
                return null;
            }
            byte[] bin = pk.getEncoded();
            return new ByteSlice(bin);
        }

        private void prepareAlgorithm() {
            PublicKey pk = this.publicKey;
            Hash h = this.hash;
            if (h == null) {
                HashProvider provider = new SHA256Provider();
                h = provider.hash();
                this.hash = h;
            }
            this.signAlgorithm = h.algorithm() + "with" + pk.getAlgorithm();
        }


        private void preparePropertyNames() {

            Set<String> name_set = this.names;
            if (name_set == null) {
                name_set = new HashSet<>();
                this.names = name_set;
            }

            if (this.encodedPublicKey != null) {
                names.add(RemotePropertyNames.public_key_encoded);
            }

            names.add(RemotePropertyNames.sign_sn);
            names.add(RemotePropertyNames.sign_time);
            names.add(RemotePropertyNames.sign_nonce);
            names.add(RemotePropertyNames.sign_algorithm);
            names.add(RemotePropertyNames.sign_signature);
            names.add(RemotePropertyNames.sign_properties);

            names.add(RemotePropertyNames.public_key_algorithm);
            names.add(RemotePropertyNames.public_key_fingerprint);
        }

        private void preparePropertyValues() {

            PropertyTable pt = this.properties;
            if (pt == null) {
                pt = PropertyTable.Factory.create();
                this.properties = pt;
            }

            String pub_key_algorithm = this.publicKey.getAlgorithm();
            KeyFingerprint pub_key_finger = KeyFingerprint.compute(this.publicKey);
            PropertySetter setter = new PropertySetter(pt);

            if (this.encodedPublicKey != null) {
                byte[] bin = this.encodedPublicKey.toByteArray();
                setter.putBase64(RemotePropertyNames.public_key_encoded, bin);
            }

            setter.put(RemotePropertyNames.sign_sn, this.sn);
            setter.put(RemotePropertyNames.sign_time, this.time);
            setter.putHex(RemotePropertyNames.sign_nonce, this.nonce.toByteArray());
            setter.put(RemotePropertyNames.sign_algorithm, this.signAlgorithm);

            setter.put(RemotePropertyNames.public_key_algorithm, pub_key_algorithm);
            setter.put(RemotePropertyNames.public_key_fingerprint, String.valueOf(pub_key_finger));
        }


        public void computeSignPropertyNameList() {
            String sep = "";
            final String key = RemotePropertyNames.sign_properties;
            final StringBuilder builder = new StringBuilder();
            final Set<String> name_set = this.names;
            name_set.add(key);
            String[] name_array = name_set.toArray(new String[0]);
            Arrays.sort(name_array);
            for (String name : name_array) {
                builder.append(sep).append(name);
                sep = ",";
            }
            String value = builder.toString();
            this.properties.put(key, value);
        }


        public void computeSignature() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

            SimpleSigner ss = new SimpleSigner();
            ss.setKey(this.privateKey);
            ss.setAlgorithm(this.signAlgorithm);
            ss.setContent(this.content);

            this.signature = ss.sign();
        }

        public void computeContent() {
            PropertyTable pt = this.properties;
            String[] names = this.names.toArray(new String[0]);
            Arrays.sort(names);
            StringBuilder builder = new StringBuilder();
            for (String name : names) {
                String value = pt.get(name);
                if (value == null) {
                    value = "";
                }
                builder.append(value);
            }
            String text = builder.toString();
            byte[] bin = text.getBytes(StandardCharsets.UTF_8);
            this.content = new ByteSlice(bin);
        }

        public void checkProperties() {
            String[] name_list = this.names.toArray(new String[0]);
            for (String name : name_list) {
                String value = this.properties.get(name);
                if (value == null) {
                    throw new RuntimeException("no required property: " + name);
                }
            }
        }

        public void holdResult() {
            ByteSlice res = this.signature;
            String key = RemotePropertyNames.sign_signature;
            String value = Base64.encode(res.toByteArray());
            this.properties.put(key, value);
        }
    }


    public void sign() {
        try {
            Computer computer = new Computer(this);
            computer.prepareAlgorithm();
            computer.preparePropertyNames();
            computer.preparePropertyValues();
            computer.computeSignPropertyNameList();
            computer.computeContent();
            computer.computeSignature();
            computer.holdResult();
            computer.checkProperties();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Hash getHash() {
        return hash;
    }

    public void setHash(Hash hash) {
        this.hash = hash;
    }

    public PropertyTable getProperties() {
        return properties;
    }

    public void setProperties(PropertyTable properties) {
        this.properties = properties;
    }

    public Set<String> getNames() {
        return names;
    }

    public void setNames(Set<String> names) {
        this.names = names;
    }

    public void setNames(List<String> src_names) {
        this.names = new HashSet<>(src_names);
    }

    public void setNames(String[] src_names) {
        this.names = new HashSet<>(Arrays.asList(src_names));
    }

    public PublicKey getEncodedPublicKey() {
        return encodedPublicKey;
    }

    public void setEncodedPublicKey(PublicKey encodedPublicKey) {
        this.encodedPublicKey = encodedPublicKey;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }
}
