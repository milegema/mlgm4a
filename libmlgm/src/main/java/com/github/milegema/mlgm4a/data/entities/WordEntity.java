package com.github.milegema.mlgm4a.data.entities;

import com.github.milegema.mlgm4a.data.ids.AccountID;
import com.github.milegema.mlgm4a.data.ids.DomainID;
import com.github.milegema.mlgm4a.data.ids.SceneID;
import com.github.milegema.mlgm4a.data.ids.UserID;
import com.github.milegema.mlgm4a.data.ids.WordID;
import com.github.milegema.mlgm4a.data.ids.EntityID;
import com.github.milegema.mlgm4a.data.ids.LongID;
import com.github.milegema.mlgm4a.data.repositories.refs.RefName;

/**
 * WordEntity 表示密码的一个具体版本
 */
public class WordEntity extends BaseEntity {

    private WordID id;

    private int length; // password length in char
    private String charset;

    private RefName block;

    private UserID ownerUser;
    private DomainID ownerDomain;
    private AccountID ownerAccount;
    private SceneID ownerScene;

    public WordEntity() {
    }


    public WordID getId() {
        return id;
    }

    public void setId(WordID id) {
        this.id = id;
    }


    @Override
    public void setEntityID(EntityID id) {
        long n = LongID.numberOf(id);
        this.id = new WordID(n);
    }

    @Override
    public EntityID getEntityID() {
        return this.id;
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

    public SceneID getOwnerScene() {
        return ownerScene;
    }

    public void setOwnerScene(SceneID ownerScene) {
        this.ownerScene = ownerScene;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
