package com.github.milegema.mlgm4a.components;

public class ComLifeManager {

    private final ComLifeContext context;


    public ComLifeManager() {
        this.context = new ComLifeContext();
    }

    public ComLifeManager(ComLifeContext ctx) {
        this.context = ctx;
    }


    public void add(ComLife life) {
        if (life == null) {
            return;
        }
        if (this.context.isCreated()) {
            // invoke onCreate directly
            ComLife.OnCreate on_create_func = life.getOnCreate();
            if (on_create_func != null) {
                try {
                    on_create_func.invoke();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        context.lives.add(life);
    }

    public ComLife getMain() {
        ComLifeEventDispatcher d = new ComLifeEventDispatcher(context);
        return d.life();
    }
}
