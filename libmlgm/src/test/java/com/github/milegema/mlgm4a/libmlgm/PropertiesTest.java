package com.github.milegema.mlgm4a.libmlgm;

import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.data.properties.PropertyTableLS;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PropertiesTest {
    @Test
    public void useProperties() {

        PropertyTable pt = PropertyTable.Factory.create();
        pt.put("foo", "bar");
        pt.put("a", "1");
        pt.put("b", "2");
        pt.put("c", "3");
        pt.put("x", "8");
        pt.put("y", "9");


        String text = PropertyTableLS.stringify(pt);
        PropertyTable pt2 = PropertyTableLS.parse(text);

        String[] name_list = pt.names();
        for (String name : name_list) {
            String v1 = pt.get(name);
            String v2 = pt2.get(name);
            Assert.assertEquals(v1, v2);
        }
    }
}
