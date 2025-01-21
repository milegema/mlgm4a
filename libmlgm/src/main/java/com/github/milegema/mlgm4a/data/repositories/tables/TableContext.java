package com.github.milegema.mlgm4a.data.repositories.tables;

public class TableContext {

    private final Table facade;
    private final TableFieldsCache fieldCache;

    private TableName name;
    private Schema owner;
    private Field[] fields; // children
    private FieldName primary; // primary-key
    private Class<?> entityClass;
    private EntityAdapter entityAdapter;
    private IdentityGenerator identityGenerator;

    public TableContext() {
        this.facade = new TableFacade(this);
        this.fieldCache = new TableFieldsCache(this);
    }

    public TableContext(TableContext src) {
        if (src != null) {
            this.name = src.name;
            this.owner = src.owner;
            this.fields = src.fields;
            this.primary = src.primary;
            this.entityAdapter = src.entityAdapter;
            this.entityClass = src.entityClass;
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

    public FieldName getPrimary() {
        return primary;
    }

    public void setPrimary(FieldName primary) {
        this.primary = primary;
    }

    public Field[] getFields() {
        return fields;
    }

    public void setFields(Field[] fields) {
        this.fields = fields;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public EntityAdapter getEntityAdapter() {
        return entityAdapter;
    }

    public void setEntityAdapter(EntityAdapter entityAdapter) {
        this.entityAdapter = entityAdapter;
    }


    public IdentityGenerator getIdentityGenerator() {
        return identityGenerator;
    }

    public void setIdentityGenerator(IdentityGenerator identityGenerator) {
        this.identityGenerator = identityGenerator;
    }
}
