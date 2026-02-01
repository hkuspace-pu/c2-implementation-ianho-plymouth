package com.example.secw2.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "restaurant.db";
    public static final int DB_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS menu (" +
                "item_id INTEGER PRIMARY KEY," +
                "item_name TEXT NOT NULL," +
                "price REAL NOT NULL CHECK(price >= 0)," +
                "discount_rate INTEGER NOT NULL CHECK(discount_rate >=0 AND discount_rate <= 100)," +
                "image_url TEXT," +
                "available INTEGER NOT NULL DEFAULT 1 CHECK(available IN (0,1))," +
                "update_by TEXT NOT NULL," +
                "update_ts INTEGER NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS reservation (" +
                "reservation_id INTEGER PRIMARY KEY," +
                "user_id TEXT NOT NULL," +
                "guest_name TEXT NOT NULL," +
                "party_size INTEGER NOT NULL CHECK(party_size > 0)," +
                "reservation_time INTEGER NOT NULL," +
                "status INTEGER NOT NULL," +
                "notes TEXT," +
                "create_at INTEGER NOT NULL," +
                "update_by TEXT NOT NULL," +
                "update_ts INTEGER NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS notification (" +
                "notification_id INTEGER PRIMARY KEY," +
                "reservation_id INTEGER," +
                "user_id TEXT," +
                "message TEXT NOT NULL," +
                "is_read INTEGER NOT NULL DEFAULT 0 CHECK(available IN (0,1))," +
                "create_at INTEGER NOT NULL," +
                "update_by TEXT NOT NULL," +
                "update_ts INTEGER NOT NULL," +
                "FOREIGN KEY (reservation_id) REFERENCES reservation(reservation_id) " +
                "ON DELETE SET NULL ON UPDATE CASCADE" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS notification_prefs (" +
                "user_id TEXT NOT NULL," +
                "channel TEXT NOT NULL," +
                "enabled INTEGER NOT NULL DEFAULT 1 CHECK(available IN (0,1))," +
                "PRIMARY KEY (guest_user_id, channel)" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS menu");
        db.execSQL("DROP TABLE IF EXISTS reservation");
        db.execSQL("DROP TABLE IF EXISTS notification");
        db.execSQL("DROP TABLE IF EXISTS notification_prefs");
        onCreate(db);
    }
}