package com.github.milegema.mlgm4a.classes.domains;

import android.content.Context;

import com.github.milegema.mlgm4a.data.entities.DomainEntity;
import com.github.milegema.mlgm4a.data.ids.DomainID;

import java.util.List;

public class DomainServiceImpl implements DomainService {

    private DomainDao dao;

    public DomainServiceImpl() {
    }

    public DomainDao getDao() {
        return dao;
    }

    public void setDao(DomainDao dao) {
        this.dao = dao;
    }

    @Override
    public DomainEntity find(Context ctx, DomainID id) {
        return this.dao.find(null, id);
    }

    @Override
    public List<DomainEntity> list(Context ctx, Filter<DomainID, DomainEntity> filter) {
        if (filter == null) {
            filter = (p1, p2) -> true;
        }
        return this.dao.list(null, filter::accept);
    }

    @Override
    public DomainEntity insert(Context ctx, DomainEntity item) {
        return this.dao.insert(null, item);
    }

    @Override
    public DomainEntity update(Context ctx, DomainID id, Updater<DomainID, DomainEntity> fn) {
        return this.dao.update(null, id, fn::onUpdate);
    }

    @Override
    public DomainEntity delete(Context ctx, DomainID id) {
        return this.dao.delete(null, id);
    }

}
