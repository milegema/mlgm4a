package com.github.milegema.mlgm4a.data.repositories.tables;

import com.github.milegema.mlgm4a.data.ids.EntityID;

public class PropertyNamePrefix {

    private final TableName table_name;
    private final EntityID entity_id;

    public PropertyNamePrefix(EntityContext ec) {
        this.table_name = ec.getTable().name();
        this.entity_id = ec.getId();
    }

    public String fullName(String field) {
        return table_name + "." + entity_id + "." + field;
    }

}
