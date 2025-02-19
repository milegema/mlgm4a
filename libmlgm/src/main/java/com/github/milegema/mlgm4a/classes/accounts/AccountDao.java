package com.github.milegema.mlgm4a.classes.accounts;

import com.github.milegema.mlgm4a.classes.services.DaoTemplate;
import com.github.milegema.mlgm4a.data.entities.AccountEntity;
import com.github.milegema.mlgm4a.data.entities.UserEntity;
import com.github.milegema.mlgm4a.data.ids.AccountID;
import com.github.milegema.mlgm4a.data.ids.UserID;

public interface AccountDao extends DaoTemplate<AccountID, AccountEntity> {

}
