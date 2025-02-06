package com.github.milegema.mlgm4a.data.entities;

import com.github.milegema.mlgm4a.data.ids.EntityWithID;
import com.github.milegema.mlgm4a.data.ids.GroupID;
import com.github.milegema.mlgm4a.data.ids.UUID;
import com.github.milegema.mlgm4a.data.ids.UserID;
import com.github.milegema.mlgm4a.utils.Time;

public abstract class BaseEntity implements EntityWithID {

    private UUID uuid;
    private UserID owner;
    private UserID creator;
    private UserID committer;
    private GroupID group;
    private Time createdAt;
    private Time updatedAt;
    private Time deletedAt;

    public BaseEntity() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UserID getOwner() {
        return owner;
    }

    public void setOwner(UserID owner) {
        this.owner = owner;
    }

    public UserID getCreator() {
        return creator;
    }

    public void setCreator(UserID creator) {
        this.creator = creator;
    }

    public UserID getCommitter() {
        return committer;
    }

    public void setCommitter(UserID committer) {
        this.committer = committer;
    }

    public GroupID getGroup() {
        return group;
    }

    public void setGroup(GroupID group) {
        this.group = group;
    }

    public Time getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Time updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Time getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Time createdAt) {
        this.createdAt = createdAt;
    }

    public Time getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Time deletedAt) {
        this.deletedAt = deletedAt;
    }

}
