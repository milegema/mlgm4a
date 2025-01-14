package com.github.milegema.mlgm4a.components;


import com.github.milegema.mlgm4a.logs.Logs;

import java.util.ArrayList;
import java.util.List;

public class ComLifeContext {

    public boolean starting;
    public boolean started;
    public boolean stopping;
    public boolean stopped;
    public boolean created;

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


    public boolean isStarting() {
        return starting;
    }

    public void setStarting(boolean starting) {
        this.starting = starting;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isStopping() {
        return stopping;
    }

    public void setStopping(boolean stopping) {
        this.stopping = stopping;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }
}
