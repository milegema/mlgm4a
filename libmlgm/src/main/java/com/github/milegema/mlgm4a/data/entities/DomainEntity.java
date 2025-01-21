package com.github.milegema.mlgm4a.data.entities;

import com.github.milegema.mlgm4a.data.ids.DomainID;
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
    public void setLongID(long id) {
        this.id = new DomainID(id);
    }

    @Override
    public long getLongID() {
        DomainID tmp = this.id;
        if (tmp == null) {
            return 0;
        }
        return tmp.number();
    }

    @Override
    public LongID toLongID() {
        return this.id;
    }

}
