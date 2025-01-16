package com.github.milegema.mlgm4a.data.repositories.tables;

public class FieldContext {

    private final Field facade;
    private FieldName name;
    private Table owner;
    private FieldType type;

    private boolean unique;
    private boolean nullable;


    public FieldContext(FieldName _name, Table _owner) {
        this.facade = new FieldFacade(this);
        this.name = _name;
        this.owner = _owner;
    }

    public FieldContext(FieldContext src) {
        this.facade = new FieldFacade(this);
        if (src == null) {
            return;
        }
        this.name = src.name;
        this.owner = src.owner;
    }

    public Field getFacade() {
        return facade;
    }

    public FieldName getName() {
        return name;
    }

    public void setName(FieldName name) {
        this.name = name;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }


    public FieldType getType() {
        return type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    public Table getOwner() {
        return owner;
    }

    public void setOwner(Table owner) {
        this.owner = owner;
    }
}
