package com.github.milegema.mlgm4a.services;

import com.github.milegema.mlgm4a.data.entities.DomainEntity;
import com.github.milegema.mlgm4a.data.ids.DomainID;

public interface DomainService {

    DomainEntity find(DomainID id);

}
