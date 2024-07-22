package com.example.studentgrivences;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name
    private static final String DATABASE_NAME = "student_grievances.db";

    // Table name for users
    private static final String TABLE_USERS = "users";
    // Columns for users table
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_USERTYPE = "user_type";

    // Table name for grievances
    private static final String TABLE_GRIEVANCES = "grievances";
    // Columns for grievances table
    private static final String COLUMN_GRIEVANCE_ID = "grievance_id";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_GRIEVANCE = "grievance";

    // Table name for students
    private static final String TABLE_STUDENTS = "students";
    // Columns for students table
    private static final String COLUMN_STUDENT_ID = "student_id";
    private static final String COLUMN_STUDENT_USERNAME = "username";
    private static final String COLUMN_STUDENT_PASSWORD = "password";

    // Table name for staff
    private static final String TABLE_STAFF = "staff";
    // Columns for staff table
    private static final String COLUMN_STAFF_ID = "staff_id";
    private static final String COLUMN_STAFF_USERNAME = "username";
    private static final String COLUMN_STAFF_PASSWORD = "password";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    // onCreate method
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table for users
        String createUserTableQuery = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_USERTYPE + " TEXT)";
        db.execSQL(createUserTableQuery);

        // Create table for grievances
        String createGrievanceTableQuery = "CREATE TABLE " + TABLE_GRIEVANCES + " (" +
                COLUMN_GRIEVANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_GRIEVANCE + " TEXT)";
        db.execSQL(createGrievanceTableQuery);

        // Create table for students
        String createStudentTableQuery = "CREATE TABLE " + TABLE_STUDENTS + " (" +
                COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_STUDENT_USERNAME + " TEXT, " +
                COLUMN_STUDENT_PASSWORD + " TEXT)";
        db.execSQL(createStudentTableQuery);

        // Create table for staff
        String createStaffTableQuery = "CREATE TABLE " + TABLE_STAFF + " (" +
                COLUMN_STAFF_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_STAFF_USERNAME + " TEXT, " +
                COLUMN_STAFF_PASSWORD + " TEXT)";
        db.execSQL(createStaffTableQuery);

        // Insert default staff details into the staff table
        ContentValues staffValues = new ContentValues();
        staffValues.put(COLUMN_STAFF_USERNAME, "Karan");
        staffValues.put(COLUMN_STAFF_PASSWORD, "123456");
        db.insert(TABLE_STAFF, null, staffValues);

        staffValues.clear();
        staffValues.put(COLUMN_STAFF_USERNAME, "Aqhib");
        staffValues.put(COLUMN_STAFF_PASSWORD, "123456");
        db.insert(TABLE_STAFF, null, staffValues);

    }

    // onUpgrade method
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRIEVANCES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STAFF);
        onCreate(db);
    }

    // Method to add a new user
    public boolean addUser(String username, String password, String userType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_USERTYPE, userType);

        long result = db.insert(TABLE_USERS, null, contentValues);
        return result != -1; // Returns true if successful, false otherwise
    }

    // Method to add a new student
    public boolean addStudent(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STUDENT_USERNAME, username);
        contentValues.put(COLUMN_STUDENT_PASSWORD, password);

        long result = db.insert(TABLE_STUDENTS, null, contentValues);
        return result != -1; // Returns true if successful, false otherwise
    }

    // Method to check if a user exists (student or staff)
    public boolean checkUser(String username, String password, String userType) {
        SQLiteDatabase db = this.getReadableDatabase();
        String tableName = (userType.equals("student")) ? TABLE_STUDENTS : TABLE_STAFF;
        String[] columns = {COLUMN_STUDENT_USERNAME, COLUMN_STUDENT_PASSWORD};
        String selection = COLUMN_STUDENT_USERNAME + " = ? AND " + COLUMN_STUDENT_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(tableName, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    // Method to add a grievance with its category
    public boolean addGrievance(String category, String grievance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_GRIEVANCE, grievance);
        long result = db.insert(TABLE_GRIEVANCES, null, values);
        db.close(); // Close the database connection
        return result != -1;
    }

    // Method to retrieve grievances by category
    public List<String> getGrievancesByCategory(String category) {
        List<String> grievances = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_GRIEVANCE + " FROM " + TABLE_GRIEVANCES + " WHERE " + COLUMN_CATEGORY + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{category});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String grievance = cursor.getString(cursor.getColumnIndex(COLUMN_GRIEVANCE));
                grievances.add(grievance);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return grievances;
    }

// Method to delete a grievance
public void deleteGrievance(String grievanceText) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GRIEVANCES, COLUMN_GRIEVANCE + "=?", new String[]{grievanceText});
        db.close();
    }
}