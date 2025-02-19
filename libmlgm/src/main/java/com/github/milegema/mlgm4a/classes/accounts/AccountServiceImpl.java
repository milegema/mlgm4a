package com.github.milegema.mlgm4a.classes.accounts;

import android.content.Context;

import com.github.milegema.mlgm4a.data.entities.AccountEntity;
import com.github.milegema.mlgm4a.data.ids.AccountID;

import java.util.Collections;
import java.util.List;

public class AccountServiceImpl implements AccountService {

    private AccountDao dao;

    public AccountServiceImpl() {
    }

    public AccountDao getDao() {
        return dao;
    }

    public void setDao(AccountDao dao) {
        this.dao = dao;
    }

    @Override
    public AccountEntity insert(Context ctx, AccountEntity item) {
        return this.dao.insert(null, item);
    }

    @Override
    public AccountEntity update(Context ctx, AccountID id, Updater<AccountID, AccountEntity> fn) {
        return this.dao.update(null, id, fn::onUpdate);
    }

    @Override
    public AccountEntity delete(Context ctx, AccountID id) {
        return this.dao.delete(null, id);
    }

    @Override
    public AccountEntity find(Context ctx, AccountID id) {
        return this.dao.find(null, id);
    }

    @Override
    public List<AccountEntity> list(Context ctx, Filter<AccountID, AccountEntity> filter) {
        if (filter == null) {
            filter = (p1, p2) -> true;
        }
        return this.dao.list(null, filter::accept);
    }
}
