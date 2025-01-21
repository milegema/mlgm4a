package com.github.milegema.mlgm4a.libmlgm;

import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.utils.Time;

import org.junit.Test;

public class TimeTest {
    @Test
    public void addition_isCorrect() {

        Time t = Time.now();
        Logs.info("Time.now() = " + t);

        //      Assert.assertEquals(4, 2 + 2);

    }
}
