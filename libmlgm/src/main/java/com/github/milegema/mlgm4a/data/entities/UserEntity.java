package com.github.milegema.mlgm4a.data.entities;

import com.github.milegema.mlgm4a.data.ids.EmailAddress;
import com.github.milegema.mlgm4a.data.ids.EntityID;
import com.github.milegema.mlgm4a.data.ids.LongID;
import com.github.milegema.mlgm4a.data.ids.UserID;
import com.github.milegema.mlgm4a.network.inforefs.RemoteURL;

public class UserEntity extends BaseEntity {

    private UserID id;
    private EmailAddress email;
    private RemoteURL remote;

    public UserEntity() {
    }

    public UserID getId() {
        return id;
    }

    public void setId(UserID id) {
        this.id = id;
    }

    public EmailAddress getEmail() {
        return email;
    }

    public void setEmail(EmailAddress email) {
        this.email = email;
    }

    public RemoteURL getRemote() {
        return remote;
    }

    public void setRemote(RemoteURL remote) {
        this.remote = remote;
    }

    @Override
    public void setEntityID(EntityID id) {
        long n = LongID.numberOf(id);
        this.id = new UserID(n);
    }

    @Override
    public EntityID getEntityID() {
        return this.id;
    }
}
