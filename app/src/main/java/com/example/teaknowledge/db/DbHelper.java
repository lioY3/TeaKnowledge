package com.example.teaknowledge.db;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

public class DbHelper {
    private static final DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
            .setDbName("database.db")
            .setDbVersion(1);
    private static DbManager db;

    public static DbManager getDbManager() {
        if (db == null) {
            try {
                db = x.getDb(daoConfig);
            } catch (DbException e) {
                db = null;
                e.printStackTrace();
            }
        }
        return db;
    }
}

