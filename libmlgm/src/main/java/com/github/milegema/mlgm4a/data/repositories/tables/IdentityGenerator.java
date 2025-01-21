package com.github.milegema.mlgm4a.data.repositories.tables;

import com.github.milegema.mlgm4a.data.ids.EntityID;

public interface IdentityGenerator {

    EntityID generateID(EntityContext ec);

}
