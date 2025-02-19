package com.github.milegema.mlgm4a.data.databases;

/****
 * AutoCommitter 为 调用者 提供默认的 DB, 并在 finish 时自动提交
 * */
public final class AutoCommitter {

    private DB db1;

    public AutoCommitter() {
    }

    public DB init(DB db, DatabaseAgent agent) {
        if (db == null) {
            db = agent.db(db);
            this.db1 = db;
        }
        return db;
    }

    public void finish() {
        if (db1 == null) return;
        db1.commit();
    }
}
