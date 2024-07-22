package com.example.studentgrivences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends BaseActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private RadioButton studentRadioButton;
    private RadioButton staffRadioButton;
    private TextView signUpTextView;

    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);

        // Initialize views
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        studentRadioButton = findViewById(R.id.studentRadioButton);
        staffRadioButton = findViewById(R.id.staffRadioButton); // Initialize staff radio button
        signUpTextView = findViewById(R.id.signUpTextView);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Check if the user is already logged in
        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            String userType = sharedPreferences.getString("userType", "");
            if ("student".equals(userType)) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } else if ("staff".equals(userType)) {
                String username = sharedPreferences.getString("username", "");
                Intent intent = new Intent(LoginActivity.this, StaffActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
            finish();
        }

        // Set click listener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String userType = studentRadioButton.isChecked() ? "student" : "staff";

                if (!studentRadioButton.isChecked() && !staffRadioButton.isChecked()) {
                    // Neither student nor staff radio button is checked
                    Toast.makeText(LoginActivity.this, "Please select a user type", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (databaseHelper.checkUser(username, password, userType)) {
                    // Set user as logged in
                    editor.putBoolean("isLoggedIn", true);
                    editor.putString("userType", userType);
                    editor.putString("username", username);
                    editor.apply();

                    // Start the appropriate activity based on the selected radio button
                    if (studentRadioButton.isChecked()) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(LoginActivity.this, StaffActivity.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                    }
                    finish(); // Finish the LoginActivity
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set click listener for sign up text view
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
