package com.github.milegema.mlgm4a.libmlgm;

import com.github.milegema.mlgm4a.classes.accounts.AccountDao;
import com.github.milegema.mlgm4a.classes.accounts.AccountDaoImpl;
import com.github.milegema.mlgm4a.classes.domains.DomainDao;
import com.github.milegema.mlgm4a.classes.domains.DomainDaoImpl;
import com.github.milegema.mlgm4a.classes.scenes.SceneDao;
import com.github.milegema.mlgm4a.classes.scenes.SceneDaoImpl;
import com.github.milegema.mlgm4a.classes.users.UserDao;
import com.github.milegema.mlgm4a.classes.users.UserDaoImpl;
import com.github.milegema.mlgm4a.classes.words.WordDao;
import com.github.milegema.mlgm4a.classes.words.WordDaoImpl;
import com.github.milegema.mlgm4a.components.ComponentHolderBuilder;
import com.github.milegema.mlgm4a.components.ComponentProviderT;
import com.github.milegema.mlgm4a.components.ComponentSetBuilder;
import com.github.milegema.mlgm4a.configurations.Configuration;
import com.github.milegema.mlgm4a.data.databases.RootDBA;
import com.github.milegema.mlgm4a.data.databases.UserDBA;
import com.github.milegema.mlgm4a.data.properties.PropertyGetter;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.network.web.WebClient;

final class ConfigComDAObjects {

    public static void config_all(Configuration configuration) {
        final ComponentSetBuilder csb = configuration.getComponentSetBuilder();
        config_user_dao(csb);
        config_domain_dao(csb);
        config_account_dao(csb);
        config_scene_dao(csb);
        config_word_dao(csb);
    }

    private static void config_user_dao(ComponentSetBuilder csb) {
        ComponentProviderT<UserDaoImpl> provider = new ComponentProviderT<>();
        provider.setFactory(UserDaoImpl::new);
        provider.setWirer((ac, holder, inst) -> {

            RootDBA dba = ac.components().find(RootDBA.class);
            inst.setDba(dba);
        });
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addAlias(UserDao.class);
    }

    private static void config_domain_dao(ComponentSetBuilder csb) {
        ComponentProviderT<DomainDaoImpl> provider = new ComponentProviderT<>();
        provider.setFactory(DomainDaoImpl::new);
        provider.setWirer((ac, holder, inst) -> {

            UserDBA dba = ac.components().find(UserDBA.class);
            inst.setDba(dba);
        });
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addAlias(DomainDao.class);
    }


    private static void config_account_dao(ComponentSetBuilder csb) {
        ComponentProviderT<AccountDaoImpl> provider = new ComponentProviderT<>();
        provider.setFactory(AccountDaoImpl::new);
        provider.setWirer((ac, holder, inst) -> {

            UserDBA dba = ac.components().find(UserDBA.class);
            inst.setAgent(dba);
        });
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addAlias(AccountDao.class);
    }

    private static void config_scene_dao(ComponentSetBuilder csb) {
        ComponentProviderT<SceneDaoImpl> provider = new ComponentProviderT<>();
        provider.setFactory(SceneDaoImpl::new);
        provider.setWirer((ac, holder, inst) -> {

            UserDBA dba = ac.components().find(UserDBA.class);
            inst.setAgent(dba);
        });
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addAlias(SceneDao.class);
    }

    private static void config_word_dao(ComponentSetBuilder csb) {
        ComponentProviderT<WordDaoImpl> provider = new ComponentProviderT<>();
        provider.setFactory(WordDaoImpl::new);
        provider.setWirer((ac, holder, inst) -> {

            UserDBA dba = ac.components().find(UserDBA.class);
            inst.setAgent(dba);
        });
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addAlias(WordDao.class);
    }

}
