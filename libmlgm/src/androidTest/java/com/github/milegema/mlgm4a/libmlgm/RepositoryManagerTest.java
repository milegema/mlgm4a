package com.github.milegema.mlgm4a.libmlgm;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.data.repositories.AndroidRepositoryManager;
import com.github.milegema.mlgm4a.data.repositories.Repository;
import com.github.milegema.mlgm4a.data.repositories.RepositoryHolder;
import com.github.milegema.mlgm4a.data.repositories.RepositoryManager;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockID;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockType;
import com.github.milegema.mlgm4a.data.repositories.objects.EncodedObject;
import com.github.milegema.mlgm4a.data.repositories.objects.ObjectBuilder;
import com.github.milegema.mlgm4a.data.repositories.objects.ObjectHolder;
import com.github.milegema.mlgm4a.data.repositories.objects.ObjectMeta;
import com.github.milegema.mlgm4a.data.repositories.objects.Objects;
import com.github.milegema.mlgm4a.data.repositories.refs.Ref;
import com.github.milegema.mlgm4a.data.repositories.refs.RefName;
import com.github.milegema.mlgm4a.data.repositories.refs.Refs;
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


@RunWith(AndroidJUnit4.class)
public class RepositoryManagerTest {

    private BlockID foobar_block_id;

    @Test
    public void useRepositoryManager() throws IOException {

        Context app_context = ApplicationProvider.getApplicationContext();
        RepositoryManager repo_manager = new AndroidRepositoryManager(app_context);
        KeyPairManager key_pair_manager = KeyPairManager.Agent.getKeyPairManager();

        // prepare key-pair
        KeyPairHolder key_pair_holder = key_pair_manager.get(new KeyPairAlias("test"));
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
}
