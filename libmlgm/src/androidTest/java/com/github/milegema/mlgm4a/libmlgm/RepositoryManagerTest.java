package com.github.milegema.mlgm4a.libmlgm;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.milegema.mlgm4a.boot.Bootstrap;
import com.github.milegema.mlgm4a.components.ComponentManager;
import com.github.milegema.mlgm4a.contexts.ApplicationContext;
import com.github.milegema.mlgm4a.data.entities.AccountEntity;
import com.github.milegema.mlgm4a.data.entities.BaseEntity;
import com.github.milegema.mlgm4a.data.ids.AccountID;
import com.github.milegema.mlgm4a.data.ids.EntityID;
import com.github.milegema.mlgm4a.data.ids.UserID;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.data.repositories.Repository;
import com.github.milegema.mlgm4a.data.repositories.RepositoryHolder;
import com.github.milegema.mlgm4a.data.repositories.RepositoryManager;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockID;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockType;
import com.github.milegema.mlgm4a.data.repositories.objects.EncodedObject;
import com.github.milegema.mlgm4a.data.repositories.objects.ObjectBuilder;
import com.github.milegema.mlgm4a.data.repositories.objects.ObjectHolder;
import com.github.milegema.mlgm4a.data.repositories.objects.Objects;
import com.github.milegema.mlgm4a.data.repositories.refs.Ref;
import com.github.milegema.mlgm4a.data.repositories.refs.RefName;
import com.github.milegema.mlgm4a.data.repositories.refs.Refs;
import com.github.milegema.mlgm4a.data.databases.DB;
import com.github.milegema.mlgm4a.data.repositories.tables.TableManager;
import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.security.KeyPairAlias;
import com.github.milegema.mlgm4a.security.KeyPairHolder;
import com.github.milegema.mlgm4a.security.KeyPairManager;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;


@RunWith(AndroidJUnit4.class)
public class RepositoryManagerTest {

    private BlockID foobar_block_id;

    @Test
    public void useRepositoryManager() throws IOException {

        Context context = ApplicationProvider.getApplicationContext();
        ApplicationContext ac = Bootstrap.boot(context);
        ComponentManager com_man = ac.components();

        RepositoryManager repo_manager = com_man.find(RepositoryManager.class);
        KeyPairManager key_pair_manager = com_man.find(KeyPairManager.class);

        // prepare key-pair
        KeyPairHolder key_pair_holder = key_pair_manager.get(KeyPairAlias.parse("test"));
        if (!key_pair_holder.exists()) {
            Logs.info("create new key-pair");
            key_pair_holder.create();
        }
        KeyPair key_pair = key_pair_holder.fetch();


        // prepare repo
        RepositoryHolder repo_holder = repo_manager.get(key_pair);
        if (!repo_holder.exists()) {
            Logs.info("create new repository");
            repo_holder.create();
        }
        Repository repo = repo_holder.open();

        // Logs.info("open repo: " + repo);

        this.try_refs(repo);
        this.try_object_create_and_fetch(repo);
        this.try_object_export_and_import(repo);
        this.try_tables(repo);
    }

    private void try_refs(Repository repo) throws IOException {
        BlockID id_1 = new BlockID("ffff000ddd");
        RefName name = new RefName("refs/test/foo/bar");
        Refs refs = repo.refs();
        Ref r1 = refs.get(name);
        r1.write(id_1);
        BlockID id_2 = r1.read();
        Assert.assertArrayEquals(id_1.toByteArray(), id_2.toByteArray());
    }

    private void try_object_create_and_fetch(Repository repo) throws IOException {

        Objects obj_manager = repo.objects();
        ObjectBuilder ob = new ObjectBuilder(obj_manager);
        PropertyTable head = PropertyTable.Factory.create();

        head.put("foo", "bar");

        ob.setBlockType(BlockType.FooBar);
        ob.setContentType("application/x-foo-bar");
        ob.setBody(new ByteSlice("hello,foo-bar"));
        ob.setHead(head);

        ObjectHolder h1 = ob.create();
        ObjectHolder h2 = obj_manager.get(h1.id());

        ByteSlice body1 = h1.body();
        ByteSlice body2 = h2.body();

        Assert.assertArrayEquals(body1.toByteArray(), body2.toByteArray());
        this.foobar_block_id = h1.id();
    }

    private void try_object_export_and_import(Repository repo) throws IOException {

        ObjectHolder h1 = repo.objects().get(this.foobar_block_id);
        EncodedObject encoded = h1.export();
        ObjectHolder h2 = repo.objects().importObject(encoded);

        ByteSlice body1 = h1.body();
        ByteSlice body2 = h2.body();
        Assert.assertArrayEquals(body1.toByteArray(), body2.toByteArray());

        encoded.verify();
    }

    private void try_tables(Repository repo) {
        TableManager tm = repo.tables();
        DB db = tm.db();
        List<EntityID> ids = new ArrayList<>();
        List<BaseEntity> entities = new ArrayList<>();

        // create
        final int total = 10;
        for (int i = 0; i < total; ++i) {
            String name = "foo-" + i;
            AccountEntity entity = new AccountEntity();
            entity.setUsername(name);
            entity.setDomain(name + ".bar.com");
            entity = db.create(entity);
            AccountID id = entity.getId();
            ids.add(id);
            Logs.info("account.id = " + id);
        }

        db.commit();
        db.clearCache();

        // update

        for (EntityID id : ids) {
            AccountEntity ent = db.find(id, AccountEntity.class);
            ent.setOwner(new UserID(666));
            db.update(id, ent);
            // Logs.info("account.entity = " + ent);
        }
        db.commit();
        db.clearCache();

        // delete
        db.delete(ids.get(0), AccountEntity.class);
        db.commit();
        db.clearCache();

        // find
        for (EntityID id : ids) {
            try {
                AccountEntity ent = db.find(id, AccountEntity.class);
                Logs.info("account.entity = " + ent);
                entities.add(ent);
            } catch (Exception e) {
                Logs.error(e.getMessage());
            }
        }

        //query

        DB.Query<AccountEntity> q = new DB.Query<>(AccountEntity.class);
        q.offset = 2;
        q.limit = 5;
        db.query(q);


        Logs.debug("done");
    }
}
