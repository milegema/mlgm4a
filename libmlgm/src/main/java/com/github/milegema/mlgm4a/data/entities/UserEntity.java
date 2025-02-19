package com.github.milegema.mlgm4a.data.entities;

import com.github.milegema.mlgm4a.data.ids.EmailAddress;
import com.github.milegema.mlgm4a.data.ids.EntityID;
import com.github.milegema.mlgm4a.data.ids.LongID;
import com.github.milegema.mlgm4a.data.ids.UserID;
import com.github.milegema.mlgm4a.network.inforefs.RemoteURL;

import java.net.URL;

public class UserEntity extends BaseEntity {

    private UserID id;
    private EmailAddress email;
    private RemoteURL remote;
    private URL avatar;
    private String displayName;

    /**  登录时间, 通过这个字段来判断当前的用户(最后登陆者) */
    private long signedAt;

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

    public URL getAvatar() {
        return avatar;
    }

    public void setAvatar(URL avatar) {
        this.avatar = avatar;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public long getSignedAt() {
        return signedAt;
    }

    public void setSignedAt(long signedAt) {
        this.signedAt = signedAt;
    }
}
