package com.example.secw2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

public class GuestReserveActivity extends ComponentActivity {
    private Button btnBack;
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_reserve);

        btnBack = findViewById(R.id.btnBack);
        btnConfirm = findViewById(R.id.btnConfirm);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, GuestHomeActivity.class);
            startActivity(intent);
            finish();
        });

        btnConfirm.setOnClickListener(v -> {
            Toast.makeText(this, "Reservation Confirmed", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, GuestHomeActivity.class);
            startActivity(intent);
            finish();
        });
    }
}