package com.github.milegema.mlgm4a.security;

public enum CipherMode {

    NONE,
    ECB, CBC, CTR, CFB, OFB,
    UNKNOWN;

    public static boolean requireIV(CipherMode m) {
        if (m != null) {
            switch (m) {
                case CBC:
                case CTR:
                case CFB:
                case OFB:
                    return true;
            }
        }
        return false;
    }
}
