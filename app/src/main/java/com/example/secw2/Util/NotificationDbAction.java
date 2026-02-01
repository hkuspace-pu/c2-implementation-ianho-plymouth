package com.example.secw2.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class NotificationDbAction {
    private final DbHelper helper;

    public NotificationDbAction(Context ctx) {
        this.helper = new DbHelper(ctx.getApplicationContext());
    }

    public long insert(NotificationBean bean) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if (bean.getReservationId() != null) {
            cv.put("reservation_id", bean.getReservationId());
        }
        cv.put("user_id", bean.getUserId());
        cv.put("message", bean.getMessage());
        cv.put("is_read", bean.isRead() ? 1 : 0);
        cv.put("created_at", bean.getCreateAt());
        cv.put("update_by", bean.getUpdateBy());
        cv.put("update_ts", bean.getUpdateTs());
        return db.insert("notification", null, cv);
    }

    public int markRead(int notificationId, boolean read, String updateBy, long updateTs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("is_read", read ? 1 : 0);
        cv.put("update_by", updateBy);
        cv.put("update_ts", updateTs);
        return db.update("notification", cv, "notification_id = ?",
                new String[]{String.valueOf(notificationId)});
    }

    public int delete(int notificationId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete("notification", "notification_id = ?",
                new String[]{String.valueOf(notificationId)});
    }

    // 依使用者列出通知（時間新到舊）
    public List<NotificationBean> listByUser(String userId, int limit) {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<NotificationBean> out = new ArrayList<>();
        try (Cursor c = db.rawQuery(
                "SELECT notification_id, reservation_id, user_id, message, is_read, " +
                        "created_at, update_by, update_ts FROM notification WHERE user_id = ? " +
                        "ORDER BY created_at DESC LIMIT ?",
                new String[]{userId, String.valueOf(limit)})) {
            while (c.moveToNext()) {
                NotificationBean n = new NotificationBean();
                n.setNotificationId(c.getInt(0));
                n.setReservationId(c.isNull(1) ? null : c.getInt(1));
                n.setUserId(c.getString(2));
                n.setMessage(c.getString(3));
                n.setRead(c.getInt(4) != 0);
                n.setCreateAt(c.getLong(5));
                n.setUpdateBy(c.getString(6));
                n.setUpdateTs(c.getLong(7));
                out.add(n);
            }
        }
        return out;
    }
}