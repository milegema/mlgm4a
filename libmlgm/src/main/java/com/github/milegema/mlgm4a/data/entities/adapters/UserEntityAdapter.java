package com.github.milegema.mlgm4a.data.entities.adapters;

import com.github.milegema.mlgm4a.data.entities.AccountEntity;
import com.github.milegema.mlgm4a.data.entities.UserEntity;
import com.github.milegema.mlgm4a.data.ids.EmailAddress;
import com.github.milegema.mlgm4a.data.ids.EntityID;
import com.github.milegema.mlgm4a.data.properties.PropertyGetter;
import com.github.milegema.mlgm4a.data.properties.PropertySetter;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.data.repositories.tables.EntityAdapter;
import com.github.milegema.mlgm4a.data.repositories.tables.EntityContext;
import com.github.milegema.mlgm4a.data.repositories.tables.PropertyNamePrefix;
import com.github.milegema.mlgm4a.network.inforefs.RemoteURL;

import java.net.URL;

public class UserEntityAdapter extends BaseEntityAdapter {

    @Override
    public void convert(EntityContext ec) {
        super.convert(ec);
        final PropertyNamePrefix prefix = new PropertyNamePrefix(ec);
        final UserEntity entity = (UserEntity) ec.getEntity();
        final PropertyTable pt = ec.getProperties();
        if (ec.isEntityToProperties()) {
            convertE2P(prefix, entity, ec.getSetter());
        } else {
            convertP2E(prefix, ec.getGetter(), entity);
        }
    }

    private static void convertE2P(PropertyNamePrefix prefix, UserEntity src, PropertySetter dst) {

        dst.putObject(prefix.fullName("id"), src.getId());
        dst.putObject(prefix.fullName("email"), src.getEmail());
        dst.putObject(prefix.fullName("remote"), src.getRemote());

        dst.put(prefix.fullName("avatar"), src.getAvatar());
        dst.put(prefix.fullName("display-name"), src.getDisplayName());
        dst.put(prefix.fullName("signed_at"), src.getSignedAt());
    }

    private static void convertP2E(PropertyNamePrefix prefix, PropertyGetter src, UserEntity dst) {

        EntityID id = src.getEntityID(prefix.fullName("id"), null);
        EmailAddress email = src.getEmailAddress(prefix.fullName("email"), null);
        RemoteURL remote = src.getRemoteURL(prefix.fullName("remote"), null);
        URL avatar = src.getURL(prefix.fullName("avatar"), null);
        String display_name = src.getString(prefix.fullName("display-name"), null);
        long signed_at = src.getLong(prefix.fullName("signed_at"), 0);

        dst.setEntityID(id);
        dst.setRemote(remote);
        dst.setEmail(email);
        dst.setAvatar(avatar);
        dst.setDisplayName(display_name);
        dst.setSignedAt(signed_at);
    }
}
