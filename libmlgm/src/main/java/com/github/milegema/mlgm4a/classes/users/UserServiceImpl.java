package com.github.milegema.mlgm4a.classes.users;

import android.content.Context;

import com.github.milegema.mlgm4a.data.entities.UserEntity;
import com.github.milegema.mlgm4a.data.ids.UserID;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private UserDao dao;

    public UserServiceImpl() {
    }

    public UserDao getDao() {
        return dao;
    }

    public void setDao(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public UserEntity insert(Context ctx, UserEntity item) {
        return dao.insert(null, item);
    }

    @Override
    public UserEntity update(Context ctx, UserID id, Updater<UserID, UserEntity> fn) {
        return dao.update(null, id, fn::onUpdate);
    }

    @Override
    public UserEntity delete(Context ctx, UserID id) {
        return dao.delete(null, id);
    }

    @Override
    public UserEntity find(Context ctx, UserID id) {
        return dao.find(null, id);
    }

    @Override
    public List<UserEntity> list(Context ctx, Filter<UserID, UserEntity> filter) {
        if (filter == null) {
            filter = (p1, p2) -> true;
        }
        return dao.list(null, filter::accept);
    }

    @Override
    public Optional<UserEntity> findCurrentUser(Context ctx) {
        UserEntity result = null;
        List<UserEntity> all = this.list(ctx, null);
        for (UserEntity item : all) {
            if (result == null) {
                result = item;
            }
            if (result == null || item == null) {
                continue;
            }
            if (result.getSignedAt() < item.getSignedAt()) {
                result = item;
            }
        }
        if (result == null) {
            return Optional.empty();
        }
        if (result.getSignedAt() < 1) {
            return Optional.empty();
        }
        return Optional.of(result);
    }
}
