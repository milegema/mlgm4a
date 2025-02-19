package com.github.milegema.mlgm4a.data.types;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.milegema.mlgm4a.utils.Strings;

public class Text {

    private final String value;

    public Text() {
        this.value = "";
    }

    public Text(String str) {
        if (str == null) {
            str = "";
        }
        this.value = str;
    }

    @Override
    public boolean equals(@Nullable Object o2) {
        if (o2 == null) {
            return false;
        }
        if (o2 == this) {
            return true;
        }
        if (o2 instanceof Text) {
            Text txt2 = (Text) o2;
            Class<?> c1 = this.getClass();
            Class<?> c2 = o2.getClass();
            if (c1.equals(c2)) {
                return Strings.equals(this.value, txt2.value);
            }
        }
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
}
