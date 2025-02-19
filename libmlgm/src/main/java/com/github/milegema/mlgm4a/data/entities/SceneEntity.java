package com.github.milegema.mlgm4a.data.entities;

import com.github.milegema.mlgm4a.data.ids.AccountID;
import com.github.milegema.mlgm4a.data.ids.DomainID;
import com.github.milegema.mlgm4a.data.ids.EntityID;
import com.github.milegema.mlgm4a.data.ids.LongID;
import com.github.milegema.mlgm4a.data.ids.SceneID;
import com.github.milegema.mlgm4a.data.ids.UserID;
import com.github.milegema.mlgm4a.data.repositories.refs.RefName;

public class SceneEntity extends BaseEntity {

    private SceneID id;
    private String label;
    private String description;

    private RefName block;

    private UserID ownerUser;
    private DomainID ownerDomain;
    private AccountID ownerAccount;

    public SceneEntity() {
    }


    public SceneID getId() {
        return id;
    }

    public void setId(SceneID id) {
        this.id = id;
    }

    public RefName getBlock() {
        return block;
    }

    public void setBlock(RefName block) {
        this.block = block;
    }

    public UserID getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(UserID ownerUser) {
        this.ownerUser = ownerUser;
    }

    public DomainID getOwnerDomain() {
        return ownerDomain;
    }

    public void setOwnerDomain(DomainID ownerDomain) {
        this.ownerDomain = ownerDomain;
    }

    public AccountID getOwnerAccount() {
        return ownerAccount;
    }

    public void setOwnerAccount(AccountID ownerAccount) {
        this.ownerAccount = ownerAccount;
    }

    @Override
    public void setEntityID(EntityID id) {
        long n = LongID.numberOf(id);
        this.id = new SceneID(n);
    }

    @Override
    public EntityID getEntityID() {
        return this.id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
