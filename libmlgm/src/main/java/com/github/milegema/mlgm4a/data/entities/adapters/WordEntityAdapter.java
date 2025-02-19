package com.github.milegema.mlgm4a.data.entities.adapters;

import com.github.milegema.mlgm4a.data.entities.WordEntity;
import com.github.milegema.mlgm4a.data.ids.WordID;
import com.github.milegema.mlgm4a.data.properties.PropertyGetter;
import com.github.milegema.mlgm4a.data.properties.PropertySetter;
import com.github.milegema.mlgm4a.data.repositories.tables.EntityContext;
import com.github.milegema.mlgm4a.data.repositories.tables.PropertyNamePrefix;

public class WordEntityAdapter extends BaseEntityAdapter {

    @Override
    public void convert(EntityContext ec) {

        final WordEntity entity = (WordEntity) ec.getEntity();
        final PropertyNamePrefix prefix = new PropertyNamePrefix(ec);

        final String name_id = prefix.fullName("id");
        final String name_length = prefix.fullName("length");
        final String name_charset = prefix.fullName("charset");

        if (ec.isEntityToProperties()) {
            // entity to props ...
            PropertySetter setter = ec.getSetter();

            setter.put(name_id, entity.getId());
            setter.put(name_length, entity.getLength());
            setter.put(name_charset, entity.getCharset());

            String[] required_list = {name_id, name_length, name_charset};
            checkRequiredFields(required_list, ec.getProperties(), true);

        } else {
            // props to entity ...
            PropertyGetter getter = ec.getGetter();

            // required fields
            getter.setRequired(true);
            long id = getter.getLong(name_id, 0);
            int length = getter.getInt(name_length, 8);
            String charset = getter.getString(name_charset, "0123456789");

            // optional fields
            getter.setRequired(false);


            entity.setId(new WordID(id));
            entity.setLength(length);
            entity.setCharset(charset);
        }

        super.convert(ec);
    }
}
