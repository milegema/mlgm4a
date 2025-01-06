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

        @Override
        public FileAccessFilter getNextFilter() {
            return this.filter;
        }
    }

    private static class MyEnding implements FileAccessFilterChain {
        @Override
        public FileAccessResponse access(FileAccessRequest request) throws IOException {
            return null;
        }

        @Override
        public FileAccessFilter getNextFilter() {
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
            int o1 = a.getIndex();
            int o2 = b.getIndex();
            return o2 - o1;
        });
        return dst;
    }

    private static void applyAutoOrder(FileAccessFilterRegistration[] array) {
        int index;
        int current_index = FileAccessFilterOrder.Max.ordinal() + 1;
        for (FileAccessFilterRegistration item : array) {
            current_index++;
            if (item == null) {
                continue;
            }
            FileAccessFilterOrder order = item.getOrder();
            if (order == null) {
                order = FileAccessFilterOrder.Auto;
            }
            if (order == FileAccessFilterOrder.Auto) {
                index = current_index;
            } else {
                index = order.ordinal();
            }
            item.setIndex(index);
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
