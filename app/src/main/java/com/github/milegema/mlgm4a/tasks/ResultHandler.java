package com.github.milegema.mlgm4a.tasks;

public interface ResultHandler<T> {

    Result<T> handle(Result<T> res) throws Exception;

}
