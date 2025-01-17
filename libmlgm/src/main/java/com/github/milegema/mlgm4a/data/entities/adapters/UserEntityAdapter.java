package com.github.milegema.mlgm4a.data.entities.adapters;

import com.github.milegema.mlgm4a.data.entities.AccountEntity;
import com.github.milegema.mlgm4a.data.repositories.tables.EntityAdapter;
import com.github.milegema.mlgm4a.data.repositories.tables.EntityContext;

public class UserEntityAdapter implements EntityAdapter {
    @Override
    public void convert(EntityContext ec) {

        final AccountEntity entity = (AccountEntity) ec.getEntity();

        if (ec.isEntityToProperties()) {
            // todo ...
        } else {
            // todo ...
        }
    }
}
