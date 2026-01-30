package com.example.secw2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.ComponentActivity;

public class StaffHomeActivity extends ComponentActivity {
    private Button btnEditMenu;
    private Button btnViewReservation;
    private Button btnCreateAccount;
    private Button btnSetting;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_main);

        btnEditMenu = findViewById(R.id.btnEditMenu);
        btnViewReservation = findViewById(R.id.btnViewReservation);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        btnSetting = findViewById(R.id.btnSetting);
        btnLogout = findViewById(R.id.btnLogout);

        btnEditMenu.setOnClickListener(v -> {
            Intent intent = new Intent(this, StaffMenuActivity.class);
            startActivity(intent);
            finish();
        });

        btnViewReservation.setOnClickListener(v -> {
            Intent intent = new Intent(this, StaffReservationActivity.class);
            startActivity(intent);
            finish();
        });

        btnCreateAccount.setOnClickListener(v -> {
            Intent intent = new Intent(this, StaffCreateAccountActivity.class);
            startActivity(intent);
            finish();
        });

        btnSetting.setOnClickListener(v -> {
            Intent intent = new Intent(this, StaffSettingActivity.class);
            startActivity(intent);
            finish();
        });

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}