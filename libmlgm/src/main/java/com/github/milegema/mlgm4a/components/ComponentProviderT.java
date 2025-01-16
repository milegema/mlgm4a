package com.github.milegema.mlgm4a.components;

import com.github.milegema.mlgm4a.contexts.ApplicationContext;

public class ComponentProviderT<T> implements ComponentProvider {

    private FactoryT<T> factory;
    private WirerT<T> wirer;
    private String id;

    public ComponentProviderT() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getDefaultID() {
        String def_id = this.id;
        if (def_id == null) {
            if (this.factory != null) {
                def_id = this.factory.toString();
            } else if (this.wirer != null) {
                def_id = this.wirer.toString();
            }
        }
        return def_id;
    }

    public interface FactoryT<T> {
        T createInstance();
    }

    public interface WirerT<T> {
        void wire(ApplicationContext ctx, ComponentHolder holder, T inst);
    }


    @Override
    public Object createInstance() {
        return this.factory.createInstance();
    }

    @Override
    public void wire(ApplicationContext appContext, ComponentHolder holder) {

        WirerT<T> p_wirer = this.wirer; // 这个字段是一次性的
        this.wirer = null;
        if (p_wirer == null) {
            return;
        }

        Object inst = holder.getInstance();
        if (inst == null) {
            // call factory
            inst = this.createInstance();
            holder.setInstance(inst);
        }

        T inst_t = (T) inst;
        p_wirer.wire(appContext, holder, inst_t);
    }

    public FactoryT<T> getFactory() {
        return factory;
    }

    public void setFactory(FactoryT<T> factory) {
        this.factory = factory;
    }

    public WirerT<T> getWirer() {
        return wirer;
    }

    public void setWirer(WirerT<T> wirer) {
        this.wirer = wirer;
    }
}
