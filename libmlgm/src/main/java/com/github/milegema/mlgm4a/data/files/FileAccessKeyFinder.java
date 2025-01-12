package com.github.milegema.mlgm4a.data.files;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SecretKey;

public final class FileAccessKeyFinder {

    private FileAccessKeyFinder() {
    }

    public static SecretKey findSecretKey(FileAccessKeyRef... sources) {
        if (sources == null) {
            return null;
        }
        for (FileAccessKeyRef src : sources) {
            if (src == null) {
                continue;
            }
            Key k = src.key();
            if (k == null) {
                continue;
            }
            if (k instanceof SecretKey) {
                return (SecretKey) k;
            }
        }
        return null;
    }

    public static PrivateKey findPrivateKey(FileAccessKeyRef... sources) {
        if (sources == null) {
            return null;
        }
        for (FileAccessKeyRef src : sources) {
            if (src == null) {
                continue;
            }
            Key k = src.key();
            if (k == null) {
                continue;
            }
            if (k instanceof PrivateKey) {
                return (PrivateKey) k;
            }
        }
        return null;
    }

    public static PublicKey findPublicKey(FileAccessKeyRef... sources) {
        if (sources == null) {
            return null;
        }
        for (FileAccessKeyRef src : sources) {
            if (src == null) {
                continue;
            }
            Key k = src.key();
            if (k == null) {
                continue;
            }
            if (k instanceof PublicKey) {
                return (PublicKey) k;
            }
        }
        return null;
    }

    public static class Result {
        public PrivateKey private_key;
        public PublicKey public_key;
        public SecretKey secret_key;
    }

    public static Result findOneKey(FileAccessKeyRef... sources) {
        Result res = new Result();
        if (sources == null) {
            return res;
        }
        for (FileAccessKeyRef src : sources) {
            if (src == null) {
                continue;
            }
            Key key = src.key();
            if (key == null) {
                continue;
            }
            if (key instanceof SecretKey) {
                res.secret_key = (SecretKey) key;
                break;
            } else if (key instanceof PublicKey) {
                res.public_key = (PublicKey) key;
                break;
            } else if (key instanceof PrivateKey) {
                res.private_key = (PrivateKey) key;
                break;
            }
        }
        return res;
    }
}
