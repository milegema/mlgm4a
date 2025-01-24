package com.github.milegema.mlgm4a.libmlgm;

import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.data.properties.PropertyTableLS;
import com.github.milegema.mlgm4a.logs.Logs;

import org.junit.Assert;
import org.junit.Test;

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

    @Test
    public void usePropertiesParser() {

        final String nl = "\n";
        final StringBuilder text = new StringBuilder();
        long now = System.currentTimeMillis();

        text.append("# this is a comment" + nl);
        text.append("a=1" + nl);
        text.append("b=2" + nl);
        text.append("[segment '1']" + nl);
        text.append("e=5" + nl);
        text.append("f=6" + nl);
        text.append("[segment '2']" + nl);
        text.append("g=7" + nl);
        text.append("h=8" + nl);
        if ((now & 1) == 0) {
            text.append("[segment]" + nl);
            text.append("c=3" + nl);
            text.append("d=4" + nl);
        }
        text.append("[document]" + nl);
        text.append("eof=1" + nl);


        PropertyTable pt = PropertyTableLS.parse(text.toString());
        String[] name_list = pt.names();

        for (String name : name_list) {
            String v1 = pt.get(name);
            Logs.info(name + " = " + v1);
        }
    }
}
