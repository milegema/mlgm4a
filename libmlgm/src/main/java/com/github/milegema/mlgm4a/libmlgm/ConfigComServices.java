package com.github.milegema.mlgm4a.libmlgm;

import com.github.milegema.mlgm4a.classes.accounts.AccountDao;
import com.github.milegema.mlgm4a.classes.accounts.AccountService;
import com.github.milegema.mlgm4a.classes.accounts.AccountServiceImpl;
import com.github.milegema.mlgm4a.classes.domains.DomainDao;
import com.github.milegema.mlgm4a.classes.domains.DomainService;
import com.github.milegema.mlgm4a.classes.domains.DomainServiceImpl;
import com.github.milegema.mlgm4a.classes.scenes.SceneDao;
import com.github.milegema.mlgm4a.classes.scenes.SceneService;
import com.github.milegema.mlgm4a.classes.scenes.SceneServiceImpl;
import com.github.milegema.mlgm4a.classes.users.UserDao;
import com.github.milegema.mlgm4a.classes.users.UserService;
import com.github.milegema.mlgm4a.classes.users.UserServiceImpl;
import com.github.milegema.mlgm4a.classes.words.WordDao;
import com.github.milegema.mlgm4a.classes.words.WordService;
import com.github.milegema.mlgm4a.classes.words.WordServiceImpl;
import com.github.milegema.mlgm4a.components.ComponentHolderBuilder;
import com.github.milegema.mlgm4a.components.ComponentManager;
import com.github.milegema.mlgm4a.components.ComponentProviderT;
import com.github.milegema.mlgm4a.components.ComponentSetBuilder;
import com.github.milegema.mlgm4a.configurations.Configuration;
import com.github.milegema.mlgm4a.contexts.ContextAgent;
import com.github.milegema.mlgm4a.data.repositories.RepositoryManager;
import com.github.milegema.mlgm4a.security.KeyPairManager;
import com.github.milegema.mlgm4a.classes.authx.AuthService;
import com.github.milegema.mlgm4a.classes.authx.AuthServiceImpl;
import com.github.milegema.mlgm4a.classes.pins.PinService;
import com.github.milegema.mlgm4a.classes.pins.PinServiceImpl;

final class ConfigComServices {

    public static void config_all(Configuration configuration) {
        final ComponentSetBuilder csb = configuration.getComponentSetBuilder();
        config_auth_service(csb);
        config_pin_service(csb);

        config_user_service(csb);
        config_domain_service(csb);
        config_account_service(csb);
        config_scene_service(csb);
        config_word_service(csb);
    }


    private static void config_domain_service(ComponentSetBuilder csb) {
        ComponentProviderT<DomainServiceImpl> provider = new ComponentProviderT<>();
        provider.setFactory(DomainServiceImpl::new);
        provider.setWirer((ac, holder, inst) -> {

            DomainDao dao = ac.components().find(DomainDao.class);
            inst.setDao(dao);
        });
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addAlias(DomainService.class);
    }

    private static void config_pin_service(ComponentSetBuilder csb) {
        ComponentProviderT<PinServiceImpl> provider = new ComponentProviderT<>();
        provider.setFactory(PinServiceImpl::new);
        provider.setWirer((ac, holder, inst) -> {
        });
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addAlias(PinService.class);
    }


    private static void config_auth_service(ComponentSetBuilder csb) {
        ComponentProviderT<AuthServiceImpl> provider = new ComponentProviderT<>();
        provider.setFactory(AuthServiceImpl::new);
        provider.setWirer((ac, holder, inst) -> {

            ComponentManager com_man = ac.components();

            ContextAgent ctx_agent = com_man.find(ContextAgent.class);
            KeyPairManager kp_man = com_man.find(KeyPairManager.class);
            RepositoryManager repo_man = com_man.find(RepositoryManager.class);
            UserService user_ser = com_man.find(UserService.class);

            inst.setContextAgent(ctx_agent);
            inst.setKeyPairManager(kp_man);
            inst.setRepositoryManager(repo_man);
            inst.setUserService(user_ser);
        });
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addAlias(AuthService.class);
    }

    private static void config_user_service(ComponentSetBuilder csb) {
        ComponentProviderT<UserServiceImpl> provider = new ComponentProviderT<>();
        provider.setFactory(UserServiceImpl::new);
        provider.setWirer((ac, holder, inst) -> {

            UserDao dao = ac.components().find(UserDao.class);
            inst.setDao(dao);
        });
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addAlias(UserService.class);

    }


    private static void config_account_service(ComponentSetBuilder csb) {
        ComponentProviderT<AccountServiceImpl> provider = new ComponentProviderT<>();
        provider.setFactory(AccountServiceImpl::new);
        provider.setWirer((ac, holder, inst) -> {

            AccountDao dao = ac.components().find(AccountDao.class);
            inst.setDao(dao);
        });
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addAlias(AccountService.class);
    }

    private static void config_scene_service(ComponentSetBuilder csb) {
        ComponentProviderT<SceneServiceImpl> provider = new ComponentProviderT<>();
        provider.setFactory(SceneServiceImpl::new);
        provider.setWirer((ac, holder, inst) -> {

            SceneDao dao = ac.components().find(SceneDao.class);
            inst.setDao(dao);
        });
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addAlias(SceneService.class);
    }

    private static void config_word_service(ComponentSetBuilder csb) {
        ComponentProviderT<WordServiceImpl> provider = new ComponentProviderT<>();
        provider.setFactory(WordServiceImpl::new);
        provider.setWirer((ac, holder, inst) -> {

            WordDao dao = ac.components().find(WordDao.class);
            inst.setDao(dao);
        });
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addAlias(WordService.class);
    }

}
