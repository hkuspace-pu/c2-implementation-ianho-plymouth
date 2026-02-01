package com.example.secw2.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class NotificationPrefDbAction {
    private final DbHelper helper;

    public NotificationPrefDbAction(Context ctx) {
        this.helper = new DbHelper(ctx.getApplicationContext());
    }

    public long upsert(NotificationPrefBean bean) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("user_id", bean.getUserId());
        cv.put("channel", bean.getChannel());
        cv.put("enabled", bean.isEnabled() ? 1 : 0);
        return db.insertWithOnConflict("notification_prefs", null, cv,
                SQLiteDatabase.CONFLICT_REPLACE);
    }

    public int setEnabled(String userId, String channel, boolean enabled) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("enabled", enabled ? 1 : 0);
        return db.update("notification_prefs", cv, "user_id = ? AND channel = ?",
                new String[]{userId, channel});
    }

    public List<NotificationPrefBean> listByUser(String userId) {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<NotificationPrefBean> out = new ArrayList<>();
        try (Cursor c = db.rawQuery("SELECT user_id, channel, enabled " +
                "FROM notification_prefs WHERE user_id = ?", new String[]{userId})) {
            while (c.moveToNext()) {
                NotificationPrefBean p = new NotificationPrefBean();
                p.setUserId(c.getString(0));
                p.setChannel(c.getString(1));
                p.setEnabled(c.getInt(2) != 0);
                out.add(p);
            }
        }
        return out;
    }

    public int delete(String userId, String channel) {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete("notification_prefs", "user_id = ? AND channel = ?",
                new String[]{userId, channel});
    }
}