package com.github.milegema.mlgm4a.libmlgm;

import androidx.annotation.NonNull;

import com.github.milegema.mlgm4a.data.properties.Names;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;

final class ApplicationReleaseProperties {
    private ApplicationReleaseProperties() {
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    public static PropertyTable get(PropertyTable dst) {
        Object inst = new ApplicationReleaseProperties();
        dst.put(Names.application_1_properties, inst + "");
        return dst;
    }

}
