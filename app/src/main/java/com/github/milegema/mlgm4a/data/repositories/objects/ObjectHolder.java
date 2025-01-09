package com.github.milegema.mlgm4a.data.repositories.objects;

import com.github.milegema.mlgm4a.data.repositories.blocks.BlockID;

/*****
 * object 是一类特殊的 block, 具有生成后不可改写的特点
 * */
public interface ObjectHolder {

    BlockID id();

    boolean exists();

}
