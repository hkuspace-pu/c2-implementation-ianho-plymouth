package com.example.secw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    String sql;

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword;
    private TextView tvCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvCreateAccount = findViewById(R.id.tvCreateAccount);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            // String pwd = etPassword.getText().toString().trim();
            // validate email & pwd as needed

            if ("staff".equals(email)) {
                Intent intent = new Intent(this, StaffHomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this, GuestHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvCreateAccount.setOnClickListener(v -> {
            Intent intent = new Intent(this, GuestCreateAccountActivity.class);
            startActivity(intent);
            finish();
        });
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