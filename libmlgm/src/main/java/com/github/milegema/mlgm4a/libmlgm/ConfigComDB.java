package com.github.milegema.mlgm4a.libmlgm;

import com.github.milegema.mlgm4a.components.ComponentHolderBuilder;
import com.github.milegema.mlgm4a.components.ComponentProviderT;
import com.github.milegema.mlgm4a.components.ComponentSetBuilder;
import com.github.milegema.mlgm4a.configurations.Configuration;
import com.github.milegema.mlgm4a.data.entities.AccountEntity;
import com.github.milegema.mlgm4a.data.entities.DomainEntity;
import com.github.milegema.mlgm4a.data.entities.UserEntity;
import com.github.milegema.mlgm4a.data.entities.adapters.AccountEntityAdapter;
import com.github.milegema.mlgm4a.data.entities.adapters.DomainEntityAdapter;
import com.github.milegema.mlgm4a.data.entities.adapters.UserEntityAdapter;
import com.github.milegema.mlgm4a.data.repositories.tables.Field;
import com.github.milegema.mlgm4a.data.repositories.tables.FieldBuilder;
import com.github.milegema.mlgm4a.data.repositories.tables.FieldType;
import com.github.milegema.mlgm4a.data.repositories.tables.Schema;
import com.github.milegema.mlgm4a.data.repositories.tables.SchemaBuilder;
import com.github.milegema.mlgm4a.data.repositories.tables.Table;
import com.github.milegema.mlgm4a.data.repositories.tables.TableBuilder;
import com.github.milegema.mlgm4a.data.repositories.tables.TableName;
import com.github.milegema.mlgm4a.security.AlgorithmProvider;
import com.github.milegema.mlgm4a.security.aes.AesProvider;
import com.github.milegema.mlgm4a.security.hash.MD5Provider;
import com.github.milegema.mlgm4a.security.hash.SHA1Provider;
import com.github.milegema.mlgm4a.security.hash.SHA256Provider;
import com.github.milegema.mlgm4a.security.rsa.RsaProvider;

final class ConfigComDB {

    public static void config_all(Configuration configuration) {
        final ComponentSetBuilder csb = configuration.getComponentSetBuilder();
        final MySchemaBuilder builder = new MySchemaBuilder();

        builder.create();

        config_table_users(csb, builder);
        config_table_domains(csb, builder);
        config_table_accounts(csb, builder);

        config_schema(csb, builder);
    }

    private static class MySchemaBuilder {

        Schema schema;

        Table table_users;
        Table table_domains;
        Table table_accounts;

        void create() {
            final SchemaBuilder schema_builder = new SchemaBuilder();

            config_table_accounts(schema_builder);
            config_table_domains(schema_builder);
            config_table_users(schema_builder);

            schema_builder.setName("milegema");
            Schema schema1 = schema_builder.create();
            this.schema = schema1;

            this.table_accounts = schema1.getTable(new TableName("accounts"));
            this.table_domains = schema1.getTable(new TableName("domains"));
            this.table_users = schema1.getTable(new TableName("users"));
        }

        private static void create_base_fields(TableBuilder table_builder) {
            table_builder.addField("uuid", FieldType.UUID).setUnique(true);
            table_builder.addField("created_at", FieldType.TIMESTAMP);
            table_builder.addField("updated_at", FieldType.TIMESTAMP);
            table_builder.addField("deleted_at", FieldType.TIMESTAMP).setNullable(true);
            table_builder.addField("group", FieldType.GROUP_ID).setNullable(true);
            table_builder.addField("owner", FieldType.USER_ID);
            table_builder.addField("creator", FieldType.USER_ID);
            table_builder.addField("committer", FieldType.USER_ID);
        }


        private static void config_table_accounts(SchemaBuilder schema_builder) {

            TableBuilder table_builder = schema_builder.addTable("accounts");
            table_builder.setEntityInfo(AccountEntity.class, new AccountEntityAdapter());

            // pk
            table_builder.addPrimaryKey("id", FieldType.INT);
            create_base_fields(table_builder);

            // other fields
            table_builder.addField("name", FieldType.STRING);
            table_builder.addField("name_with_domain", FieldType.STRING).setUnique(true);
            table_builder.addField("label", FieldType.STRING).setNullable(true);
            table_builder.addField("description", FieldType.STRING).setNullable(true);
            table_builder.addField("icon", FieldType.URL).setNullable(true);
        }

        private static void config_table_domains(SchemaBuilder schema_builder) {

            TableBuilder table_builder = schema_builder.addTable("domains");
            table_builder.setEntityInfo(DomainEntity.class, new DomainEntityAdapter());

            table_builder.addPrimaryKey("id", FieldType.INT);
            create_base_fields(table_builder);

            table_builder.addField("name", FieldType.STRING).setUnique(true); // domain-name
            table_builder.addField("label", FieldType.STRING).setNullable(true);
            table_builder.addField("description", FieldType.STRING).setNullable(true);
            table_builder.addField("icon", FieldType.URL).setNullable(true);
        }

        private static void config_table_users(SchemaBuilder schema_builder) {

            TableBuilder table_builder = schema_builder.addTable("users");
            table_builder.setEntityInfo(UserEntity.class, new UserEntityAdapter());

            table_builder.addPrimaryKey("id", FieldType.INT);
            create_base_fields(table_builder);

            table_builder.addField("name", FieldType.STRING).setUnique(true); // user-name
            table_builder.addField("display_name", FieldType.STRING).setNullable(true); // aka:nick-name
            table_builder.addField("email", FieldType.STRING).setUnique(true);
            table_builder.addField("avatar", FieldType.URL).setNullable(true);
        }
    }


    static void config_schema(ComponentSetBuilder csb, MySchemaBuilder schema_builder) {
        ComponentProviderT<Schema> provider = new ComponentProviderT<>();
        provider.setFactory(() -> schema_builder.schema);
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addAlias(Schema.class);
        builder.addClass(Schema.class);
    }

    static void config_table_domains(ComponentSetBuilder csb, MySchemaBuilder schema_builder) {
        ComponentProviderT<Table> provider = new ComponentProviderT<>();
        provider.setFactory(() -> schema_builder.table_domains);
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addClass(Table.class);
    }

    static void config_table_users(ComponentSetBuilder csb, MySchemaBuilder schema_builder) {
        ComponentProviderT<Table> provider = new ComponentProviderT<>();
        provider.setFactory(() -> schema_builder.table_users);
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addClass(Table.class);
    }

    static void config_table_accounts(ComponentSetBuilder csb, MySchemaBuilder schema_builder) {
        ComponentProviderT<Table> provider = new ComponentProviderT<>();
        provider.setFactory(() -> schema_builder.table_accounts);
        ComponentHolderBuilder builder = csb.addComponentProvider(provider);
        builder.addClass(Table.class);
    }

}
