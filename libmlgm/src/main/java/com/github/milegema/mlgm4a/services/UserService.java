package com.github.milegema.mlgm4a.services;

import com.github.milegema.mlgm4a.data.entities.UserEntity;
import com.github.milegema.mlgm4a.data.ids.UserID;

public interface UserService {

    UserEntity find(UserID id);

}
