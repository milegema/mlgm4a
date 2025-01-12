package com.github.milegema.mlgm4a.network.web;

import java.util.ArrayList;
import java.util.List;

public final class WebFilterList {

    private final List<WebFilterRegistration> items;

    public WebFilterList() {
        this.items = new ArrayList<>();
    }


    private static class MyFilterRegistry implements WebFilterRegistry {

        private final WebFilterRegistration inner;

        MyFilterRegistry(WebFilterRegistration item) {
            if (item == null) {
                item = new WebFilterRegistration();
            }
            this.inner = item;
        }

        @Override
        public WebFilterRegistration registration() {
            return this.inner;
        }
    }

    private WebFilterList inner_add(WebFilterRegistration item) {
        if (item == null) {
            return this;
        }
        if (item.getOrder() == 0) {
            item.setOrder(this.items.size() + 1);
        }
        this.items.add(item);
        return this;
    }

    public WebFilterRegistration[] toArray() {
        return this.items.toArray(new WebFilterRegistration[0]);
    }

    public List<WebFilterRegistration> toList() {
        return new ArrayList<>(this.items);
    }

    public WebFilterList add(WebFilter item) {
        WebFilterRegistration reg = new WebFilterRegistration();
        reg.setFilter(item);
        return this.inner_add(reg);
    }

    public WebFilterList add(WebFilter item, int order) {
        WebFilterRegistration reg = new WebFilterRegistration();
        reg.setFilter(item);
        reg.setOrder(order);
        return this.inner_add(reg);
    }

    public WebFilterList add(WebFilterRegistry item) {
        if (item == null) {
            return this;
        }
        return this.inner_add(item.registration());
    }

    public WebFilterList add(WebFilterRegistration item) {
        return this.inner_add(item);
    }
}
