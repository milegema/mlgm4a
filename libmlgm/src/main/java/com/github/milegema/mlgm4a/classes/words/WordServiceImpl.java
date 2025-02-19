package com.github.milegema.mlgm4a.classes.words;

import android.content.Context;

import com.github.milegema.mlgm4a.classes.services.ServiceTemplate;
import com.github.milegema.mlgm4a.data.databases.UserDBA;
import com.github.milegema.mlgm4a.data.entities.WordEntity;
import com.github.milegema.mlgm4a.data.ids.WordID;

import java.util.Collections;
import java.util.List;

public class WordServiceImpl implements WordService {

    private WordDao dao;

    public WordServiceImpl() {
    }

    public WordDao getDao() {
        return dao;
    }

    public void setDao(WordDao dao) {
        this.dao = dao;
    }

    @Override
    public WordEntity insert(Context ctx, WordEntity item) {
        return this.dao.insert(null, item);
    }

    @Override
    public WordEntity update(Context ctx, WordID id, Updater<WordID, WordEntity> fn) {
        return this.dao.update(null, id, fn::onUpdate);
    }

    @Override
    public WordEntity delete(Context ctx, WordID id) {
        return this.dao.delete(null, id);
    }

    @Override
    public WordEntity find(Context ctx, WordID id) {
        return this.dao.find(null, id);
    }

    @Override
    public List<WordEntity> list(Context ctx, Filter<WordID, WordEntity> filter) {
        if (filter == null) {
            filter = (p1, p2) -> true;
        }
        return this.dao.list(null, filter::accept);
    }
}
