package com.github.milegema.mlgm4a.data.properties;

import android.util.NoSuchPropertyException;

import com.github.milegema.mlgm4a.data.files.FileAccessLayerClass;
import com.github.milegema.mlgm4a.data.ids.EmailAddress;
import com.github.milegema.mlgm4a.data.ids.EntityID;
import com.github.milegema.mlgm4a.data.ids.LongID;
import com.github.milegema.mlgm4a.data.ids.UUID;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockID;
import com.github.milegema.mlgm4a.data.repositories.blocks.BlockType;
import com.github.milegema.mlgm4a.data.repositories.refs.RefName;
import com.github.milegema.mlgm4a.errors.Errors;
import com.github.milegema.mlgm4a.network.inforefs.RemoteURL;
import com.github.milegema.mlgm4a.security.CipherMode;
import com.github.milegema.mlgm4a.security.CipherPadding;
import com.github.milegema.mlgm4a.utils.Base64;
import com.github.milegema.mlgm4a.utils.Hex;
import com.github.milegema.mlgm4a.utils.Time;


public class PropertyGetter {

    private PropertyTable properties;
    private boolean required;

    public PropertyGetter() {
        this.properties = PropertyTable.Factory.create();
        this.required = true;
    }

    public PropertyGetter(PropertyTable src) {
        if (src == null) {
            src = PropertyTable.Factory.create();
        }
        this.properties = src;
        this.required = true;
    }


    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public PropertyTable getProperties() {
        return properties;
    }

    public void setProperties(PropertyTable p) {
        if (p == null) {
            return;
        }
        this.properties = p;
    }

    private String innerGet(String name) {
        String str = this.properties.get(name);
        if (str == null) {
            if (this.required) {
                throw new NoSuchPropertyException("no property named: " + name);
            }
        }
        return str;
    }


    public String getString(String name, String def) {
        return innerGet(name);
    }

    public int getInt(String name, int def) {
        String str = innerGet(name);
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            Errors.handle(null, e);
        }
        return def;
    }

    public boolean getBoolean(String name, boolean def) {
        String str = innerGet(name);
        if (str == null) {
            return def;
        }
        str = str.toLowerCase();
        switch (str) {
            case "true":
            case "yes":
            case "1":
            case "t":
            case "y":
                return true;
            default:
                break;
        }
        return def;
    }


    public long getLong(String name, long def) {
        String str = innerGet(name);
        if (str == null) {
            return def;
        }
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            Errors.handle(null, e);
        }
        return def;
    }

    public short getShort(String name, short def) {
        String str = innerGet(name);
        try {
            return Short.parseShort(str);
        } catch (Exception e) {
            Errors.handle(null, e);
        }
        return def;
    }


    public byte[] getDataHex(String name, byte[] def) {
        String str = innerGet(name);
        try {
            final String prefix = "hex:";
            if (str.startsWith(prefix)) {
                str = str.substring(prefix.length()).trim();
            }
            return Hex.parse(str);
        } catch (Exception e) {
            Errors.handle(null, e);
        }
        return def;
    }

    public byte[] getDataBase64(String name, byte[] def) {
        String str = innerGet(name);
        try {
            final String prefix = "base64:";
            if (str.startsWith(prefix)) {
                str = str.substring(prefix.length()).trim();
            }
            return Base64.decode(str);
        } catch (Exception e) {
            Errors.handle(null, e);
        }
        return def;
    }

    public byte[] getDataAuto(String name, byte[] def) {
        String str = innerGet(name);
        if (str == null) {
            return def;
        } else if (str.startsWith("base64:")) {
            return this.getDataBase64(name, def);
        } else if (str.startsWith("hex:")) {
            return this.getDataHex(name, def);
        }
        if (Hex.isHexString(str)) {
            return Hex.parse(str);
        }
        return Base64.decode(str);
    }


    public byte[] getData(String name, byte[] def) {
        return getDataAuto(name, def);
    }

    public FileAccessLayerClass getFileAccessLayerClass(String name, FileAccessLayerClass def) {
        String str = innerGet(name);
        if (str == null) {
            return def;
        }
        try {
            return FileAccessLayerClass.valueOf(str);
        } catch (Exception e) {
            Errors.handle(null, e);
        }
        return def;
    }

    public BlockType getBlockType(String name, BlockType def) {
        String str = innerGet(name);
        try {
            return BlockType.valueOf(str);
        } catch (Exception e) {
            Errors.handle(null, e);
        }
        return def;
    }

    public BlockID getBlockID(String name, BlockID def) {
        String str = innerGet(name);
        if (str == null) {
            return def;
        }
        if (str.isEmpty()) {
            return def;
        }
        try {
            return new BlockID(str);
        } catch (Exception e) {
            Errors.handle(null, e);
        }
        return def;
    }


    public RefName getRefName(String name, RefName def) {
        String str = innerGet(name);
        if (str == null) {
            return def;
        }
        if (str.isEmpty()) {
            return def;
        }
        try {
            return new RefName(str);
        } catch (Exception e) {
            Errors.handle(null, e);
        }
        return def;
    }


    public CipherPadding getPaddingMode(String name, CipherPadding def) {
        String str = innerGet(name);
        try {
            return CipherPadding.valueOf(str);
        } catch (Exception e) {
            Errors.handle(null, e);
        }
        return def;
    }

    public CipherMode getCipherMode(String name, CipherMode def) {
        String str = innerGet(name);
        try {
            return CipherMode.valueOf(str);
        } catch (Exception e) {
            Errors.handle(null, e);
        }
        return def;
    }


    public EmailAddress getEmailAddress(String name, EmailAddress def) {
        String str = innerGet(name);
        if (str == null) {
            return def;
        }
        return new EmailAddress(str);
    }

    public RemoteURL getRemoteURL(String name, RemoteURL def) {
        String str = innerGet(name);
        if (str == null) {
            return def;
        }
        return new RemoteURL(str);
    }

    public EntityID getEntityID(String name, EntityID def) {
        String str = innerGet(name);
        if (str == null) {
            return def;
        }
        try {
            long n = Long.parseLong(str);
            return new LongID(n);
        } catch (Exception e) {
            return def;
        }
    }


    public Time getTime(String name, Time def) {
        String str = innerGet(name);
        try {
            long n = Long.parseLong(str);
            return new Time(n);
        } catch (Exception e) {
            Errors.handle(null, e);
        }
        return def;
    }

    public UUID getUUID(String name, UUID def) {
        String str = innerGet(name);
        try {
            return new UUID(str);
        } catch (Exception e) {
            Errors.handle(null, e);
        }
        return def;
    }

}
