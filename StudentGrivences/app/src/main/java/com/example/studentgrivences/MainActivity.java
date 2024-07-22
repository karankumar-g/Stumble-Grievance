package com.example.studentgrivences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends BaseActivity {

    private Spinner categorySpinner;
    private EditText grievanceEditText;
    private Button submitButton;
    private Button signOutButton;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_page);

        // Initialize views
        categorySpinner = findViewById(R.id.categorySpinner);
        grievanceEditText = findViewById(R.id.grievanceEditText);
        submitButton = findViewById(R.id.submitButton);
        signOutButton = findViewById(R.id.signOutButton);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Set up the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        // Set click listener for submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = categorySpinner.getSelectedItem().toString();
                String grievance = grievanceEditText.getText().toString().trim();

                if (!grievance.isEmpty()) {
                    boolean isInserted = databaseHelper.addGrievance(category, grievance);
                    if (isInserted) {
                        Toast.makeText(MainActivity.this, "Grievance submitted successfully", Toast.LENGTH_SHORT).show();
                        grievanceEditText.setText(""); // Clear the text field after submission
                    } else {
                        Toast.makeText(MainActivity.this, "Error submitting grievance", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please enter your grievance", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set click listener for sign-out button
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform sign-out operation
                signOut();
            }
        });
    }

    private void signOut() {
        // Clear session data
        editor.clear();
        editor.apply();

        // Navigate back to LoginActivity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finishAffinity(); // Finish all activities in the task stack

        // Show sign-out message
        Toast.makeText(MainActivity.this, "Signed out successfully", Toast.LENGTH_SHORT).show();
    }
}
