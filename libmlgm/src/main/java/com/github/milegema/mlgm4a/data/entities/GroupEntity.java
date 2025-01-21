package com.github.milegema.mlgm4a.data.entities;

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
    public void setLongID(long id) {
        this.id = new GroupID(id);
    }

    @Override
    public long getLongID() {
        GroupID tmp = this.id;
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
