package com.github.milegema.mlgm4a.libmlgm;

import com.github.milegema.mlgm4a.data.ids.UUID;
import com.github.milegema.mlgm4a.logs.Logs;

import org.junit.Assert;
import org.junit.Test;

public class UuidTest {
    @Test
    public void useUUID() {

        UUID u0 = UUID.zero();
        UUID u1 = UUID.generate();
        String str1 = u1.toString();
        UUID u2 = new UUID(str1);

        Logs.info("uuid_0 = " + u0);
        Logs.info("uuid_1 = " + u1);
        Logs.info("uuid_2 = " + u2);

        Assert.assertEquals(u1, u2);
    }
}
