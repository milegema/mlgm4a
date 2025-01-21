package com.github.milegema.mlgm4a.data.ids;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.security.SecurityRandom;
import com.github.milegema.mlgm4a.security.hash.Sum128;

public final class UUID {

    private final Sum128 inner;

    private UUID(Sum128 sum) {
        this.inner = sum;
    }

    public UUID(String str) {
        this.inner = Format.parse(str);
    }

    public static UUID generate() {
        byte[] buffer = new byte[128 / 8];
        SecurityRandom.getRandom().nextBytes(buffer);
        return new UUID(new Sum128(buffer));
    }

    public static UUID create(byte[] buffer) {
        return new UUID(new Sum128(buffer));
    }

    public static UUID zero() {
        return new UUID(new Sum128());
    }

    private static class Format {

        static String stringify(UUID uuid) {
            // format: '00000000-0000-0000-0000-000000000000'
            final String str = uuid.inner.toString();
            StringBuilder builder = new StringBuilder();
            int[] len_list = {8, 4, 4, 4};
            int i = 0;
            for (int len : len_list) {
                String sub = str.substring(i, i + len);
                builder.append(sub).append('-');
                i += len;
            }
            builder.append(str.substring(i));
            //  Logs.debug("sum.str = " + str);
            return builder.toString();
        }

        static Sum128 parse(String str) {
            String[] parts = str.split("-");
            StringBuilder builder = new StringBuilder();
            for (String part : parts) {
                builder.append(part);
            }
            return new Sum128(builder.toString());
        }
    }


    @Override
    public int hashCode() {
        return this.inner.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (o instanceof UUID) {
            UUID o2 = (UUID) o;
            return this.inner.equals(o2.inner);
        }
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        return Format.stringify(this);
    }
}
