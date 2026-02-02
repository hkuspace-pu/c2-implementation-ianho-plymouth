package com.example.secw2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.secw2.Util.UserService;

public class MainActivity extends BaseEdgeToEdgeActivity {
    private EditText etID;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword;
    private TextView tvCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etID = findViewById(R.id.etID);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvCreateAccount = findViewById(R.id.tvCreateAccount);

        btnLogin.setOnClickListener(v -> {
            String id = etID.getText().toString().trim();
            String pwd = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(id)) {
                Toast.makeText(this, "Please enter your ID", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(pwd)) {
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
                return;
            }

            UserService.getUser(this, id, user -> {
                if (user == null) {
                    Toast.makeText(this, "ID or Password is Incorrect", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (pwd.equals(user.getPassword())) {
                        Intent intent;
                        if ("staff".equals(user.getUserType())) {
                            intent = new Intent(this, StaffHomeActivity.class);
                        } else {
                            intent = new Intent(this, GuestHomeActivity.class);
                        }
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "ID or Password is Incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
            }, err -> {
                Toast.makeText(this, "ID or Password is Incorrect", Toast.LENGTH_SHORT).show();
            });
        });

        tvCreateAccount.setOnClickListener(v -> {
            Intent intent = new Intent(this, GuestCreateAccountActivity.class);
            startActivity(intent);
            finish();
        });
    }
}