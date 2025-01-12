package com.github.milegema.mlgm4a.components;


import com.github.milegema.mlgm4a.logs.Logs;

import java.util.ArrayList;
import java.util.List;

public class ComLifeContext {

    public boolean starting;
    public boolean started;
    public boolean stopping;
    public boolean stopped;

    //   public final List<Throwable> errors;
    public final List<ComLife> lives;

    public ComLifeContext() {
        //     this.errors = new ArrayList<>();
        this.lives = new ArrayList<>();
    }

    public void push(Throwable err) {
        //   if (err == null) {
        //      return;
        //   }
        //  this.errors.add(err);
        Logs.error("ComLifeContext.handle_error:", err);
    }

}
