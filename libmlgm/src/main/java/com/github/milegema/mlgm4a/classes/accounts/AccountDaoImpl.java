package com.github.milegema.mlgm4a.classes.accounts;

import com.github.milegema.mlgm4a.data.databases.AutoCommitter;
import com.github.milegema.mlgm4a.data.databases.DB;
import com.github.milegema.mlgm4a.data.databases.UserDBA;
import com.github.milegema.mlgm4a.data.entities.AccountEntity;
import com.github.milegema.mlgm4a.data.ids.AccountID;

import java.util.List;

public class AccountDaoImpl implements AccountDao {

    private UserDBA agent;

    public AccountDaoImpl() {
    }

    public UserDBA getAgent() {
        return agent;
    }

    public void setAgent(UserDBA agent) {
        this.agent = agent;
    }

    @Override
    public AccountEntity insert(DB db, AccountEntity item) {
        final AutoCommitter committer = new AutoCommitter();
        db = committer.init(db, this.agent);
        item = db.create(item);
        committer.finish();
        return item;
    }

    @Override
    public AccountEntity update(DB db, AccountID id, Updater<AccountID, AccountEntity> fn) {
        final AutoCommitter committer = new AutoCommitter();
        db = committer.init(db, this.agent);
        AccountEntity item = db.find(id, AccountEntity.class);
        fn.onUpdate(id, item);
        item = db.update(id, item);
        committer.finish();
        return item;
    }

    @Override
    public AccountEntity delete(DB db, AccountID id) {
        final AutoCommitter committer = new AutoCommitter();
        db = committer.init(db, this.agent);
        AccountEntity ent = find(db, id);
        db.delete(id, AccountEntity.class);
        committer.finish();
        return ent;
    }

    @Override
    public AccountEntity find(DB db, AccountID id) {
        db = agent.db(db);
        return db.find(id, AccountEntity.class);
    }

    @Override
    public List<AccountEntity> list(DB db, Filter<AccountID, AccountEntity> filter) {
        db = agent.db(db);
        DB.Query<AccountEntity> q = new DB.Query<>(AccountEntity.class);
        if (filter != null) {
            q.filter = (item) -> {
                return filter.accept(item.getId(), item);
            };
        }
        db.query(q);
        return q.results;
    }
}
