package com.github.milegema.mlgm4a.config;

import androidx.annotation.NonNull;

import com.github.milegema.mlgm4a.data.properties.Names;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;

final class ApplicationDebugProperties {

    private ApplicationDebugProperties() {
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    public static PropertyTable get(PropertyTable dst) {
        Object inst = new ApplicationDebugProperties();

        dst.put(Names.application_2_properties, inst + "");
        dst.put(Names.config_default_remote_url, "http://192.168.0.108:7957/");

        return dst;
    }

}
