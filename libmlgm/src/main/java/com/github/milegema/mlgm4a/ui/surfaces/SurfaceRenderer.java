package com.github.milegema.mlgm4a.ui.surfaces;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.utils.Time;

public interface SurfaceRenderer {

    void render(SurfaceContext ctx, Canvas can) throws Exception;

}
