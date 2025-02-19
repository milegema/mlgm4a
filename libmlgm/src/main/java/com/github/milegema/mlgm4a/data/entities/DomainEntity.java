package com.github.milegema.mlgm4a.data.entities;

import com.github.milegema.mlgm4a.data.ids.DomainID;
import com.github.milegema.mlgm4a.data.ids.EntityID;
import com.github.milegema.mlgm4a.data.ids.LongID;
import com.github.milegema.mlgm4a.data.ids.UserID;
import com.github.milegema.mlgm4a.data.repositories.refs.RefName;

import java.net.URL;

public class DomainEntity extends BaseEntity {

    private DomainID id;

    private String name; // the domain-name
    private String label;
    private String description;
    private URL icon;

    private RefName block;
    private UserID ownerUser;


    public DomainEntity() {
    }

    public DomainID getId() {
        return id;
    }

    public void setId(DomainID id) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public URL getIcon() {
        return icon;
    }

    public void setIcon(URL icon) {
        this.icon = icon;
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
