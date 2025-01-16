package com.github.milegema.mlgm4a.configurations;

import com.github.milegema.mlgm4a.data.properties.PropertyTable;

import java.util.ArrayList;
import java.util.List;

public final class ApplicationPropertiesLoader {

    private List<PropertiesHolder> items;

    public ApplicationPropertiesLoader() {
        this.items = new ArrayList<>();
    }

    public interface SourceFunc {
        PropertyTable get(PropertyTable dst);
    }


    public void add(String name, SourceFunc src) {

        PropertyTable pt = PropertyTable.Factory.create();
        PropertiesHolder holder = new PropertiesHolder();

        pt = src.get(pt);
        holder.setProperties(pt);
        holder.setName(name);

        this.items.add(holder);
    }


    public List<PropertiesHolder> loadAll() {
        return new ArrayList<>(this.items);
    }

}
