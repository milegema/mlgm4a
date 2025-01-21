package com.github.milegema.mlgm4a.data.entities.adapters;

import com.github.milegema.mlgm4a.data.entities.AccountEntity;
import com.github.milegema.mlgm4a.data.ids.AccountID;
import com.github.milegema.mlgm4a.data.properties.PropertyGetter;
import com.github.milegema.mlgm4a.data.properties.PropertySetter;
import com.github.milegema.mlgm4a.data.repositories.tables.EntityContext;
import com.github.milegema.mlgm4a.data.repositories.tables.PropertyNamePrefix;

public class AccountEntityAdapter extends BaseEntityAdapter {

    @Override
    public void convert(EntityContext ec) {

        final AccountEntity entity = (AccountEntity) ec.getEntity();
        final PropertyNamePrefix prefix = new PropertyNamePrefix(ec);

        final String name_id = prefix.fullName("id");
        final String name_username = prefix.fullName("username");
        final String name_domain = prefix.fullName("domain");
        final String name_label = prefix.fullName("label");
        final String name_description = prefix.fullName("description");

        if (ec.isEntityToProperties()) {
            // entity to props ...
            PropertySetter setter = ec.getSetter();

            setter.put(name_id, entity.getId());
            setter.put(name_username, entity.getUsername());
            setter.put(name_domain, entity.getDomain());
            setter.put(name_label, entity.getLabel());
            setter.put(name_description, entity.getDescription());

            String[] required_list = {name_id, name_username, name_domain};
            checkRequiredFields(required_list, ec.getProperties(), true);

        } else {
            // props to entity ...
            PropertyGetter getter = ec.getGetter();

            // required fields
            getter.setRequired(true);
            long id = getter.getLong(name_id, 0);
            String username = getter.getString(name_username, "");
            String domain = getter.getString(name_domain, "");

            // optional fields
            getter.setRequired(false);
            String label = getter.getString(name_label, "");
            String description = getter.getString(name_description, "");


            entity.setId(new AccountID(id));
            entity.setUsername(username);
            entity.setDomain(domain);
            entity.setLabel(label);
            entity.setDescription(description);
        }

        super.convert(ec);
    }

}
