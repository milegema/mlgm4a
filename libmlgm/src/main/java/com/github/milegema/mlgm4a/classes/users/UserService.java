package com.github.milegema.mlgm4a.classes.users;

import android.content.Context;

import com.github.milegema.mlgm4a.classes.services.ServiceTemplate;
import com.github.milegema.mlgm4a.data.entities.UserEntity;
import com.github.milegema.mlgm4a.data.ids.UserID;

import java.util.Optional;

public interface UserService extends ServiceTemplate<UserID, UserEntity> {

    /**
     * 根据 'signed_at' 字段, 查找当前用户
     */
    Optional<UserEntity> findCurrentUser(Context ctx);

}
