package com.github.milegema.mlgm4a.classes.domains;

import com.github.milegema.mlgm4a.data.databases.AutoCommitter;
import com.github.milegema.mlgm4a.data.databases.UserDBA;
import com.github.milegema.mlgm4a.data.entities.DomainEntity;
import com.github.milegema.mlgm4a.data.ids.DomainID;
import com.github.milegema.mlgm4a.data.databases.DB;

import java.util.Collections;
import java.util.List;

public class DomainDaoImpl implements DomainDao {

    private UserDBA dba;

    public DomainDaoImpl() {
    }

    public UserDBA getDba() {
        return dba;
    }

    public void setDba(UserDBA dba) {
        this.dba = dba;
    }

    @Override
    public DomainEntity insert(DB db, DomainEntity item) {
        final AutoCommitter committer = new AutoCommitter();
        db = committer.init(db, this.dba);
        item = db.create(item);
        committer.finish();
        return item;
    }

    @Override
    public DomainEntity update(DB db, DomainID id, Updater<DomainID, DomainEntity> fn) {
        final AutoCommitter committer = new AutoCommitter();
        db = committer.init(db, this.dba);
        DomainEntity item = db.find(id, DomainEntity.class);
        fn.onUpdate(id, item);
        item = db.update(id, item);
        committer.finish();
        return item;
    }

    @Override
    public DomainEntity delete(DB db, DomainID id) {
        final AutoCommitter committer = new AutoCommitter();
        db = committer.init(db, this.dba);
        DomainEntity ent = find(db, id);
        db.delete(id, DomainEntity.class);
        committer.finish();
        return ent;
    }

    @Override
    public DomainEntity find(DB db, DomainID id) {
        db = dba.db(db);
        return db.find(id, DomainEntity.class);
    }

    @Override
    public List<DomainEntity> list(DB db, Filter<DomainID, DomainEntity> filter) {
        db = dba.db(db);
        DB.Query<DomainEntity> q = new DB.Query<>(DomainEntity.class);
        if (filter != null) {
            q.filter = (item) -> {
                return filter.accept(item.getId(), item);
            };
        }
        db.query(q);
        return q.results;
    }
}
