package com.github.milegema.mlgm4a.classes.scenes;

import com.github.milegema.mlgm4a.data.databases.AutoCommitter;
import com.github.milegema.mlgm4a.data.databases.DB;
import com.github.milegema.mlgm4a.data.databases.UserDBA;
import com.github.milegema.mlgm4a.data.entities.SceneEntity;
import com.github.milegema.mlgm4a.data.ids.SceneID;

import java.util.List;

public class SceneDaoImpl implements SceneDao {

    private UserDBA agent;

    public SceneDaoImpl() {
    }

    public UserDBA getAgent() {
        return agent;
    }

    public void setAgent(UserDBA agent) {
        this.agent = agent;
    }

    @Override
    public SceneEntity insert(DB db, SceneEntity item) {
        final AutoCommitter committer = new AutoCommitter();
        db = committer.init(db, this.agent);
        item = db.create(item);
        committer.finish();
        return item;
    }

    @Override
    public SceneEntity update(DB db, SceneID id, Updater<SceneID, SceneEntity> fn) {
        final AutoCommitter committer = new AutoCommitter();
        db = committer.init(db, this.agent);
        SceneEntity item = db.find(id, SceneEntity.class);
        fn.onUpdate(id, item);
        item = db.update(id, item);
        committer.finish();
        return item;
    }

    @Override
    public SceneEntity delete(DB db, SceneID id) {
        final AutoCommitter committer = new AutoCommitter();
        db = committer.init(db, this.agent);
        SceneEntity ent = find(db, id);
        db.delete(id, SceneEntity.class);
        committer.finish();
        return ent;
    }

    @Override
    public SceneEntity find(DB db, SceneID id) {
        db = agent.db(db);
        return db.find(id, SceneEntity.class);
    }

    @Override
    public List<SceneEntity> list(DB db, Filter<SceneID, SceneEntity> filter) {
        db = agent.db(db);
        DB.Query<SceneEntity> q = new DB.Query<>(SceneEntity.class);
        if (filter != null) {
            q.filter = (item) -> {
                return filter.accept(item.getId(), item);
            };
        }
        db.query(q);
        return q.results;
    }
}
