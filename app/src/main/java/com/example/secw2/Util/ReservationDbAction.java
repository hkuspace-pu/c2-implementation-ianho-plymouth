package com.example.secw2.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ReservationDbAction {
    private final DbHelper helper;

    public ReservationDbAction(Context ctx) {
        this.helper = new DbHelper(ctx.getApplicationContext());
    }

    public long insert(ReservationBean bean) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("user_id", bean.getUserId());
        cv.put("guest_name", bean.getGuestName());
        cv.put("party_size", bean.getPartySize());
        cv.put("reservation_time", bean.getReservationTime());
        cv.put("status", bean.getStatus());
        cv.put("notes", bean.getNotes());
        cv.put("create_at", bean.getCreateAt());
        cv.put("update_by", bean.getUpdateBy());
        cv.put("update_ts", bean.getUpdateTs());
        return db.insert("reservation", null, cv);
    }

    public int update(ReservationBean bean) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("user_id", bean.getUserId());
        cv.put("guest_name", bean.getGuestName());
        cv.put("party_size", bean.getPartySize());
        cv.put("reservation_time", bean.getReservationTime());
        cv.put("status", bean.getStatus());
        cv.put("notes", bean.getNotes());
        cv.put("update_by", bean.getUpdateBy());
        cv.put("update_ts", bean.getUpdateTs());
        return db.update("reservation", cv, "reservation_id = ?",
                new String[]{String.valueOf(bean.getReservationId())});
    }

    public int updateStatus(int reservationId, int newStatus, String updateBy, long updateTs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("status", newStatus);
        cv.put("update_by", updateBy);
        cv.put("update_ts", updateTs);
        return db.update("reservation", cv, "reservation_id = ?",
                new String[]{String.valueOf(reservationId)});
    }

    public int delete(int reservationId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete("reservation", "reservation_id = ?",
                new String[]{String.valueOf(reservationId)});
    }

    public List<ReservationBean> selectAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<ReservationBean> out = new ArrayList<>();
        try (Cursor c = db.rawQuery("SELECT reservation_id, user_id, guest_name, " +
                "party_size, reservation_time, status, notes, create_at, update_by, " +
                "update_ts FROM reservation ORDER BY reservation_time DESC", null)) {
            while (c.moveToNext()) {
                ReservationBean r = new ReservationBean();
                r.setReservationId(c.getInt(0));
                r.setUserId(c.getString(1));
                r.setGuestName(c.getString(2));
                r.setPartySize(c.getInt(3));
                r.setReservationTime(c.getLong(4));
                r.setStatus(c.getInt(5));
                r.setNotes(c.isNull(6) ? null : c.getString(6));
                r.setCreateAt(c.getLong(7));
                r.setUpdateBy(c.getString(8));
                r.setUpdateTs(c.getLong(9));
                out.add(r);
            }
        }
        return out;
    }

    public List<ReservationBean> selectByUser(String userId) {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<ReservationBean> out = new ArrayList<>();
        try (Cursor c = db.rawQuery("SELECT reservation_id, user_id, guest_name, party_size, reservation_time, status, notes, create_at, update_by, update_ts FROM reservation WHERE user_id = ? ORDER BY reservation_time DESC", new String[]{userId})) {
            while (c.moveToNext()) {
                ReservationBean r = new ReservationBean();
                r.setReservationId(c.getInt(0));
                r.setUserId(c.getString(1));
                r.setGuestName(c.getString(2));
                r.setPartySize(c.getInt(3));
                r.setReservationTime(c.getLong(4));
                r.setStatus(c.getInt(5));
                r.setNotes(c.isNull(6) ? null : c.getString(6));
                r.setCreateAt(c.getLong(7));
                r.setUpdateBy(c.getString(8));
                r.setUpdateTs(c.getLong(9));
                out.add(r);
            }
        }
        return out;
    }
}