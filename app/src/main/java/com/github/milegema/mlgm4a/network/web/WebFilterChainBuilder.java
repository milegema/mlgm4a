package com.github.milegema.mlgm4a.network.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class WebFilterChainBuilder {

    private final List<WebFilterRegistration> filters;

    public WebFilterChainBuilder() {
        this.filters = new ArrayList<>();
    }


    private static class MyNode implements WebFilterChain {

        private final WebFilter filter;
        private final WebFilterChain next;

        public MyNode(WebFilterRegistration item, WebFilterChain chain) {
            this.filter = item.getFilter();
            this.next = chain;
        }

        @Override
        public WebResponse execute(WebRequest request) throws IOException {
            return this.filter.execute(request, this.next);
        }
    }

    private static class MyEnding implements WebFilterChain {
        @Override
        public WebResponse execute(WebRequest request) throws IOException {
            return null; // NOP
        }
    }


    private WebFilterChainBuilder inner_add(WebFilterRegistration item) {
        if (is_available(item)) {
            this.filters.add(item);
        }
        return this;
    }

    public WebFilterChainBuilder add(WebFilter item) {
        WebFilterRegistration reg = new WebFilterRegistration();
        reg.setEnabled(true);
        reg.setOrder(0);
        reg.setFilter(item);
        return this.inner_add(reg);
    }

    public WebFilterChainBuilder add(WebFilterRegistration item) {
        return this.inner_add(item);
    }

    public WebFilterChainBuilder add(WebFilterRegistry item) {
        if (item == null) {
            return this;
        }
        return this.inner_add(item.registration());
    }


    private static boolean is_available(WebFilterRegistration reg) {
        if (reg == null) {
            return false;
        }
        if (reg.getFilter() == null) {
            return false;
        }
        return reg.isEnabled();
    }

    private int sort_comp_func(WebFilterRegistration r1, WebFilterRegistration r2) {
        if (r1 == null || r2 == null) {
            return 0;
        }
        return r2.getOrder() - r1.getOrder();
    }

    public WebFilterChain create() {
        WebFilterChain chain = new MyEnding();
        this.filters.sort(this::sort_comp_func);
        for (WebFilterRegistration item : this.filters) {
            if (is_available(item)) {
                chain = new MyNode(item, chain);
            }
        }
        return chain;
    }
}
