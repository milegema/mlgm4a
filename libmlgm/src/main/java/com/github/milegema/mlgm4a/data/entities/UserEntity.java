package com.github.milegema.mlgm4a.data.entities;

import com.github.milegema.mlgm4a.data.ids.LongID;
import com.github.milegema.mlgm4a.data.ids.UserID;

public class UserEntity extends BaseEntity {

    private UserID id;

    public UserEntity() {
    }

    public UserID getId() {
        return id;
    }

    public void setId(UserID id) {
        this.id = id;
    }


    @Override
    public void setLongID(long id) {
        this.id = new UserID(id);
    }

    @Override
    public long getLongID() {
        UserID tmp = this.id;
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
