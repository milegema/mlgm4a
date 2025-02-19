package com.github.milegema.mlgm4a.classes.users;

import com.github.milegema.mlgm4a.classes.services.DaoTemplate;
import com.github.milegema.mlgm4a.data.entities.UserEntity;
import com.github.milegema.mlgm4a.data.ids.UserID;

public interface UserDao extends DaoTemplate<UserID, UserEntity> {
}
