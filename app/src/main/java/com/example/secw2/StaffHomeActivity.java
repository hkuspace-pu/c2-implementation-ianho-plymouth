package com.example.secw2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.secw2.Util.UserBean;

public class StaffHomeActivity extends BaseEdgeToEdgeActivity {
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

        UserBean user = getIntent().getSerializableExtra("user", UserBean.class);

        btnEditMenu.setOnClickListener(v -> {
            Intent intent = new Intent(this, StaffMenuActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        });

        btnViewReservation.setOnClickListener(v -> {
            Intent intent = new Intent(this, StaffReservationActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        });

        btnCreateAccount.setOnClickListener(v -> {
            Intent intent = new Intent(this, StaffCreateAccountActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        });

        btnSetting.setOnClickListener(v -> {
            Intent intent = new Intent(this, StaffSettingActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        });

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        });
    }
}