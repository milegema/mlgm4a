package com.github.milegema.mlgm4a.data.repositories.tables;

import com.github.milegema.mlgm4a.data.ids.EntityID;
import com.github.milegema.mlgm4a.data.ids.LongID;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;

public class DefaultIdentityGenerator implements IdentityGenerator {


    static class IdentityChecker {

        TableName table_name;
        FieldName field_name;
        PropertyTable pt;

        public IdentityChecker(EntityContext ec) {
            this.pt = ec.getProperties();
            this.table_name = ec.getTable().name();
            this.field_name = ec.getTable().primaryKey().name();
        }

        public boolean hasID(long id) {
            String name = "" + table_name + '.' + id + '.' + field_name;
            String value = this.pt.get(name);
            return (value != null);
        }
    }


    @Override
    public EntityID generateID(EntityContext ec) {

        IdentityChecker checker = new IdentityChecker(ec);
        final long id_max = Long.MAX_VALUE / 8;
        long id, id1, id2;
        id1 = id2 = 0;

        for (id = 1; id < id_max; id *= 2) {
            if (checker.hasID(id)) {
                continue;
            }
            id1 = id / 2;
            id2 = id;
            break;
        }

        if (id1 < 1 || id2 < 1) {
            return new LongID(1);
        }

        for (id = id1; id <= id2; id++) {
            if (checker.hasID(id)) {
                continue;
            }
            return new LongID(id);
        }

        String type_name = ec.getTable().getEntityClass().getName();
        throw new TableException("cannot generate id for entity-class: " + type_name);
    }
}
