package com.github.milegema.mlgm4a;

import com.github.milegema.mlgm4a.logs.Logs;

import org.junit.Test;

public class Logger4jTest {

    @Test
    public void addition_isCorrect() {
        Logs.debug("hello, " + this);
    }
}
