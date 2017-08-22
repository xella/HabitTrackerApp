package com.example.xella.habittrackerapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.xella.habittrackerapp.data.WorkoutContract.WorkoutEntry;

public class WorkoutDbHelper extends SQLiteOpenHelper {

    /** Name of the database file */
    private static final String DATABASE_NAME = "habits.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link WorkoutDbHelper}.
     *
     * @param context of the app
     */
    public WorkoutDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the workout table
        String SQL_CREATE_WORKOUT_TABLE =  "CREATE TABLE " + WorkoutEntry.TABLE_NAME + " ("
                + WorkoutEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WorkoutEntry.COLUMN_WORKOUT_TITLE + " TEXT NOT NULL, "
                + WorkoutEntry.COLUMN_WORKOUT_COMMENT + " TEXT, "
                + WorkoutEntry.COLUMN_WORKOUT_DURATION + " INTEGER NOT NULL, "
                + WorkoutEntry.COLUMN_WORKOUT_DATE + " TEXT NOT NULL);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_WORKOUT_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
