package com.github.milegema.mlgm4a.errors;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import com.github.milegema.mlgm4a.logs.Logs;

public final class Errors {

    public final static int FLAG_LOG = 0x01;
    public final static int FLAG_TOAST = 0x02;
    public final static int FLAG_ALERT = 0x04;

    private Errors() {
    }

    public static void handle(Context ctx, Throwable err) {
        handle(ctx, err, FLAG_LOG | FLAG_TOAST);
    }

    public static void handle(Context ctx, Throwable err, int flags) {

        if (err == null) {
            return;
        }

        if (hasFlag(FLAG_LOG, flags)) {
            Logs.error("error: ", err);
        }
        if (hasFlag(FLAG_TOAST, flags) && (ctx != null)) {
            showToast(ctx, err);
        }
        if (hasFlag(FLAG_ALERT, flags) && (ctx != null)) {
            showAlert(ctx, err);
        }
    }

    private static void showAlert(Context ctx, Throwable err) {
        String text = err.getLocalizedMessage();
        AlertDialog.Builder ab = new AlertDialog.Builder(ctx);
        ab.setTitle("Error").setMessage(err.getLocalizedMessage());
        ab.setNegativeButton("Close", (di, n) -> {
        });
        ab.show();
    }

    private static void showToast(Context ctx, Throwable err) {
        String text = err.getLocalizedMessage();
        Toast.makeText(ctx, text, Toast.LENGTH_LONG).show();
    }

    private static boolean hasFlag(int mask, int flags) {
        return ((mask & flags) != 0);
    }
}
