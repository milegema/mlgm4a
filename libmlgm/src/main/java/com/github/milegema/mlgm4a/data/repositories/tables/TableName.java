package com.github.milegema.mlgm4a.data.repositories.tables;

import com.github.milegema.mlgm4a.data.ids.Alias;

public class TableName extends Alias {

    public TableName(String a) {
        super(TableUtils.normalizeName(a));
    }
}
