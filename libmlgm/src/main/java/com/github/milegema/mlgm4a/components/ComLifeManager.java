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
        context.lives.add(life);
    }

    public ComLife getMain() {
        ComLifeEventDispatcher d = new ComLifeEventDispatcher(context);
        return d.life();
    }
}
