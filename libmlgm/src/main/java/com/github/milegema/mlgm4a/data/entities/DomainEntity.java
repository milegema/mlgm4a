package com.github.milegema.mlgm4a.data.entities;

import com.github.milegema.mlgm4a.data.ids.DomainID;
import com.github.milegema.mlgm4a.data.ids.EntityID;
import com.github.milegema.mlgm4a.data.ids.LongID;

public class DomainEntity extends BaseEntity {


    private DomainID id;

    public DomainEntity() {
    }

    public DomainID getId() {
        return id;
    }

    public void setId(DomainID id) {
        this.id = id;
    }


    @Override
    public void setEntityID(EntityID id) {
        long n = LongID.numberOf(id);
        this.id = new DomainID(n);
    }

    @Override
    public EntityID getEntityID() {
        return this.id;
    }
}
