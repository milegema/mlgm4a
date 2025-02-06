package com.github.milegema.mlgm4a.data.repositories.tables;

import com.github.milegema.mlgm4a.data.entities.BaseEntity;
import com.github.milegema.mlgm4a.data.ids.EntityID;
import com.github.milegema.mlgm4a.data.ids.GroupID;
import com.github.milegema.mlgm4a.data.ids.LongID;
import com.github.milegema.mlgm4a.data.ids.UUID;
import com.github.milegema.mlgm4a.data.ids.UserID;
import com.github.milegema.mlgm4a.data.properties.PropertyGetter;
import com.github.milegema.mlgm4a.data.properties.PropertyTable;
import com.github.milegema.mlgm4a.data.properties.PropertyTableUtils;
import com.github.milegema.mlgm4a.security.SecurityRandom;
import com.github.milegema.mlgm4a.security.hash.Hash;
import com.github.milegema.mlgm4a.security.hash.HashUtils;
import com.github.milegema.mlgm4a.utils.Time;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DefaultRepositoryDatabase implements DB {

    private final DatabaseContext context;

    public DefaultRepositoryDatabase(DatabaseContext ctx) {
        this.context = ctx;
    }

    private static class MyUUIDGenerator {

        long indexer;

        synchronized long nextIndex() {
            this.indexer++;
            return this.indexer;
        }

        public UUID gen(BaseEntitySetter caller) {

            final char nl = '\n';
            long now = System.currentTimeMillis();
            long index = this.nextIndex();
            long nonce = SecurityRandom.getRandom().nextLong();
            StringBuilder builder = new StringBuilder();

            builder.append(nl).append(now);
            builder.append(nl).append(index);
            builder.append(nl).append(nonce);
            builder.append(nl).append(caller.entity);
            builder.append(nl).append(caller.user);
            builder.append(nl).append(caller.group);
            builder.append(nl).append(caller.id);

            String str = builder.toString();
            byte[] sum = HashUtils.sum(str, Hash.MD5);
            return UUID.create(sum);
        }
    }

    private final static MyUUIDGenerator the_uuid_gen = new MyUUIDGenerator();

    private static class BaseEntitySetter {

        boolean useOwner;
        boolean useCommitter;
        boolean useCreator;
        boolean useCreatedAt;
        boolean useUpdatedAt;
        boolean useDeletedAt;
        boolean useGroup;
        boolean useID;
        boolean useUUID;


        EntityID id;
        UUID uuid;
        BaseEntity entity;
        UserID user;
        GroupID group;
        Time time;


        BaseEntitySetter() {
        }

        void init(EntityID entity_id, EntityContext ec) {

            Object any_entity = ec.getEntity();
            if (any_entity instanceof BaseEntity) {
                this.entity = (BaseEntity) any_entity;
            }

            this.id = entity_id;
            this.time = Time.now();
        }

        void apply() {
            this.tryPrepareUUID();

            if (this.useCommitter && user != null) {
                this.entity.setCommitter(this.user);
            }
            if (this.useCreator && user != null) {
                this.entity.setCreator(this.user);
            }
            if (this.useOwner && user != null) {
                this.entity.setOwner(this.user);
            }
            if (this.useCreatedAt && time != null) {
                this.entity.setCreatedAt(this.time);
            }
            if (this.useUpdatedAt && time != null) {
                this.entity.setUpdatedAt(this.time);
            }
            if (this.useDeletedAt && time != null) {
                this.entity.setDeletedAt(this.time);
            }
            if (this.useGroup && group != null) {
                this.entity.setGroup(this.group);
            }
            if (this.useID && id != null) {
                this.entity.setEntityID(this.id);
            }
            if (this.useUUID && uuid != null) {
                this.entity.setUuid(this.uuid);
            }
        }

        private void tryPrepareUUID() {
            if (this.useUUID && uuid == null) {
                this.uuid = the_uuid_gen.gen(this);
            }
        }
    }


    private TableFileContext getTableFileContext(Class<?> t) {

        Table table = this.context.getSchema().findTableByEntityClass(t);
        Path file = this.context.getTableFileLocator().locateTableFile(table);
        TableFileContext tf_ctx = new TableFileContext();

        tf_ctx.setTable(table);
        tf_ctx.setFile(file);
        tf_ctx.setEntityAdapter(table.getEntityAdapter());
        tf_ctx.setEntityClass(table.getEntityClass());
        tf_ctx.setIdentityGenerator(table.getIdentityGenerator());

        return tf_ctx;
    }

    private boolean isItemExists(EntityContext ec) {
        PropertyNamePrefix pnp = new PropertyNamePrefix(ec);
        String name_created_at = pnp.fullName("created_at"); // 通过 created_at 这个字段来判断
        String name_deleted_at = pnp.fullName("deleted_at"); // 通过 deleted_at 这个字段来判断
        PropertyGetter getter = new PropertyGetter(ec.getProperties());
        getter.setRequired(false);
        long created_at = getter.getLong(name_created_at, 0);
        long deleted_at = getter.getLong(name_deleted_at, 0);
        boolean include_deleted = this.context.isIncludeDeleted();
        if (include_deleted) {
            return (created_at > 0);
        }
        return ((created_at > 0) && (deleted_at == 0));
    }


    private EntityContext prepareEntityContext(TableFileContext tfc, Object entity) {

        final EntityContext ec = new EntityContext();
        IdentityGenerator id_gen = tfc.getIdentityGenerator();
        EntityAdapter adapter = tfc.getEntityAdapter();

        if (id_gen == null) {
            id_gen = new DefaultIdentityGenerator();
        }

        ec.setTable(tfc.getTable());
        ec.setEntity(entity);
        ec.setId(null);
        ec.setAdapter(adapter);
        ec.setIdentityGenerator(id_gen);
        ec.setEntityToProperties(false);

        PropertyTable pt = PropertyTable.Factory.create();
        ec.setPropertiesGS(pt);

        return ec;
    }


    @Override
    public void clearCache() {
        this.context.getIo().clearInputCache();
    }


    private static <T> T createNewEntityInstance(Class<T> type) {
        try {
            return type.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> void query(Query<T> q) {

        PropertyTable pt_in;
        EntityContext ec_in;

        T entity = createNewEntityInstance(q.type);
        final TableFileContext tf_ctx = getTableFileContext(q.type);
        final EntityContext ec_base = this.prepareEntityContext(tf_ctx, entity);
        final DataPropertyTableIO data_pt_io = this.context.getIo();


        // fetch all
        ec_in = new EntityContext(ec_base);
        pt_in = data_pt_io.read(tf_ctx.getFile());
        ec_in.setPropertiesGS(pt_in);

        // make items
        List<EntityID> ids = this.listAllEntityID(ec_in);
        EntityAdapter adapter = ec_in.getAdapter();
        List<T> entities = new ArrayList<>();
        DB.QueryFilter<T> filter = this.prepareFilter(q);

        for (EntityID id : ids) {
            entity = createNewEntityInstance(q.type);
            ec_in.setId(id);
            ec_in.setEntity(entity);
            adapter.convert(ec_in);
            if (filter.accept(entity)) {
                entities.add(entity);
            }
        }

        // sort
        Comparator<T> sorter = this.prepareSorter(q);
        entities.sort(sorter);

        // limit & offset
        if (q.offset >= 0 && q.limit > 0) {
            List<T> tmp = entities;
            entities = new ArrayList<>();
            final int i1 = q.offset;
            final int i2 = q.offset + q.limit;
            final int total = tmp.size();
            for (int i = i1; i < i2 && i < total; i++) {
                entities.add(tmp.get(i));
            }
        }

        q.results = entities;
    }


    private static class MyDeletedItemFilter<T> implements QueryFilter<T> {

        private final QueryFilter<T> next;

        public MyDeletedItemFilter(QueryFilter<T> filter) {
            this.next = filter;
        }

        @Override
        public boolean accept(T ent) {
            if (ent instanceof BaseEntity) {
                BaseEntity be = (BaseEntity) ent;
                Time del_time = be.getDeletedAt();
                if (del_time != null) {
                    if (del_time.milliseconds() > 0) {
                        return false;
                    }
                }
            }
            return this.next.accept(ent);
        }
    }

    private <T> QueryFilter<T> prepareFilter(Query<T> q) {
        boolean include_del = this.context.isIncludeDeleted();
        DB.QueryFilter<T> filter = q.filter;
        if (filter == null) {
            filter = (x) -> true;
        }
        if (!include_del) {
            // add a filter to remove deleted items
            filter = new MyDeletedItemFilter<>(filter);
        }
        return filter;
    }

    private static long prepareSorter_get_long_id_of(Object entity) {
        if (entity == null) {
            return 0;
        }
        if (entity instanceof BaseEntity) {
            BaseEntity be = (BaseEntity) entity;
            return LongID.numberOf(be.getEntityID());
        }
        return 0;
    }

    private <T> Comparator<T> prepareSorter(Query<T> q) {
        Comparator<T> sorter = q.sorter;
        if (sorter == null) {
            // make a default sorter
            sorter = (o1, o2) -> {
                final long n1, n2, diff;
                n1 = prepareSorter_get_long_id_of(o1);
                n2 = prepareSorter_get_long_id_of(o2);
                diff = (n1 - n2);
                if (diff == 0) {
                    return 0;
                }
                return (diff > 0) ? 1 : -1;
            };
        }
        return sorter;
    }

    private List<EntityID> listAllEntityID(EntityContext ec) {
        String prefix = ec.getTable().name() + ".";
        String suffix = "." + ec.getTable().primaryKey().name();
        List<EntityID> dst = new ArrayList<>();
        PropertyTable pt = ec.getProperties();
        String[] names = PropertyTableUtils.listSegmentNames(pt, prefix, suffix);
        for (String name : names) {
            long n = Long.parseLong(name);
            dst.add(new LongID(n));
        }
        return dst;
    }

    @Override
    public <T> T find(EntityID id, Class<T> t) {

        EntityContext ec_in;
        T entity = createNewEntityInstance(t);

        final TableFileContext tf_ctx = getTableFileContext(t);
        final EntityContext ec_base = this.prepareEntityContext(tf_ctx, entity);
        final DataPropertyTableIO data_pt_io = this.context.getIo();

        // fetch
        ec_in = new EntityContext(ec_base);
        PropertyTable pt_in = data_pt_io.read(tf_ctx.getFile());
        ec_in.setPropertiesGS(pt_in);
        ec_in.setId(id);

        // check item exists
        if (!this.isItemExists(ec_in)) {
            throw new TableException("no item with id:" + id + " type:" + t);
        }

        // props to entity
        ec_in.setEntityToProperties(false);
        ec_in.getAdapter().convert(ec_in);

        return entity;
    }

    @Override
    public <T> T create(T entity) {

        final TableFileContext tf_ctx = getTableFileContext(entity.getClass());
        final EntityContext entity_ctx = this.prepareEntityContext(tf_ctx, entity);
        final DataPropertyTableIO data_pt_io = this.context.getIo();

        EntityContext ec_in, ec_out;
        PropertyTable pt_in, pt_out;

        pt_out = PropertyTable.Factory.create();
        ec_out = new EntityContext(entity_ctx);
        ec_out.setEntity(entity);
        ec_out.setPropertiesGS(pt_out);

        // fetch all (for id_gen)
        ec_in = new EntityContext(entity_ctx);
        pt_in = data_pt_io.read(tf_ctx.getFile());
        ec_in.setPropertiesGS(pt_in);

        // gen id
        EntityID id = ec_in.getIdentityGenerator().generateID(ec_in);

        // check item exists
        if (this.isItemExists(ec_in)) {
            String type = entity.getClass().getName();
            throw new TableException("create a existed item, id:" + id + " type:" + type);
        }

        // init entity
        BaseEntitySetter be_setter = new BaseEntitySetter();
        be_setter.init(id, ec_out);
        be_setter.useID = true;
        be_setter.useUUID = true;
        be_setter.useGroup = true;
        be_setter.useOwner = true;
        be_setter.useCreator = true;
        be_setter.useCommitter = true;
        be_setter.useCreatedAt = true;
        be_setter.useUpdatedAt = true;
        be_setter.useDeletedAt = false;
        be_setter.apply();

        // entity to props
        ec_out.setId(id);
        ec_out.setEntityToProperties(true);
        ec_out.getAdapter().convert(ec_out);

        // push (append)
        data_pt_io.write(pt_out, tf_ctx.getFile());

        return entity;
    }


    @Override
    public <T> T update(EntityID id, T entity) {

        final TableFileContext tf_ctx = getTableFileContext(entity.getClass());
        final EntityContext ec_init = this.prepareEntityContext(tf_ctx, entity);
        final DataPropertyTableIO data_pt_io = this.context.getIo();

        PropertyTable pt_in, pt_out;
        EntityContext ec_in, ec_out;
        ec_init.setId(id);


        // fetch all
        pt_in = data_pt_io.read(tf_ctx.getFile());
        ec_in = new EntityContext(ec_init);
        ec_in.setPropertiesGS(pt_in);
        ec_in.setId(id);

        // check item exists
        if (!this.isItemExists(ec_in)) {
            String type = entity.getClass().getName();
            throw new TableException("no item with id:" + id + " type:" + type);
        }

        // update entity
        BaseEntitySetter be_setter = new BaseEntitySetter();
        be_setter.init(id, ec_in);
        be_setter.useCommitter = true;
        be_setter.useUpdatedAt = true;
        be_setter.apply();

        // entity to props
        pt_out = PropertyTable.Factory.create();
        ec_out = new EntityContext(ec_init);
        ec_out.setPropertiesGS(pt_out);
        ec_out.setEntityToProperties(true);
        ec_out.getAdapter().convert(ec_out);

        // append
        data_pt_io.write(pt_out, tf_ctx.getFile());
        return entity;
    }


    @Override
    public void delete(EntityID id, Class<?> type) {

        // fetch
        Object entity = this.find(id, type);
        EntityContext ec;

        final TableFileContext tf_ctx = getTableFileContext(type);
        final EntityContext entity_ctx = this.prepareEntityContext(tf_ctx, entity);
        final DataPropertyTableIO data_pt_io = this.context.getIo();

        entity_ctx.setId(id);


        // init entity
        ec = new EntityContext(entity_ctx);
        ec.setEntity(entity);
        BaseEntitySetter be_setter = new BaseEntitySetter();
        be_setter.init(id, ec);
        be_setter.useID = false;
        be_setter.useUUID = false;
        be_setter.useGroup = false;
        be_setter.useOwner = false;
        be_setter.useCreator = false;
        be_setter.useCommitter = true;
        be_setter.useCreatedAt = false;
        be_setter.useUpdatedAt = true;
        be_setter.useDeletedAt = true;
        be_setter.apply();


        // entity to props
        PropertyTable pt_next = PropertyTable.Factory.create();
        ec = new EntityContext(entity_ctx);
        ec.setEntityToProperties(true);
        ec.setPropertiesGS(pt_next);
        ec.getAdapter().convert(ec);


        // push (append)
        data_pt_io.write(pt_next, tf_ctx.getFile());
    }


    @Override
    public void commit() {
        final DataPropertyTableIO data_pt_io = this.context.getIo();
        data_pt_io.flush();
    }

    @Override
    public DB includeDeleted() {
        DatabaseContext ctx = new DatabaseContext(this.context);
        ctx.setIncludeDeleted(true);
        return ctx.toDB();
    }
}
