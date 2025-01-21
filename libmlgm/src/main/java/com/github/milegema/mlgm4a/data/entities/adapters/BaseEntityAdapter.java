package com.github.milegema.mlgm4a.data.entities.adapters;

import com.github.milegema.mlgm4a.data.entities.BaseEntity;
import com.github.milegema.mlgm4a.data.ids.EntityID;
import com.github.milegema.mlgm4a.data.ids.GroupID;
import com.github.milegema.mlgm4a.data.ids.LongID;
import com.github.milegema.mlgm4a.data.ids.UUID;
import com.github.milegema.mlgm4a.data.ids.UserID;
import com.github.milegema.mlgm4a.data.properties.PropertyGetter;
import com.github.milegema.mlgm4a.data.properties.PropertySetter;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.data.repositories.tables.EntityAdapter;
import com.github.milegema.mlgm4a.data.repositories.tables.EntityContext;
import com.github.milegema.mlgm4a.data.repositories.tables.PropertyNamePrefix;
import com.github.milegema.mlgm4a.data.repositories.tables.TableException;
import com.github.milegema.mlgm4a.utils.Time;

public class BaseEntityAdapter implements EntityAdapter {

    public static boolean checkRequiredFields(String[] field_name_list, PropertyTable pt, boolean throwable) {
        TableException err = null;
        for (String name : field_name_list) {
            String value = pt.get(name);
            if (value == null) {
                err = new TableException("no required field: " + name);
                break;
            }
        }
        if (err != null) {
            if (throwable) {
                throw err;
            } else {
                return false;
            }
        }
        return true;
    }


    @Override
    public void convert(EntityContext ec) {

        final BaseEntity entity = (BaseEntity) ec.getEntity();
        final PropertyNamePrefix prefix = new PropertyNamePrefix(ec);

        final String name_uuid = prefix.fullName("uuid");
        final String name_group = prefix.fullName("group");
        final String name_committer = prefix.fullName("committer");
        final String name_creator = prefix.fullName("creator");
        final String name_owner = prefix.fullName("owner");
        final String name_created_at = prefix.fullName("created_at");
        final String name_updated_at = prefix.fullName("updated_at");
        final String name_deleted_at = prefix.fullName("deleted_at");

        if (ec.isEntityToProperties()) {
            // entity to props
            PropertySetter setter = ec.getSetter();

            setter.put(name_committer, entity.getCommitter());
            setter.put(name_creator, entity.getCreator());
            setter.put(name_owner, entity.getOwner());

            setter.put(name_created_at, entity.getCreatedAt());
            setter.put(name_updated_at, entity.getUpdatedAt());
            setter.put(name_deleted_at, entity.getDeletedAt());

            setter.put(name_group, entity.getGroup());
            setter.put(name_uuid, entity.getUuid());

        } else {
            // props to entity
            PropertyGetter getter = ec.getGetter();

            // required fields
            getter.setRequired(true);
            Time created_at = getter.getTime(name_created_at, null);
            Time updated_at = getter.getTime(name_updated_at, null);

            // optional fields
            getter.setRequired(false);
            long committer = getter.getLong(name_committer, 0);
            long creator = getter.getLong(name_creator, 0);
            long owner = getter.getLong(name_owner, 0);
            long group = getter.getLong(name_group, 0);
            UUID uuid = getter.getUUID(name_uuid, null);
            Time deleted_at = getter.getTime(name_deleted_at, null);

            // hold

            entity.setCommitter(new UserID(committer));
            entity.setCreator(new UserID(creator));
            entity.setOwner(new UserID(owner));

            entity.setCreatedAt(created_at);
            entity.setUpdatedAt(updated_at);
            entity.setDeletedAt(deleted_at);

            entity.setGroup(new GroupID(group));
            entity.setUuid(uuid);
        }
    }

    public static long getLongEntityID(EntityContext ec) {
        if (ec == null) {
            return 0;
        }
        EntityID id = ec.getId();
        if (id instanceof LongID) {
            LongID lid = (LongID) id;
            return lid.number();
        }
        return 0;
    }

}
