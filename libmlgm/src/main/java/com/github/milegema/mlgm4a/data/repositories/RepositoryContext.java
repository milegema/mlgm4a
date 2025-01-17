package com.github.milegema.mlgm4a.data.repositories;

import com.github.milegema.mlgm4a.data.files.RepositoryFileContext;
import com.github.milegema.mlgm4a.data.repositories.objects.Objects;
import com.github.milegema.mlgm4a.data.repositories.refs.Refs;
import com.github.milegema.mlgm4a.data.repositories.tables.DB;
import com.github.milegema.mlgm4a.data.repositories.tables.TableManager;
import com.github.milegema.mlgm4a.security.SecretKeyHolder;

import java.nio.file.Path;
import java.security.KeyPair;

import javax.crypto.SecretKey;

public class RepositoryContext {

    // attributes
    private RepositoryAlias alias;
    private RepositoryLayout layout;
    private RepositoryHolder holder;
    private RepositoryFileContext rfc;
    private Path location; // location of repository folder '.milegema'
    private int formatVersion;

    // elements
    private RepositoryConfig config;
    private RepositoryConfigCache configuration;
    private Repository repository;
    private Objects objects;
    private Refs refs;

    private TableManager tables;
    private DB db;


    // keys
    private KeyPair keyPair;
    private SecretKey secretKey;
    private SecretKeyHolder secretKeyHolder;


    public RepositoryContext() {
        this.formatVersion = 1; // current = 1
    }

    public RepositoryContext(RepositoryContext ctx) {

        if (ctx == null) {
            return;
        }

        this.alias = ctx.alias;
        this.layout = ctx.layout;
        this.location = ctx.location;
        this.holder = ctx.holder;
        this.rfc = ctx.rfc;
        this.formatVersion = ctx.formatVersion;

        this.config = ctx.config;
        this.objects = ctx.objects;
        this.refs = ctx.refs;
        this.repository = ctx.repository;
        this.tables = ctx.tables;

        this.keyPair = ctx.keyPair;
        this.secretKey = ctx.secretKey;
        this.secretKeyHolder = ctx.secretKeyHolder;
    }

    public RepositoryAlias getAlias() {
        return alias;
    }

    public void setAlias(RepositoryAlias alias) {
        this.alias = alias;
    }

    public int getFormatVersion() {
        return formatVersion;
    }

    public void setFormatVersion(int formatVersion) {
        this.formatVersion = formatVersion;
    }

    public Path getLocation() {
        return location;
    }

    public void setLocation(Path location) {
        this.location = location;
    }

    public TableManager getTables() {
        return tables;
    }

    public void setTables(TableManager tables) {
        this.tables = tables;
    }

    public DB getDb() {
        return db;
    }

    public void setDb(DB db) {
        this.db = db;
    }

    public Objects getObjects() {
        return objects;
    }

    public void setObjects(Objects objects) {
        this.objects = objects;
    }

    public Refs getRefs() {
        return refs;
    }

    public void setRefs(Refs refs) {
        this.refs = refs;
    }

    public RepositoryHolder getHolder() {
        return holder;
    }

    public void setHolder(RepositoryHolder holder) {
        this.holder = holder;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public RepositoryFileContext getRfc() {
        return rfc;
    }

    public void setRfc(RepositoryFileContext rfc) {
        this.rfc = rfc;
    }

    public SecretKeyHolder getSecretKeyHolder() {
        return secretKeyHolder;
    }

    public void setSecretKeyHolder(SecretKeyHolder secretKeyHolder) {
        this.secretKeyHolder = secretKeyHolder;
    }

    public RepositoryConfig getConfig() {
        return config;
    }

    public void setConfig(RepositoryConfig config) {
        this.config = config;
    }

    public RepositoryConfigCache getConfiguration() {
        return configuration;
    }

    public void setConfiguration(RepositoryConfigCache configuration) {
        this.configuration = configuration;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public RepositoryLayout getLayout() {
        return layout;
    }

    public void setLayout(RepositoryLayout layout) {
        this.layout = layout;
    }
}
