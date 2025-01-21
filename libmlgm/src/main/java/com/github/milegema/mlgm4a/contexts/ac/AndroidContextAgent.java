package com.github.milegema.mlgm4a.contexts.ac;

import com.github.milegema.mlgm4a.contexts.ApplicationContext;
import com.github.milegema.mlgm4a.contexts.ContextAgent;
import com.github.milegema.mlgm4a.contexts.ContextHolder;
import com.github.milegema.mlgm4a.utils.Attributes;

public class AndroidContextAgent implements ContextAgent {

    private ApplicationContext applicationContext;

    private ContextHolder m_cached_holder;

    private final static String ATTR_NAME = ContextHolder.class.getName() + "#binding";

    public AndroidContextAgent() {
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private ContextHolder load() {
        Object obj = this.applicationContext.attributes().get(ATTR_NAME, true);
        return (ContextHolder) obj;
    }

    public static void bind(ContextHolder holder, Attributes attrs) {
        attrs.set(ATTR_NAME, holder);
    }

    @Override
    public ContextHolder getContextHolder() {
        ContextHolder ch = this.m_cached_holder;
        if (ch == null) {
            ch = this.load();
            this.m_cached_holder = ch;
        }
        return ch;
    }
}
