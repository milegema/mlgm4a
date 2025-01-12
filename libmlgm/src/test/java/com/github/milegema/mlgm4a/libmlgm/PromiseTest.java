package com.github.milegema.mlgm4a.libmlgm;

import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.tasks.Promise;
import com.github.milegema.mlgm4a.tasks.PromiseContext;
import com.github.milegema.mlgm4a.tasks.Result;
import com.github.milegema.mlgm4a.utils.Time;

import org.junit.Assert;
import org.junit.Test;

public class PromiseTest {
    private boolean done;

    @Test
    public void usePromise() {

        PromiseContext pc = new PromiseContext();

        Promise.init(pc, PromiseTest.class).Try(() -> {
            Logs.debug(".try");
            Time.sleep(5000);
            return new Result<>(PromiseTest.this);
        }).Then((res) -> {
            Logs.debug(".then");
            throw new RuntimeException("usePromise.error");
            // return res;
        }).Catch((res) -> {
            Logs.debug(".catch");
            Throwable err = res.getError();
            if (err != null) {
                Logs.error("promise_catch_error: ", err);
            }
            return res;
        }).Finally((res) -> {
            Logs.debug(".finally");
            PromiseTest.this.done = true;
            return res;
        }).start();


        final int step = 1000;
        for (int ttl = 15 * 1000; ttl > 0; ttl -= step) {
            if (this.done) {
                return;
            }
            Time.sleep(step);
        }
        throw new RuntimeException("timeout");

        //  Assert.assertEquals(4, 2 + 2);
    }
}
