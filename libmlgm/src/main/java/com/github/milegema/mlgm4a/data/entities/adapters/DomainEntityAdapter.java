package com.github.milegema.mlgm4a.data.entities.adapters;

import com.github.milegema.mlgm4a.data.entities.AccountEntity;
import com.github.milegema.mlgm4a.data.entities.DomainEntity;
import com.github.milegema.mlgm4a.data.ids.DomainID;
import com.github.milegema.mlgm4a.data.properties.PropertyGetter;
import com.github.milegema.mlgm4a.data.properties.PropertySetter;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.data.repositories.tables.EntityAdapter;
import com.github.milegema.mlgm4a.data.repositories.tables.EntityContext;
import com.github.milegema.mlgm4a.data.repositories.tables.PropertyNamePrefix;

import java.net.URL;

public class DomainEntityAdapter extends BaseEntityAdapter {

    @Override
    public void convert(EntityContext ec) {
        super.convert(ec);
        final PropertyNamePrefix prefix = new PropertyNamePrefix(ec);
        final DomainEntity entity = (DomainEntity) ec.getEntity();
        if (ec.isEntityToProperties()) {
            PropertySetter pt = ec.getSetter();
            innerConvertE2P(prefix, entity, pt);
        } else {
            PropertyGetter pt = ec.getGetter();
            innerConvertP2E(prefix, pt, entity);
        }
    }

    private static void innerConvertE2P(PropertyNamePrefix prefix, DomainEntity src, PropertySetter dst) {
        dst.put(prefix.fullName("id"), src.getId());
        dst.put(prefix.fullName("name"), src.getName());
        dst.put(prefix.fullName("label"), src.getLabel());
        dst.put(prefix.fullName("description"), src.getDescription());
        dst.put(prefix.fullName("icon"), src.getIcon());
    }

    private static void innerConvertP2E(PropertyNamePrefix prefix, PropertyGetter src, DomainEntity dst) {

        long id = src.getLong(prefix.fullName("id"), 0);
        URL icon = src.getURL(prefix.fullName("icon"), null);
        String name = src.getString(prefix.fullName("name"), "");
        String label = src.getString(prefix.fullName("label"), "");
        String description = src.getString(prefix.fullName("description"), "");

        dst.setId(new DomainID(id));
        dst.setIcon(icon);
        dst.setName(name);
        dst.setLabel(label);
        dst.setDescription(description);
    }

}
