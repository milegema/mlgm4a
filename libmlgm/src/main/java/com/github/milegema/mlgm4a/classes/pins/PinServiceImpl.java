package com.github.milegema.mlgm4a.classes.pins;

import android.content.Context;

public class PinServiceImpl implements PinService {

    public PinServiceImpl() {
    }

    @Override
    public void setLock(Context ctx, Params params) {

    }

    @Override
    public void unlock(Context ctx, Params params) {

    }

    @Override
    public boolean isLocked(Context ctx) {
        return false;
    }

    @Override
    public byte[] getPinSalt(Context ctx) {
        return new byte[0];
    }
}
