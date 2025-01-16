package com.github.milegema.mlgm4a.libmlgm;

import com.github.milegema.mlgm4a.data.repositories.tables.Field;
import com.github.milegema.mlgm4a.data.repositories.tables.FieldBuilder;
import com.github.milegema.mlgm4a.data.repositories.tables.FieldType;
import com.github.milegema.mlgm4a.data.repositories.tables.Schema;
import com.github.milegema.mlgm4a.data.repositories.tables.SchemaBuilder;
import com.github.milegema.mlgm4a.data.repositories.tables.Table;
import com.github.milegema.mlgm4a.data.repositories.tables.TableBuilder;

import org.junit.Assert;
import org.junit.Test;

public class SimpleTableTest {

    @Test
    public void useTable() {

        SchemaBuilder builder1 = new SchemaBuilder();

        Table table_foo = this.create_table_foo(builder1.newTableBuilder());
        Table table_bar = this.create_table_bar(builder1.newTableBuilder());


        builder1.addTable(table_foo);
        builder1.addTable(table_bar);

        Schema schema = builder1.create();

        schema.name();
        Assert.assertEquals(4, 2 + 2);
    }

    private Table create_table_foo(TableBuilder builder) {
        builder.setName("tab_foo");

        // id:int(pk)
        FieldBuilder fb = builder.newFieldBuilder();
        fb.setType(FieldType.INT).setName("id");
        fb.setUnique(true);
        Field pk = fb.create();
        builder.addField(pk);

        // name:string(text)
        fb = builder.newFieldBuilder();
        fb.setType(FieldType.STRING).setName("name");
        fb.setUnique(true);
        builder.addField(fb.create());

        // label:string(text)
        fb = builder.newFieldBuilder();
        fb.setType(FieldType.STRING).setName("label");
        builder.addField(fb.create());

        // description:string(text)
        fb = builder.newFieldBuilder();
        fb.setType(FieldType.STRING).setName("description");
        builder.addField(fb.create());

        // done
        builder.setPrimaryKey(pk);
        return builder.create();
    }

    private Table create_table_bar(TableBuilder builder) {
        builder.setName("tab_bar");

        // id:int(pk)
        FieldBuilder fb = builder.newFieldBuilder();
        fb.setType(FieldType.INT).setName("id");
        fb.setUnique(true);
        Field pk = fb.create();
        builder.addField(pk);

        // value:double
        fb = builder.newFieldBuilder();
        fb.setType(FieldType.DOUBLE).setName("value");
        builder.addField(fb.create());

        // enabled:bool
        fb = builder.newFieldBuilder();
        fb.setType(FieldType.BOOL).setName("enabled");
        builder.addField(fb.create());

        // timestamp:timestamp
        fb = builder.newFieldBuilder();
        fb.setType(FieldType.TIMESTAMP).setName("timestamp");
        builder.addField(fb.create());

        // done
        builder.setPrimaryKey(pk);
        return builder.create();
    }
}
