package com.github.milegema.mlgm4a.components;

import com.github.milegema.mlgm4a.contexts.ApplicationContext;
import com.github.milegema.mlgm4a.logs.Logs;

import java.util.List;

public class ComLifeScanner {

    public static void scan(ApplicationContext ac, ComLifeContext clc) {

        ComponentManager src = ac.components();
        List<ComLife> dst = clc.lives;
        String[] ids = src.listIds();
        int count = 0;

        for (String id : ids) {
            Object obj = src.findById(id, Object.class);
            if (obj instanceof ComLifecycle) {
                ComLifecycle lifecycle = (ComLifecycle) obj;
                dst.add(lifecycle.life());
                count++;
            }
        }

        Logs.debug("ComLifeScanner: find ComLives, count = " + count);
    }
}
