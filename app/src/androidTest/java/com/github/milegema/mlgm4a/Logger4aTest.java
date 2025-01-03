package com.github.milegema.mlgm4a;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.milegema.mlgm4a.logs.AndroidLogger;
import com.github.milegema.mlgm4a.logs.Logs;

import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class Logger4aTest {

    @Test
    public void useAppContext() {
        AndroidLogger.init();
        Logs.debug("hello, android-logger");
    }
}
