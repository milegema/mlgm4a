package com.github.milegema.mlgm4a.data.files;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class FileAccessFilterChainBuilder {

    private final List<FileAccessFilterRegistration> items;

    public FileAccessFilterChainBuilder() {
        this.items = new ArrayList<>();
    }


    private static class MyNode implements FileAccessFilterChain {

        final FileAccessFilter filter;
        final FileAccessFilterChain next;

        MyNode(FileAccessFilter f, FileAccessFilterChain n) {
            this.filter = f;
            this.next = n;
        }

        @Override
        public FileAccessResponse access(FileAccessRequest request) throws IOException {
            return this.filter.access(request, this.next);
        }
    }

    private static class MyEnding implements FileAccessFilterChain {
        @Override
        public FileAccessResponse access(FileAccessRequest request) throws IOException {
            return null;
        }
    }

    public FileAccessFilterChainBuilder add(FileAccessFilterRegistration item) {
        if (item == null) {
            return this;
        }
        if (item.getFilter() == null) {
            return this;
        }
        this.items.add(item);
        return this;
    }

    public FileAccessFilterChainBuilder add(FileAccessFilterRegistry registry) {
        if (registry == null) {
            return this;
        }
        return this.add(registry.registration());
    }


    private FileAccessFilterRegistration[] getSortedItems() {
        FileAccessFilterRegistration[] dst = this.items.toArray(new FileAccessFilterRegistration[0]);
        applyAutoOrder(dst);
        Arrays.sort(dst, (a, b) -> {
            if (a == null || b == null) {
                return 0;
            }
            return b.getOrder() - a.getOrder();
        });
        return dst;
    }

    private static void applyAutoOrder(FileAccessFilterRegistration[] array) {
        int order_auto = FileAccessFilterOrder.Auto.ordinal();
        int order_current = FileAccessFilterOrder.Max.ordinal() + 1;
        for (FileAccessFilterRegistration item : array) {
            if (item == null) {
                continue;
            }
            if (item.getOrder() == order_auto) {
                item.setOrder(order_current);
            }
            order_current++;
        }
    }

    private static boolean isAvailable(FileAccessFilterRegistration item) {
        if (item == null) {
            return false;
        }
        if (item.getFilter() == null) {
            return false;
        }
        return item.isEnabled();
    }


    public FileAccessFilterChain create() {
        FileAccessFilterRegistration[] src = this.getSortedItems();
        FileAccessFilterChain chain = new MyEnding();
        for (FileAccessFilterRegistration item : src) {
            if (!isAvailable(item)) {
                continue;
            }
            chain = new MyNode(item.getFilter(), chain);
        }
        return chain;
    }
}
