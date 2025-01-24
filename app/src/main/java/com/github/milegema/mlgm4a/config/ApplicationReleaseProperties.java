package com.github.milegema.mlgm4a.config;

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
        dst.put(Names.application_2_properties, inst + "");
        dst.put(Names.config_default_remote_url, "https://milegema.bitwormhole.com/");
        return dst;
    }

}
