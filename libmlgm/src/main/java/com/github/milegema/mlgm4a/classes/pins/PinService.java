package com.github.milegema.mlgm4a.classes.pins;

import android.content.Context;

import com.github.milegema.mlgm4a.data.types.PIN;

public interface PinService {

    public static class Params {
        public PIN.Type type;
        public PIN.Plain plain;
    }

    void setLock(Context ctx, Params params);

    void unlock(Context ctx, Params params);

    boolean isLocked(Context ctx);

    byte[] getPinSalt(Context ctx);

}
