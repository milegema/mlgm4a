package com.github.milegema.mlgm4a.data.ids;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.milegema.mlgm4a.utils.Strings;

public final class EmailAddress {

    private final String user;
    private final String domain;

    public EmailAddress(String str) {
        String[] array = parseEmailAddress(str);
        this.user = array[0];
        this.domain = array[1];
    }

    private static String[] throwBadEmailAddress(String str) {
        throw new NumberFormatException("bad email address: " + str);
    }

    private static String[] parseEmailAddress(String str) {
        if (str == null) {
            return throwBadEmailAddress("null");
        }
        final char c1 = '@';
        final char c2 = '\n';
        String[] array = str.replace(c1, c2).split(String.valueOf(c2));
        if (array.length != 2) {
            return throwBadEmailAddress(str);
        }
        if (array[0].isEmpty() || array[1].isEmpty()) {
            return throwBadEmailAddress(str);

        }
        return array;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof EmailAddress) {
            EmailAddress a2 = (EmailAddress) obj;
            boolean b1 = Strings.equals(this.user, a2.user);
            boolean b2 = Strings.equals(this.domain, a2.domain);
            return b1 && b2;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        return user + '@' + domain;
    }
}
