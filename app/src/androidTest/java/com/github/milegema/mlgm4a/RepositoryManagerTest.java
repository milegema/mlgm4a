package com.github.milegema.mlgm4a;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.milegema.mlgm4a.data.repositories.AndroidRepositoryManager;
import com.github.milegema.mlgm4a.data.repositories.Repository;
import com.github.milegema.mlgm4a.data.repositories.RepositoryHolder;
import com.github.milegema.mlgm4a.data.repositories.RepositoryManager;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockID;
import com.github.milegema.mlgm4a.data.repositories.refs.Ref;
import com.github.milegema.mlgm4a.data.repositories.refs.RefName;
import com.github.milegema.mlgm4a.data.repositories.refs.Refs;
import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.security.KeyPairAlias;
import com.github.milegema.mlgm4a.security.KeyPairHolder;
import com.github.milegema.mlgm4a.security.KeyPairManager;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.security.KeyPair;


@RunWith(AndroidJUnit4.class)
public class RepositoryManagerTest {

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


        this.use_refs(repo);

        Logs.info("open repo: " + repo);
    }

    private void use_refs(Repository repo) throws IOException {
        BlockID id_1 = new BlockID("ffff000ddd");
        RefName name = new RefName("refs/test/foo/bar");
        Refs refs = repo.refs();
        Ref r1 = refs.get(name);
        r1.write(id_1);
        BlockID id_2 = r1.read();
        Assert.assertArrayEquals(id_1.toByteArray(), id_2.toByteArray());
    }
}
