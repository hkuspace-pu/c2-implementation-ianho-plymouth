package com.example.secw2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

public class GuestSettingActivity extends ComponentActivity {
    private Button btnBack;
    private Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_setting);

        btnBack = findViewById(R.id.btnBack);
        btnReset = findViewById(R.id.btnReset);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, GuestHomeActivity.class);
            startActivity(intent);
            finish();
        });

        btnReset.setOnClickListener(v -> {
            Toast.makeText(this, "Reset Password Success", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, GuestHomeActivity.class);
            startActivity(intent);
            finish();
        });
    }
}