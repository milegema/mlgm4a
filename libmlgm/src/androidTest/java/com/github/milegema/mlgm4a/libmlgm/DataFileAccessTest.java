package com.github.milegema.mlgm4a.libmlgm;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.milegema.mlgm4a.data.files.FileAccessAction;
import com.github.milegema.mlgm4a.data.files.FileAccessContext;
import com.github.milegema.mlgm4a.data.files.FileAccessRequest;
import com.github.milegema.mlgm4a.data.files.FileAccessResponse;
import com.github.milegema.mlgm4a.data.files.RepositoryFileContext;
import com.github.milegema.mlgm4a.logs.Logs;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Instrumented test, which will execute on an Android device.
 * <p>
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4.class)
public class DataFileAccessTest {

    @Test
    public void useDataFile() throws IOException {

        // Context of the app under test.
        //   Assert.assertEquals("com.github.milegema.mlgm4a", appContext.getPackageName());
        Context appContext = ApplicationProvider.getApplicationContext();

        RepositoryFileContext file_repo_ctx = AndroidTestComFactory.createFileRepositoryContext(appContext);
        Path file = file_repo_ctx.getFolder().resolve("tmp/test/file1");

        FileAccessContext file_acc_ctx = new FileAccessContext();
        file_acc_ctx.setSecretKey(null); // auto
        file_acc_ctx.setKeyPair(null);
        file_acc_ctx.setFile(file);
        file_acc_ctx.setChain(file_repo_ctx.getChain());

        // create
        FileAccessRequest req = new FileAccessRequest();
        req.setAction(FileAccessAction.CREATE);
        req.setContext(file_acc_ctx);
        req.setBlocks(new ArrayList<>());
        FileAccessResponse resp = file_repo_ctx.getChain().access(req);
        this.outputLog(resp);


        // append
        req = new FileAccessRequest();
        req.setAction(FileAccessAction.APPEND);
        req.setContext(file_acc_ctx);
        req.setBlocks(new ArrayList<>());
        resp = file_repo_ctx.getChain().access(req);
        this.outputLog(resp);

        // fetch
        req = new FileAccessRequest();
        req.setAction(FileAccessAction.READ_ALL);
        req.setContext(file_acc_ctx);
        req.setBlocks(new ArrayList<>());
        resp = file_repo_ctx.getChain().access(req);
        this.outputLog(resp);
    }

    private void outputLog(FileAccessResponse resp) {
        Logs.info("FileAccessResponse: " + resp);
    }
}
