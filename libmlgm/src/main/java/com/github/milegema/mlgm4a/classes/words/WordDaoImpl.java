package com.github.milegema.mlgm4a.classes.words;

import com.github.milegema.mlgm4a.data.databases.AutoCommitter;
import com.github.milegema.mlgm4a.data.databases.DB;
import com.github.milegema.mlgm4a.data.databases.UserDBA;
import com.github.milegema.mlgm4a.data.entities.WordEntity;
import com.github.milegema.mlgm4a.data.ids.WordID;

import java.util.List;

public class WordDaoImpl implements WordDao {

    private UserDBA agent;

    public WordDaoImpl() {
    }

    public UserDBA getAgent() {
        return agent;
    }

    public void setAgent(UserDBA agent) {
        this.agent = agent;
    }


    @Override
    public WordEntity insert(DB db, WordEntity item) {
        final AutoCommitter committer = new AutoCommitter();
        db = committer.init(db, this.agent);
        item = db.create(item);
        committer.finish();
        return item;
    }

    @Override
    public WordEntity update(DB db, WordID id, Updater<WordID, WordEntity> fn) {
        final AutoCommitter committer = new AutoCommitter();
        db = committer.init(db, this.agent);
        WordEntity item = db.find(id, WordEntity.class);
        fn.onUpdate(id, item);
        item = db.update(id, item);
        committer.finish();
        return item;
    }

    @Override
    public WordEntity delete(DB db, WordID id) {
        final AutoCommitter committer = new AutoCommitter();
        db = committer.init(db, this.agent);
        WordEntity ent = find(db, id);
        db.delete(id, WordEntity.class);
        committer.finish();
        return ent;
    }

    @Override
    public WordEntity find(DB db, WordID id) {
        db = agent.db(db);
        return db.find(id, WordEntity.class);
    }

    @Override
    public List<WordEntity> list(DB db, Filter<WordID, WordEntity> filter) {
        db = agent.db(db);
        DB.Query<WordEntity> q = new DB.Query<>(WordEntity.class);
        if (filter != null) {
            q.filter = (item) -> {
                return filter.accept(item.getId(), item);
            };
        }
        db.query(q);
        return q.results;
    }
}
