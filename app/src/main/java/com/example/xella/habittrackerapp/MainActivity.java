package com.example.xella.habittrackerapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xella.habittrackerapp.data.WorkoutContract.WorkoutEntry;
import com.example.xella.habittrackerapp.data.WorkoutDbHelper;

public class MainActivity extends AppCompatActivity {

    /** Database helper that will provide us access to the database */
    private WorkoutDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new WorkoutDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Method to display information in the onscreen TextView about the state of
     * the workout database.
     */
    private void displayDatabaseInfo() {

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                WorkoutEntry._ID,
                WorkoutEntry.COLUMN_WORKOUT_TITLE,
                WorkoutEntry.COLUMN_WORKOUT_COMMENT,
                WorkoutEntry.COLUMN_WORKOUT_DURATION,
                WorkoutEntry.COLUMN_WORKOUT_DATE
        };

        Cursor cursor = db.query(
                WorkoutEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        TextView displayView = (TextView) findViewById(R.id.workout_text_view);

        try {
            // Create a the Text View that looks like this:
            //
            // The workout table contains <number of rows in Cursor> entries.
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column.
            displayView.setText("The workout table contains " + cursor.getCount() + " entries.\n\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(WorkoutEntry._ID);
            int titleColumnIndex = cursor.getColumnIndex(WorkoutEntry.COLUMN_WORKOUT_TITLE);
            int commentColumnIndex = cursor.getColumnIndex(WorkoutEntry.COLUMN_WORKOUT_COMMENT);
            int durationColumnIndex = cursor.getColumnIndex(WorkoutEntry.COLUMN_WORKOUT_DURATION);
            int dateColumnIndex = cursor.getColumnIndex(WorkoutEntry.COLUMN_WORKOUT_DATE);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentTitle = cursor.getString(titleColumnIndex);
                String currentComment = cursor.getString(commentColumnIndex);
                int currentDuration = cursor.getInt(durationColumnIndex);
                String currentDate = cursor.getString(dateColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentTitle + " - " +
                        currentComment + " - " +
                        currentDuration + " - " +
                        currentDate));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    /**
     * Helper method to insert hardcoded workout data into the database. For debugging purposes only.
     */
    private void insertDummyWorkout() {

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and new workout's attributes are the values.
        ContentValues values = new ContentValues();
        values.put(WorkoutEntry.COLUMN_WORKOUT_TITLE, "Gym");
        values.put(WorkoutEntry.COLUMN_WORKOUT_COMMENT, "20xSquats, 30xPush-Ups");
        values.put(WorkoutEntry.COLUMN_WORKOUT_DURATION, 45);
        values.put(WorkoutEntry.COLUMN_WORKOUT_DATE, "20 Aug 2017");

        // Insert a new row in the database, returning the ID of that new row.
        long newRowId = db.insert(WorkoutEntry.TABLE_NAME, null, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/main_menu.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertDummyWorkout();
                displayDatabaseInfo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Inserting new workout into the database
     */
    public void insertWorkout(String title, String comment, int duration, String date) {

        //Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WorkoutEntry.COLUMN_WORKOUT_TITLE, title);
        values.put(WorkoutEntry.COLUMN_WORKOUT_COMMENT, comment);
        values.put(WorkoutEntry.COLUMN_WORKOUT_DURATION, duration);
        values.put(WorkoutEntry.COLUMN_WORKOUT_DATE, date);

        // Insert a new row in the database, returning the ID of that new row.
        long newRowId = db.insert(WorkoutEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving workout", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Workout saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }
}
