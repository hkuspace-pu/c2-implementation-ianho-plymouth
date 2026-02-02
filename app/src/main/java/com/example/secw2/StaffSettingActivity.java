package com.example.secw2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class StaffSettingActivity extends BaseEdgeToEdgeActivity {
    private Button btnBack;
    private Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_setting);

        btnBack = findViewById(R.id.btnBack);
        btnReset = findViewById(R.id.btnReset);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, StaffHomeActivity.class);
            startActivity(intent);
            finish();
        });

        btnReset.setOnClickListener(v -> {
            Toast.makeText(this, "Reset Password Success", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, StaffHomeActivity.class);
            startActivity(intent);
            finish();
        });
    }
}