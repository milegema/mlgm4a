package com.github.milegema.mlgm4a.contexts.ac;

import android.content.Context;

import com.github.milegema.mlgm4a.components.ComLife;
import com.github.milegema.mlgm4a.components.ComLifeManager;
import com.github.milegema.mlgm4a.components.ComLifecycle;
import com.github.milegema.mlgm4a.components.ComponentHolder;
import com.github.milegema.mlgm4a.components.ComponentManager;
import com.github.milegema.mlgm4a.components.ComponentRegistrar;
import com.github.milegema.mlgm4a.components.ComponentSet;
import com.github.milegema.mlgm4a.components.ComponentSetBuilder;
import com.github.milegema.mlgm4a.components.ComponentWirer;
import com.github.milegema.mlgm4a.components.DefaultComponentSet;
import com.github.milegema.mlgm4a.configurations.Configuration;
import com.github.milegema.mlgm4a.configurations.Customizer;
import com.github.milegema.mlgm4a.configurations.PropertiesHolder;
import com.github.milegema.mlgm4a.contexts.ApplicationContext;
import com.github.milegema.mlgm4a.data.properties.Names;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.logs.Logs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DefaultApplicationContextFactory implements ApplicationContextFactory {

    public DefaultApplicationContextFactory() {
    }

    private interface StepFunc {
        void doStep(ApplicationContextInner inner) throws Exception;
    }

    @Override
    public ApplicationContext create(Configuration configuration) {
        ApplicationContextInner inner = new ApplicationContextInner();
        inner.setConfiguration(configuration);
        List<StepFunc> steps = new ArrayList<>();
        // steps
        steps.add(this::step_init);
        steps.add(this::step_customize);

        steps.add(this::step_load_properties); // load first time
        steps.add(this::step_load_profile);
        steps.add(this::step_load_properties); // load again

        steps.add(this::step_load_components);
        steps.add(this::step_wire_components);
        steps.add(this::step_apply_components_lifecycle);

        steps.add(this::step_log_properties);
        steps.add(this::step_log_profile);
        steps.add(this::step_log_done);

        Logs.info("boot: " + inner);

        // invoke
        try {
            for (StepFunc fn : steps) {
                fn.doStep(inner);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return inner.getFacade();
    }

    private void step_init(ApplicationContextInner inner) {

        long now = System.currentTimeMillis();
        Configuration cfg = inner.getConfiguration();
        DefaultComponentSet com_set = new DefaultComponentSet();

        inner.setFacade(new ApplicationContextFacade(inner));
        inner.setProfile(cfg.getProfile());
        inner.setAttributes(cfg.getAttributes());
        inner.setProperties(PropertyTable.Factory.create());
        inner.setCreatedAt(now);
        inner.setAndroid(cfg.getAndroid());
        inner.setComponentManager(com_set);
        inner.setComponentRegistrar(com_set);

        //  inner.setComponents(  ...   );
    }

    private void step_customize(ApplicationContextInner inner) {
        Configuration config = inner.getConfiguration();
        List<Customizer> all = config.getCustomizers();
        for (Customizer c : all) {
            c.customize(config);
        }
    }

    private void step_load_properties(ApplicationContextInner inner) {

        Configuration config = inner.getConfiguration();
        String profile = inner.getProfile();
        List<PropertiesHolder> src = config.getProperties();
        PropertyTable dst = inner.getProperties();
        String want_name = "application.properties";

        if (profile != null) {
            profile = profile.trim();
            if (!profile.isEmpty()) {
                want_name = "application-" + profile + ".properties";
            }
        }

        for (PropertiesHolder ph : src) {
            if (want_name.equals(ph.getName())) {
                this.inner_copy_props(ph.getProperties(), dst);
            }
        }

        Logs.info("load properties from [" + want_name + "]");
    }

    private void inner_copy_props(PropertyTable src, PropertyTable dst) {
        if (src == null || dst == null) {
            return;
        }
        dst.importAll(src.exportAll(null));
    }

    private void step_log_profile(ApplicationContextInner inner) {
        String profile = inner.getProfile();
        Logs.info(Names.application_profiles_active + " = " + profile);
    }

    private void step_log_done(ApplicationContextInner inner) {

        long t0 = inner.getCreatedAt();
        long now = System.currentTimeMillis();
        long ms = now - t0;
        String app = this.try_get_app_name(inner.getAndroid());

        inner.setStartedAt(now);
        String msg = "started application (" + app + ") in " + ms + " ms";
        Logs.info(msg);
    }

    private String try_get_app_name(Context ctx) {
        if (ctx == null) {
            return "";
        }
        Context aac = ctx.getApplicationContext();
        if (aac == null) {
            return "";
        }
        return aac.getClass().getName();
    }

    private void step_log_properties(ApplicationContextInner inner) {
        final String nl = "\n";
        PropertyTable props = inner.getProperties();
        StringBuilder buffer = new StringBuilder();
        buffer.append("Properties:").append(nl);
        String[] names = props.names();
        Arrays.sort(names);
        for (String name : names) {
            String value = props.get(name);
            buffer.append("\t").append(name).append(" = ").append(value).append(nl);
        }
        buffer.append("[End of Properties]").append(nl);
        Logs.info(buffer.toString());
    }

    private void step_load_profile(ApplicationContextInner inner) {

        Configuration cfg = inner.getConfiguration();
        String profile = null;

        final String profile_def = "default";
        final String profile_force = cfg.getProfile();
        final String profile_props = inner.getProperties().get(Names.application_profiles_active);

        String[] list = {profile_force, profile_props, profile_def};
        for (String item : list) {
            if (item != null) {
                item = item.toLowerCase().trim();
                if (!item.isEmpty()) {
                    profile = item;
                    break;
                }
            }
        }

        if (profile == null) {
            profile = profile_def;
        }
        inner.setProfile(profile);
    }

    private void step_load_components(ApplicationContextInner inner) {
        ComponentSetBuilder src1 = inner.getConfiguration().getComponentSetBuilder();
        ComponentSet src = src1.create();
        ComponentRegistrar dst = inner.getComponentRegistrar();
        String[] ids = src.listIds();
        for (String id : ids) {
            ComponentHolder holder = src.getHolder("#" + id);
            init_component(holder);
            dst.register(holder);
        }
    }

    private static void init_component(ComponentHolder holder) {
        Object inst = holder.getInstance();
        if (inst != null) {
            return;
        }
        inst = holder.getFactory().createInstance();
        holder.setInstance(inst);
    }

    private void step_wire_components(ApplicationContextInner inner) {
        ApplicationContext app_ctx = inner.getFacade();
        ComponentManager src = inner.getComponentManager();
        String[] ids = src.listIds();
        for (String id : ids) {
            ComponentHolder holder = src.getHolder("#" + id);
            ComponentWirer wirer = holder.getWirer();
            if (wirer == null) {
                continue;
            }
            wirer.wire(app_ctx, holder);
        }
    }

    private void step_apply_components_lifecycle(ApplicationContextInner inner) throws Exception {
        ComponentManager src = inner.getComponentManager();
        ComLifeManager dst = new ComLifeManager();
        String[] ids = src.listIds();
        for (String id : ids) {
            ComponentHolder holder = src.getHolder("#" + id);
            Object inst = holder.getInstance();
            if (inst instanceof ComLifecycle) {
                ComLifecycle lifecycle = (ComLifecycle) inst;
                dst.add(lifecycle.life());
            }
        }
        ComLife cl_main = dst.getMain();
        invoke_lifecycle_callback(cl_main.getOnCreate());
        invoke_lifecycle_callback(cl_main.getOnStart());
        invoke_lifecycle_callback(cl_main.getOnResume());
    }


    private static void invoke_lifecycle_callback(ComLife.Callback callback) throws Exception {
        if (callback == null) {
            return;
        }
        callback.invoke();
    }
}
