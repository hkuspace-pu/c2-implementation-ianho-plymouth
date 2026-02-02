package com.example.secw2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class StaffCreateAccountActivity extends BaseEdgeToEdgeActivity {
    private Button btnBack;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_create_account);

        btnBack = findViewById(R.id.btnBack);
        btnRegister = findViewById(R.id.btnRegister);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, StaffHomeActivity.class);
            startActivity(intent);
            finish();
        });

        btnRegister.setOnClickListener(v -> {
            Toast.makeText(this, "Registered", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}