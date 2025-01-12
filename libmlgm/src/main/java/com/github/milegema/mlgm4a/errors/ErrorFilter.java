package com.github.milegema.mlgm4a.errors;

public interface ErrorFilter {

    Throwable doFilter(Throwable err);

}
