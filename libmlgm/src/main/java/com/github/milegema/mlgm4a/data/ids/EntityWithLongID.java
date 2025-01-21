package com.github.milegema.mlgm4a.data.ids;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface EntityWithLongID {

    void setLongID(long id);

    long getLongID();

    LongID toLongID();

}
