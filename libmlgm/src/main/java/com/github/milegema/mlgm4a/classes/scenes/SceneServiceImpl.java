package com.github.milegema.mlgm4a.classes.scenes;

import android.content.Context;

import com.github.milegema.mlgm4a.classes.services.ServiceTemplate;
import com.github.milegema.mlgm4a.data.entities.SceneEntity;
import com.github.milegema.mlgm4a.data.ids.SceneID;

import java.util.Collections;
import java.util.List;

public class SceneServiceImpl implements SceneService {

    private SceneDao dao;

    public SceneServiceImpl() {
    }

    public SceneDao getDao() {
        return dao;
    }

    public void setDao(SceneDao dao) {
        this.dao = dao;
    }

    @Override
    public SceneEntity insert(Context ctx, SceneEntity item) {
        return this.dao.insert(null, item);
    }

    @Override
    public SceneEntity update(Context ctx, SceneID id, Updater<SceneID, SceneEntity> fn) {
        return this.dao.update(null, id, fn::onUpdate);
    }

    @Override
    public SceneEntity delete(Context ctx, SceneID id) {
        return this.dao.delete(null, id);
    }

    @Override
    public SceneEntity find(Context ctx, SceneID id) {
        return this.dao.find(null, id);
    }

    @Override
    public List<SceneEntity> list(Context ctx, Filter<SceneID, SceneEntity> filter) {
        if (filter == null) {
            filter = (p1, p2) -> true;
        }
        return this.dao.list(null, filter::accept);
    }
}
