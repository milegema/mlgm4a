package com.github.milegema.mlgm4a.config;

import androidx.annotation.NonNull;

import com.github.milegema.mlgm4a.data.properties.Names;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;

final class ApplicationDefaultProperties {

    private ApplicationDefaultProperties() {
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    public static PropertyTable get(PropertyTable dst) {
        Object inst = new ApplicationDefaultProperties();
        dst.put(Names.application_2_properties, inst + "");
        return dst;
    }

}
