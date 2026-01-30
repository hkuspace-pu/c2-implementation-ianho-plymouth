package com.example.secw2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    String sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void initialDB() {
        try {
            db = SQLiteDatabase.openDatabase("/data/data/com.example.secw2/restaurantDB", null, SQLiteDatabase.CREATE_IF_NECESSARY);
            //sql = "";
            db.execSQL(sql);
        } catch (Exception e) {
        }
    }
}