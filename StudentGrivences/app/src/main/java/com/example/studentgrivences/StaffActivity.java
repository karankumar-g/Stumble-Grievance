package com.example.studentgrivences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class StaffActivity extends AppCompatActivity {

    private Spinner categorySpinner;
    private ScrollView grievancesScrollView;
    private LinearLayout grievancesContainer;
    private Button signOutButton;
    private TextView greetingTextView;
    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_activity);

        String username = getIntent().getStringExtra("username");

        categorySpinner = findViewById(R.id.categorySpinner);
        grievancesScrollView = findViewById(R.id.grievancesScrollView);
        grievancesContainer = findViewById(R.id.grievancesContainer);
        signOutButton = findViewById(R.id.signOutButton);
        greetingTextView = findViewById(R.id.greetingTextView);

        greetingTextView.setText(getString(R.string.greeting_staff, username));

        databaseHelper = new DatabaseHelper(this);

        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        loadGrievances();

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadGrievances();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    private void loadGrievances() {
        String selectedCategory = categorySpinner.getSelectedItem().toString();
        final List<String> grievances = databaseHelper.getGrievancesByCategory(selectedCategory);

        grievancesContainer.removeAllViews();

        for (final String grievance : grievances) {
            View grievanceView = LayoutInflater.from(this).inflate(R.layout.grievance_item, null);

            TextView grievanceTextView = grievanceView.findViewById(R.id.grievanceTextView);
            Button viewButton = grievanceView.findViewById(R.id.viewButton);

            // Display only a portion of the grievance text initially
            final String previewText = grievance.length() > 50 ? grievance.substring(0, 25) + "..." : grievance;
            grievanceTextView.setText(previewText);

            viewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Show full grievance details in the popup window
                    View popupView = LayoutInflater.from(StaffActivity.this).inflate(R.layout.grievance_popup, null);
                    TextView fullGrievanceTextView = popupView.findViewById(R.id.fullGrievanceTextView);
                    Button solveButtonPopup = popupView.findViewById(R.id.solveButton);

                    fullGrievanceTextView.setText(grievance);

                    final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setFocusable(true);

                    solveButtonPopup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            databaseHelper.deleteGrievance(grievance);
                            popupWindow.dismiss();
                            loadGrievances();
                        }
                    });

                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                }
            });

            grievancesContainer.addView(grievanceView);
        }
    }

    private void signOut() {
        editor.clear();
        editor.apply();

        Intent intent = new Intent(StaffActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finishAffinity();
        Toast.makeText(StaffActivity.this, "Signed out successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
