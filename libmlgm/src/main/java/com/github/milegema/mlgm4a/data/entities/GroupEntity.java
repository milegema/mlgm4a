package com.github.milegema.mlgm4a.data.entities;

import com.github.milegema.mlgm4a.data.ids.EntityID;
import com.github.milegema.mlgm4a.data.ids.GroupID;
import com.github.milegema.mlgm4a.data.ids.LongID;

public class GroupEntity extends BaseEntity {

    private GroupID id;

    public GroupEntity() {
    }


    public GroupID getId() {
        return id;
    }

    public void setId(GroupID id) {
        this.id = id;
    }


    @Override
    public void setEntityID(EntityID id) {
        long n = LongID.numberOf(id);
        this.id = new GroupID(n);
    }

    @Override
    public EntityID getEntityID() {
        return this.id;
    }
}
