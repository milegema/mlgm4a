package com.github.milegema.mlgm4a.data.repositories.tables;

import com.github.milegema.mlgm4a.data.ids.Alias;

public class FieldName extends Alias {

    public FieldName(String a) {
        super(TableUtils.normalizeName(a));
    }
}
