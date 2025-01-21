package com.github.milegema.mlgm4a.application;

import com.github.milegema.mlgm4a.contexts.ContextAgent;
import com.github.milegema.mlgm4a.contexts.ContextHolder;

public interface MLGM extends ContextAgent {

    @Override
    ContextHolder getContextHolder();

}
