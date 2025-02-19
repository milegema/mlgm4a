package com.github.milegema.mlgm4a.libmlgm;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.milegema.mlgm4a.boot.Bootstrap;
import com.github.milegema.mlgm4a.classes.accounts.AccountDao;
import com.github.milegema.mlgm4a.classes.domains.DomainDao;
import com.github.milegema.mlgm4a.classes.scenes.SceneDao;
import com.github.milegema.mlgm4a.classes.users.UserDao;
import com.github.milegema.mlgm4a.classes.words.WordDao;
import com.github.milegema.mlgm4a.components.ComponentManager;
import com.github.milegema.mlgm4a.contexts.ApplicationContext;
import com.github.milegema.mlgm4a.contexts.ContextHolder;
import com.github.milegema.mlgm4a.data.databases.DB;
import com.github.milegema.mlgm4a.data.databases.DatabaseAgent;
import com.github.milegema.mlgm4a.data.databases.UserDBA;
import com.github.milegema.mlgm4a.data.entities.AccountEntity;
import com.github.milegema.mlgm4a.data.entities.DomainEntity;
import com.github.milegema.mlgm4a.data.entities.SceneEntity;
import com.github.milegema.mlgm4a.data.entities.UserEntity;
import com.github.milegema.mlgm4a.data.entities.WordEntity;
import com.github.milegema.mlgm4a.logs.Logs;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    @Test
    public void useDB() {
        MyTestContext tc = new MyTestContext();
        prepare(tc);
        tryWrite(tc);
    }

    private static class MyTestContext {

        ApplicationContext ac;
        DatabaseAgent dba;

        UserDao users;
        DomainDao domains;
        AccountDao accounts;
        SceneDao scenes;
        WordDao words;

        MyTestContext() {
        }
    }

    private void prepare(MyTestContext tc) {

        Context context = ApplicationProvider.getApplicationContext();
        ApplicationContext ac = Bootstrap.boot(context);
        ComponentManager com_man = ac.components();
        ContextHolder ch = ContextHolder.getInstance(context);

        //  RepositoryManager repo_man = com_man.find(RepositoryManager.class);
        // Logs.debug(repo_man + "");

        tc.ac = ac;
        tc.dba = com_man.find(UserDBA.class);

        tc.users = com_man.find(UserDao.class);
        tc.domains = com_man.find(DomainDao.class);
        tc.accounts = com_man.find(AccountDao.class);
        tc.scenes = com_man.find(SceneDao.class);
        tc.words = com_man.find(WordDao.class);
    }

    private void tryWrite(MyTestContext tc) {

        UserEntity user = new UserEntity();
        DomainEntity domain = new DomainEntity();
        AccountEntity account = new AccountEntity();
        SceneEntity scene = new SceneEntity();
        WordEntity word = new WordEntity();


        DB db = tc.dba.db(null);

        // init objects

        account.setUsername("user1");
        account.setDomain("a.b.c");

        word.setCharset("abc123");
        word.setLength(23);

        // insert objects

        user = tc.users.insert(db, user);
        domain = tc.domains.insert(db, domain);
        account = tc.accounts.insert(db, account);
        scene = tc.scenes.insert(db, scene);
        word = tc.words.insert(db, word);

        account = tc.accounts.insert(db, account); // try re-insert

        Logs.debug("" + user);
        Logs.debug("" + domain);
        Logs.debug("" + account);
        Logs.debug("" + scene);
        Logs.debug("" + word);
    }
}
