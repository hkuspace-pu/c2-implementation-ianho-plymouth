package com.example.secw2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class GuestHomeActivity extends BaseEdgeToEdgeActivity {
    private Button btnViewMenu;
    private Button btnReserveNow;
    private Button btnViewReservation;
    private Button btnViewNotification;
    private Button btnSetting;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_main);

        btnViewMenu = findViewById(R.id.btnViewMenu);
        btnReserveNow = findViewById(R.id.btnReserveNow);
        btnViewReservation = findViewById(R.id.btnViewReservation);
        btnViewNotification = findViewById(R.id.btnViewNotification);
        btnSetting = findViewById(R.id.btnSetting);
        btnLogout = findViewById(R.id.btnLogout);

        btnViewMenu.setOnClickListener(v -> {
            Intent intent = new Intent(this, GuestMenuActivity.class);
            startActivity(intent);
            finish();
        });

        btnReserveNow.setOnClickListener(v -> {
            Intent intent = new Intent(this, GuestReserveActivity.class);
            startActivity(intent);
            finish();
        });

        btnViewReservation.setOnClickListener(v -> {
            Intent intent = new Intent(this, GuestReservationActivity.class);
            startActivity(intent);
            finish();
        });

        btnViewNotification.setOnClickListener(v -> {
            Intent intent = new Intent(this, GuestNotificationActivity.class);
            startActivity(intent);
            finish();
        });

        btnSetting.setOnClickListener(v -> {
            Intent intent = new Intent(this, GuestSettingActivity.class);
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