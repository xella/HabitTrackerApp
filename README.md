# HabitTrackerApp

I’ve designed and created the structure of a Habit Tracking app which allows a user to store and track habit over time. 

This project have no UI components.

There exists a contract class that defines name of table and constants.

```
public final class WorkoutContract {

    private WorkoutContract() {}

    public static final class WorkoutEntry implements BaseColumns {

        /** Name of the table */
        public final static String TABLE_NAME = "workouts";

        /** ID of the workout entry */
        public final static String _ID = BaseColumns._ID;

        /** Title of the workout entry */
        public final static String COLUMN_WORKOUT_TITLE = "title";

        /** Comment for the workout */
        public final static String COLUMN_WORKOUT_COMMENT = "comment";

        /** Duration of the workout (minutes) */
        public final static String COLUMN_WORKOUT_DURATION = "duration";

        /** Date of the workout */
        public final static String COLUMN_WORKOUT_DATE = "date";

    }
}
```

There exists a subclass of SQLiteOpenHelper that overrides onCreate() and onUpgrade(). 
```
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
```
There is a single insert method that add different datatypes into the database using a ContentValues object and the insert() method.
```
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
```
There is a single read method that returns a Cursor object.
```
 /**
  * Method for reading the data from the database and store it in a Cursor object
  *
  * @return a Cursor object
  */
  private Cursor readDb() {
    // Create and/or open a database to read from it
    SQLiteDatabase db = mDbHelper.getReadableDatabase();

    String[] projection = {
            WorkoutEntry._ID,
            WorkoutEntry.COLUMN_WORKOUT_TITLE,
            WorkoutEntry.COLUMN_WORKOUT_COMMENT,
            WorkoutEntry.COLUMN_WORKOUT_DURATION,
            WorkoutEntry.COLUMN_WORKOUT_DATE
    };

    return db.query(
            WorkoutEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null );
  }
```
