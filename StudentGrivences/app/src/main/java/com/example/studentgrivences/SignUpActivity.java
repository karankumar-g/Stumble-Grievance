package com.example.studentgrivences;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends BaseActivity {

    // Views
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button signUpButton;

    // Database Helper
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize views
        usernameEditText = findViewById(R.id.signupUsernameEditText);
        passwordEditText = findViewById(R.id.signupPasswordEditText);
        signUpButton = findViewById(R.id.signUpButton);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Set click listener for sign up button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get entered username and password
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Validate password
                if (!isValidPassword(password)) {
                    // Show error if password is invalid
                    Toast.makeText(SignUpActivity.this, "Password must be at least 8 characters long and contain at least one special character", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Add student to the database
                boolean isInserted = databaseHelper.addStudent(username, password);

                // Show toast message based on insertion status
                if (isInserted) {
                    // If student added successfully
                    Toast.makeText(SignUpActivity.this, "Student registered successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);

                } else {
                    // If student insertion failed
                    Toast.makeText(SignUpActivity.this, "Error registering student", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to validate password
    private boolean isValidPassword(String password) {
        // Password must be at least 8 characters long and contain at least one special character
        return !TextUtils.isEmpty(password) && password.length() >= 8 && !password.matches("[a-zA-Z0-9 ]*");
    }
}
