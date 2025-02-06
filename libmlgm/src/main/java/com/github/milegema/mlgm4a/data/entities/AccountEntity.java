package com.github.milegema.mlgm4a.data.entities;

import com.github.milegema.mlgm4a.data.ids.AccountID;
import com.github.milegema.mlgm4a.data.ids.EntityID;
import com.github.milegema.mlgm4a.data.ids.LongID;

public class AccountEntity extends BaseEntity {

    private AccountID id;
    private String domain; // @domain-name
    private String username;
    private String label;
    private String description;


    public AccountEntity() {
    }

    public AccountID getId() {
        return id;
    }

    public void setId(AccountID id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public void setEntityID(EntityID id) {
        long n = LongID.numberOf(id);
        this.id = new AccountID(n);
    }

    @Override
    public EntityID getEntityID() {
        return this.id;
    }
}
