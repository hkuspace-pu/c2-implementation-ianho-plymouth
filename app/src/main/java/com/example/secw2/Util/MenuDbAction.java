package com.example.secw2.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class MenuDbAction {
    private final DbHelper helper;

    public MenuDbAction(Context ctx) {
        this.helper = new DbHelper(ctx.getApplicationContext());
    }

    public long insert(MenuBean bean) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("item_name", bean.getItemName());
        cv.put("price", bean.getPrice());
        cv.put("image_url", bean.getImageUrl());
        cv.put("available", bean.getAvailable() ? 1 : 0);
        cv.put("update_by", bean.getUpdateBy());
        cv.put("update_ts", bean.getUpdateTs());
        return db.insert("menu", null, cv);
    }

    public int update(MenuBean bean) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("item_name", bean.getItemName());
        cv.put("price", bean.getPrice());
        cv.put("image_url", bean.getImageUrl());
        cv.put("available", bean.getAvailable() ? 1 : 0);
        cv.put("update_by", bean.getUpdateBy());
        cv.put("update_ts", bean.getUpdateTs());
        return db.update("menu", cv, "item_id = ?",
                new String[]{String.valueOf(bean.getItemId())});
    }

    public int delete(MenuBean bean) {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete("menu", "item_id = ?",
                new String[]{String.valueOf(bean.getItemId())});
    }

    public List<MenuBean> selectAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<MenuBean> out = new ArrayList<>();
        try (Cursor c = db.rawQuery("SELECT item_id, item_name, price, image_url, " +
                "available, update_by, update_ts FROM menu", null)) {
            while (c.moveToNext()) {
                MenuBean row = new MenuBean();
                row.setItemId(c.getInt(0));
                row.setItemName(c.getString(1));
                row.setPrice(c.getDouble(2));
                row.setImageUrl(c.getString(3));
                row.setAvailable(c.getInt(4) != 0);
                row.setUpdateBy(c.getString(5));
                row.setUpdateTs(c.getLong(6));
                out.add(row);
            }
        }
        return out;
    }
}
