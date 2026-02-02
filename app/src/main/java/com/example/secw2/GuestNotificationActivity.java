package com.example.secw2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class GuestNotificationActivity extends BaseEdgeToEdgeActivity {
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_notification);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, GuestHomeActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
