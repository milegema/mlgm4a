package com.github.milegema.mlgm4a.classes.domains;

import com.github.milegema.mlgm4a.data.entities.DomainEntity;

import java.util.ArrayList;
import java.util.List;

public class DomainListHolder {

    private List<DomainEntity> list;

    public DomainListHolder() {
        this.list = new ArrayList<>();
    }

    public DomainListHolder(List<DomainEntity> src) {
        if (src == null) {
            src = new ArrayList<>();
        }
        this.list = src;
    }

    public List<DomainEntity> getList() {
        return list;
    }

    public void setList(List<DomainEntity> src) {
        if (src == null) {
            src = new ArrayList<>();
        }
        this.list = src;
    }
}
