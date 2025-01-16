package com.github.milegema.mlgm4a.data.repositories.tables;

import com.github.milegema.mlgm4a.data.ids.Alias;

public class SchemaName extends Alias {

    public SchemaName(String a) {
        super(TableUtils.normalizeName(a));
    }
}
