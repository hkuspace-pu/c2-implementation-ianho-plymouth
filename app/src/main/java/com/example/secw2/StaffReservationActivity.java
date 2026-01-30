package com.example.secw2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.ComponentActivity;

public class StaffReservationActivity extends ComponentActivity {
    private Button btnBack;
    private Button btnView1;
    private Button btnDelete1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_reservation);

        btnBack = findViewById(R.id.btnBack);
        btnView1 = findViewById(R.id.btnView1);
        btnDelete1 = findViewById(R.id.btnDelete1);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, StaffHomeActivity.class);
            startActivity(intent);
            finish();
        });

        btnView1.setOnClickListener(v -> {
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