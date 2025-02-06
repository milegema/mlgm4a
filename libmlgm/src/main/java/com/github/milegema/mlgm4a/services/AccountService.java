package com.github.milegema.mlgm4a.services;

import com.github.milegema.mlgm4a.data.entities.AccountEntity;
import com.github.milegema.mlgm4a.data.entities.UserEntity;
import com.github.milegema.mlgm4a.data.ids.AccountID;
import com.github.milegema.mlgm4a.data.ids.UserID;

public interface AccountService {

    AccountEntity find(AccountID id);

}
