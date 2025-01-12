package com.github.milegema.mlgm4a.errors;

import com.github.milegema.mlgm4a.logs.Logs;

public class NoThrowErrorFilter implements ErrorFilter {

    @Override
    public Throwable doFilter(Throwable err) {
        if (err != null) {
            Logs.error(this + ".handle_error: ", err);
        }
        return null;
    }

}
