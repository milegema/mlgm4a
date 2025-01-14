package com.github.milegema.mlgm4a.ui.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.milegema.mlgm4a.application.BaseMilegemaActivity;
import com.github.milegema.mlgm4a.components.ComLifeManager;

public final class ViewUtils {

    private ViewUtils() {
    }

    public static View loadLayout(int layout_res_id, ViewGroup root) {
        Context ctx = root.getContext();
        return LayoutInflater.from(ctx).inflate(layout_res_id, root);
    }

    public static ComLifeManager getLifeManager(Context ctx) {
        BaseMilegemaActivity activity = (BaseMilegemaActivity) ctx;
        return activity.getLifeManager();
    }

}
