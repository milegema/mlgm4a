package com.github.milegema.mlgm4a.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.github.milegema.mlgm4a.components.ComLifeManager;
import com.github.milegema.mlgm4a.libmlgm.R;
import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.ui.surfaces.SurfaceContext;
import com.github.milegema.mlgm4a.ui.surfaces.SurfaceContextBuilder;
import com.github.milegema.mlgm4a.ui.surfaces.SurfaceRenderer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UnlockGraphView extends LinearLayout {

    private OnResultListener onResultListener;

    public UnlockGraphView(Context context) {
        super(context);
        load_layout();
    }

    public UnlockGraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        load_layout();
    }

    public UnlockGraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        load_layout();
    }

    public UnlockGraphView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        load_layout();
    }

    public interface OnResultListener {
        void onResult(int[] result, String result_text);
    }

    private class MyGraphContext {

        MyGraphLayout layout;
        final MyPoint[] points;
        MyPoint[] result_points;
        PointF current_point_at;
        float sideLength; // 每一格的边长

        int colorForeground;
        int colorBackground;

        MyGraphContext() {
            MyPoint[] pt_list = new MyPoint[9];
            for (int i = 0; i < pt_list.length; i++) {
                MyPoint point = new MyPoint();
                point.index = i;
                point.number = i + 1;
                pt_list[i] = point;
            }
            this.points = pt_list; // 九宫格
            this.colorBackground = Color.LTGRAY;
            this.colorForeground = Color.rgb(100, 200, 150);
        }

        void reset() {
            for (MyPoint pt : this.points) {
                pt.hit = false;
                pt.order = 0;
            }
            this.result_points = null;
        }

        void update_result_points() {
            List<MyPoint> list = new ArrayList<>();
            for (MyPoint pt : this.points) {
                if (pt.hit) {
                    list.add(pt);
                }
            }
            list.sort(Comparator.comparingInt(a -> a.order));
            this.result_points = list.toArray(new MyPoint[0]);
        }

        private int[] result() {
            MyPoint[] src = this.result_points;
            if (src == null) {
                return new int[0];
            }
            int[] dst = new int[src.length];
            for (int i = 0; i < dst.length; ++i) {
                dst[i] = src[i].number;
            }
            return dst;
        }
    }

    private static String stringify_result(int[] result) {
        StringBuilder builder = new StringBuilder();
        if (result != null) {
            for (int num : result) {
                builder.append(num);
            }
        }
        return builder.toString();
    }

    private static class MyPoint {

        float x;
        float y;
        int index; // 正常的顺序 [0,8]
        int number; // 正常的顺序 [1,9]
        int order; // 输入的顺序 [1,9]; 如果还没有命中, 设为0
        boolean hit;

        MyPoint() {
        }

    }


    private class MyGraphRenderer implements SurfaceRenderer {

        final MyGraphContext graphContext;

        MyGraphRenderer(MyGraphContext gc) {
            this.graphContext = gc;
        }

        @Override
        public void render(SurfaceContext ctx, Canvas can) throws Exception {
            this.checkLayout(ctx);
            this.paintBackground(ctx, can);
            this.paintBorder(ctx, can);
            this.paintPoints(ctx, can);
            this.paintLine(ctx, can);
        }

        private void paintBackground(SurfaceContext ctx, Canvas can) {
            int w = ctx.getWidth();
            int h = ctx.getHeight();
            Paint paint = new Paint();
            paint.setColor(this.graphContext.colorBackground);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setStrokeWidth(1);
            can.drawRect(0, 0, w, h, paint);
        }

        private void paintBorder(SurfaceContext ctx, Canvas can) {
            int w = ctx.getWidth();
            int h = ctx.getHeight();
            Paint paint = new Paint();
            paint.setColor(this.graphContext.colorForeground);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);
            can.drawRect(0, 0, w, h, paint);
        }

        private void paintLine(SurfaceContext ctx, Canvas can) {
            PointF current = this.graphContext.current_point_at;
            MyPoint[] res_pt_list = this.graphContext.result_points;
            if (current == null || res_pt_list == null) {
                return;
            }
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setStrokeWidth(5);
            paint.setColor(this.graphContext.colorForeground);
            for (int i = res_pt_list.length - 1; i >= 0; --i) {
                MyPoint p = res_pt_list[i];
                PointF next = new PointF(p.x, p.y);
                can.drawLine(current.x, current.y, next.x, next.y, paint);
                current = next;

            }
        }

        private void paintPoints(SurfaceContext ctx, Canvas can) {
            float radius = this.graphContext.sideLength / 5;
            Paint paint = new Paint();
            paint.setColor(this.graphContext.colorForeground);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(3);
            Paint paint_hit = new Paint(paint);
            paint_hit.setStyle(Paint.Style.FILL_AND_STROKE);
            MyPoint[] all = this.graphContext.points;
            for (MyPoint point : all) {
                float cx = point.x;
                float cy = point.y;
                if (point.hit) {
                    // 画一个更小的实心圆
                    can.drawCircle(cx, cy, radius / 2, paint_hit);
                }
                can.drawCircle(cx, cy, radius, paint);
            }
        }


        private void checkLayout(SurfaceContext ctx) {
            MyGraphLayout layout = this.graphContext.layout;
            if (layout != null) {
                if (layout.needUpdate(ctx)) {
                    layout = null;
                }
            }
            if (layout == null) {
                layout = new MyGraphLayout(ctx);
                layout.update(this.graphContext);
                this.graphContext.layout = layout;
            }
        }
    }

    private class MyGraphLayout {

        private final int width;
        private final int height;
        private final int revision;

        public MyGraphLayout(SurfaceContext ctx) {
            this.width = ctx.getWidth();
            this.height = ctx.getHeight();
            this.revision = ctx.getLayoutRevision();
        }

        public void update(MyGraphContext gc) {
            final int total_row = 3;
            final int total_col = 3;
            final float outer_w = this.width;
            final float outer_h = this.height;
            final float inner_w = Math.min(outer_w, outer_h);
            final float inner_h = Math.min(outer_w, outer_h);
            final float inner_x = (outer_w - inner_w) / 2;
            final float inner_y = (outer_h - inner_h) / 2;
            final float side_len = (inner_w / 3);
            for (int row = 0; row < total_row; row++) {
                for (int col = 0; col < total_col; col++) {
                    final int index = (row * total_col) + col;
                    MyPoint pt = getPointAt(index, gc);
                    if (pt == null) {
                        continue;
                    }
                    pt.x = inner_x + (col * side_len) + (side_len / 2);
                    pt.y = inner_y + (row * side_len) + (side_len / 2);
                }
            }
            gc.sideLength = side_len;
        }

        MyPoint getPointAt(int index, MyGraphContext gc) {
            MyPoint[] all = gc.points;
            if (0 <= index && index < all.length) {
                return all[index];
            }
            return null;
        }

        public boolean needUpdate(SurfaceContext ctx) {
            boolean b1 = ctx.getWidth() == this.width;
            boolean b2 = ctx.getHeight() == this.height;
            boolean b3 = ctx.getLayoutRevision() == this.revision;
            return !(b1 && b2 && b3);
        }
    }

    private class MyOnTouchListener implements OnTouchListener {
        final MyGraphContext context;

        public MyOnTouchListener(MyGraphContext gc) {
            this.context = gc;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    this.onTouchBegin(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    this.onTouchMove(event);
                    break;
                case MotionEvent.ACTION_UP:
                    this.onTouchEnd(event);
                    v.performClick();
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                case MotionEvent.ACTION_POINTER_UP:
                default:
                    break;
            }
            return true;
        }

        private MyPoint findPointAt(float x, float y) {
            MyPoint[] all = this.context.points;
            float threshold = this.context.sideLength / 5;
            float threshold2 = threshold * threshold;
            for (MyPoint pt : all) {
                float diff_x = x - pt.x;
                float diff_y = y - pt.y;
                float distance2 = (diff_x * diff_x) + (diff_y * diff_y);
                if (distance2 < threshold2) {
                    return pt;
                }
            }
            return null;
        }

        private void handle_move(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            this.context.current_point_at = new PointF(x, y);
            MyPoint pt = findPointAt(x, y);
            if (pt == null) {
                return;
            }
            if (pt.hit) {
                return;
            }
            MyPoint[] res_pt_list = this.context.result_points;
            pt.hit = true;
            pt.order = 0;
            if (res_pt_list != null) {
                pt.order = res_pt_list.length;
            }
            this.context.update_result_points();
        }

        private void onTouchBegin(MotionEvent event) {
            this.context.reset();
            this.handle_move(event);
        }

        private void onTouchMove(MotionEvent event) {
            this.handle_move(event);
        }

        private void onTouchEnd(MotionEvent event) {
            int[] res = this.context.result();
            String res_text = stringify_result(res);
            OnResultListener listener = UnlockGraphView.this.onResultListener;
            if (listener != null && res.length == 9) {
                listener.onResult(res, res_text);
            }
            //  Logs.debug("graph.result: " + res_text);
            this.context.reset();
        }
    }

    private void load_layout() {
        MyGraphContext gc = new MyGraphContext();
        Context ctx = this.getContext();
        View view = ViewUtils.loadLayout(R.layout.view_unlock_graph, this);
        view.setOnTouchListener(new MyOnTouchListener(gc));

        SurfaceView sv = view.findViewById(R.id.surface_graph_view);
        SurfaceContextBuilder sc_builder = new SurfaceContextBuilder();
        sc_builder.setView(sv);
        SurfaceContext sc = sc_builder.create();
        ComLifeManager clm = ViewUtils.getLifeManager(ctx);
        sc.setRenderer(new MyGraphRenderer(gc));
        clm.add(sc.getLifecycle().life());
    }

    public OnResultListener getOnResultListener() {
        return onResultListener;
    }

    public void setOnResultListener(OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
    }
}
