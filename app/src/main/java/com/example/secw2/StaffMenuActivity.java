package com.example.secw2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.ComponentActivity;

public class StaffMenuActivity extends ComponentActivity {
    private Button btnBack;
    private Button btnEdit1;
    private Button btnDelete1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_menu);

        btnBack = findViewById(R.id.btnBack);
        btnEdit1 = findViewById(R.id.btnEdit1);
        btnDelete1 = findViewById(R.id.btnDelete1);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, StaffHomeActivity.class);
            startActivity(intent);
            finish();
        });

        btnEdit1.setOnClickListener(v -> {
            Intent intent = new Intent(this, StaffHomeActivity.class);
            startActivity(intent);
            finish();
        });

        btnDelete1.setOnClickListener(v -> {
            Intent intent = new Intent(this, StaffHomeActivity.class);
            startActivity(intent);
            finish();
        });
    }
}