package com.github.milegema.mlgm4a.data.repositories.tables;

public class TableContext {

    private final Table facade;
    private final TableFieldsCache fieldCache;

    private TableName name;
    private Schema owner;
    private Field[] fields; // children
    private Field primary; // primary-key

    private Class<?> entityClass;
    private EntityAdapter entityAdapter;

    public TableContext(TableName _name, Schema _owner) {
        this.facade = new TableFacade(this);
        this.fieldCache = new TableFieldsCache(this);
        this.name = _name;
        this.owner = _owner;
    }

    public TableContext(TableContext src) {
        if (src != null) {
            this.name = src.name;
            this.owner = src.owner;
            this.fields = src.fields;
        }
        this.facade = new TableFacade(this);
        this.fieldCache = new TableFieldsCache(this);
    }


    public Table getFacade() {
        return facade;
    }

    public TableName getName() {
        return name;
    }

    public void setName(TableName name) {
        this.name = name;
    }

    public Schema getOwner() {
        return owner;
    }

    public void setOwner(Schema owner) {
        this.owner = owner;
    }

    public TableFieldsCache getFieldCache() {
        return fieldCache;
    }

    public Field getPrimary() {
        return primary;
    }

    public void setPrimary(Field primary) {
        this.primary = primary;
    }

    public Field[] getFields() {
        return fields;
    }

    public void setFields(Field[] fields) {
        this.fields = fields;
    }
}
