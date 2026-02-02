package com.example.secw2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.secw2.Util.UserBean;
import com.example.secw2.Util.UserService;

import java.util.concurrent.atomic.AtomicBoolean;

public class GuestCreateAccountActivity extends BaseEdgeToEdgeActivity {
    private EditText etID;
    private EditText etPassword;
    private EditText etFirstname;
    private EditText etLastname;
    private EditText etEmail;
    private EditText etContact;
    private Button btnBack;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_create_account);

        etID = findViewById(R.id.etID);
        etPassword = findViewById(R.id.etPassword);
        etFirstname = findViewById(R.id.etFirstname);
        etLastname = findViewById(R.id.etLastname);
        etEmail = findViewById(R.id.etEmail);
        etContact = findViewById(R.id.etContact);
        btnBack = findViewById(R.id.btnBack);
        btnRegister = findViewById(R.id.btnRegister);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        btnRegister.setOnClickListener(v -> {
            String id = etID.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String firstname = etFirstname.getText().toString().trim();
            String lastname = etLastname.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String contact = etContact.getText().toString().trim();

            if (id.isEmpty()) {
                Toast.makeText(this, "Your ID is empty", Toast.LENGTH_LONG).show();
                return;
            } else if (password.isEmpty()) {
                Toast.makeText(this, "Your password is empty", Toast.LENGTH_LONG).show();
                return;
            } else if (firstname.isEmpty()) {
                Toast.makeText(this, "Your firstname is empty", Toast.LENGTH_LONG).show();
                return;
            } else if (lastname.isEmpty()) {
                Toast.makeText(this, "Your lastname is empty", Toast.LENGTH_LONG).show();
                return;
            } else if (email.isEmpty()) {
                Toast.makeText(this, "Your email is empty", Toast.LENGTH_LONG).show();
                return;
            } else if (contact.isEmpty()) {
                Toast.makeText(this, "Your contact is empty", Toast.LENGTH_LONG).show();
                return;
            } else {
                UserService.getUser(this, id, user -> {
                    if (user != null) {
                        Toast.makeText(this, "ID is Occupied", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }, err -> {
                    UserBean bean = new UserBean();
                    bean.setUsername(id);
                    bean.setPassword(password);
                    bean.setFirstname(firstname);
                    bean.setLastname(lastname);
                    bean.setEmail(email);
                    bean.setContact(contact);
                    bean.setUserType("guest");

                    UserService.createUser(this, bean);
                    Toast.makeText(this, "Registered", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                });
            }
        });
    }
}