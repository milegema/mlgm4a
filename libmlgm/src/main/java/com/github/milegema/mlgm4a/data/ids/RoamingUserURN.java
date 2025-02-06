package com.github.milegema.mlgm4a.data.ids;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.milegema.mlgm4a.network.inforefs.RemoteURL;
import com.github.milegema.mlgm4a.utils.Strings;

public final class RoamingUserURN {

    private final String text;

    public RoamingUserURN(RemoteURL url, EmailAddress email) {
        this.text = stringify(url, email);
    }

    public static boolean equals(RoamingUserURN n1, RoamingUserURN n2) {
        if (n1 == null || n2 == null) {
            return false;
        }
        return n1.equals(n2);
    }


    private static String stringify(RemoteURL url, EmailAddress email) {
        final char sep_char = '/';
        final String sep = String.valueOf(sep_char);
        String str = email + sep + "roaming" + sep + url;
        String[] array = str.replace(':', sep_char).split(sep);
        StringBuilder builder = new StringBuilder();
        builder.append("user:/");
        for (String item : array) {
            item = item.trim();
            if (item.isEmpty()) {
                continue;
            }
            builder.append(sep).append(item);
        }
        return builder.toString();
    }

    @NonNull
    @Override
    public String toString() {
        return this.text;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof RoamingUserURN) {
            RoamingUserURN o2 = (RoamingUserURN) obj;
            return Strings.equals(this.text, o2.text);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.text.hashCode();
    }
}
