package com.example.secw2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.secw2.Util.UserBean;
import com.example.secw2.Util.UserService;

public class StaffSettingActivity extends BaseEdgeToEdgeActivity {
    private EditText etID;
    private EditText etPassword;
    private EditText etFirstname;
    private EditText etLastname;
    private EditText etEmail;
    private EditText etContact;
    private Button btnBack;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_setting);

        btnBack = findViewById(R.id.btnBack);
        btnUpdate = findViewById(R.id.btnUpdate);
        etID = findViewById(R.id.etID);
        etPassword = findViewById(R.id.etPassword);
        etFirstname = findViewById(R.id.etFirstname);
        etLastname = findViewById(R.id.etLastname);
        etEmail = findViewById(R.id.etEmail);
        etContact = findViewById(R.id.etContact);

        UserBean user = getIntent().getSerializableExtra("user", UserBean.class);
        etID.setText(user.getUsername());
        etPassword.setText(user.getPassword());
        etFirstname.setText(user.getFirstname());
        etLastname.setText(user.getLastname());
        etEmail.setText(user.getEmail());
        etContact.setText(user.getContact());

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, StaffHomeActivity.class);
            startActivity(intent);
            finish();
        });

        btnUpdate.setOnClickListener(v -> {
            String id = etID.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String firstname = etFirstname.getText().toString().trim();
            String lastname = etLastname.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String contact = etContact.getText().toString().trim();

            if (password.isEmpty()) {
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
                user.setPassword(password);
                user.setFirstname(firstname);
                user.setLastname(lastname);
                user.setEmail(email);
                user.setContact(contact);
                UserService.updateUser(this, id, user);

                Toast.makeText(this, "Update Success", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, StaffHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}