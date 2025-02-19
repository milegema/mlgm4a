package com.github.milegema.mlgm4a.classes.users;

import com.github.milegema.mlgm4a.data.databases.AutoCommitter;
import com.github.milegema.mlgm4a.data.databases.RootDBA;
import com.github.milegema.mlgm4a.data.entities.UserEntity;
import com.github.milegema.mlgm4a.data.ids.UserID;
import com.github.milegema.mlgm4a.data.databases.DB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private RootDBA dba;

    public UserDaoImpl() {
    }

    public RootDBA getDba() {
        return dba;
    }

    public void setDba(RootDBA dba) {
        this.dba = dba;
    }

    @Override
    public UserEntity insert(DB db, UserEntity item) {
        final AutoCommitter committer = new AutoCommitter();
        db = committer.init(db, this.dba);
        item = db.create(item);
        committer.finish();
        return item;
    }

    @Override
    public UserEntity update(DB db, UserID id, Updater<UserID, UserEntity> fn) {
        final AutoCommitter committer = new AutoCommitter();
        db = committer.init(db, this.dba);
        UserEntity item = db.find(id, UserEntity.class);
        fn.onUpdate(id, item);
        item = db.update(id, item);
        committer.finish();
        return item;
    }

    @Override
    public UserEntity delete(DB db, UserID id) {
        final AutoCommitter committer = new AutoCommitter();
        db = committer.init(db, this.dba);
        UserEntity item = db.find(id, UserEntity.class);
        db.delete(id, UserEntity.class);
        committer.finish();
        return item;
    }

    @Override
    public UserEntity find(DB db, UserID id) {
        db = this.dba.db(db);
        return db.find(id, UserEntity.class);
    }

    @Override
    public List<UserEntity> list(DB db, Filter<UserID, UserEntity> filter) {
        db = this.dba.db(db);
        DB.Query<UserEntity> q = new DB.Query<>(UserEntity.class);
        q.results = new ArrayList<>();
        if (filter != null) {
            q.filter = (item) -> filter.accept(item.getId(), item);
        }
        db.query(q);
        return q.results;
    }
}
