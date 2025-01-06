package com.github.milegema.mlgm4a.errors;

public class BypassErrorFilter implements ErrorFilter {

    @Override
    public Throwable doFilter(Throwable err) {
        return err;
    }

}
