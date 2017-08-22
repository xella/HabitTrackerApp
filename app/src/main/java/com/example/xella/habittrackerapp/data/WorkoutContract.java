package com.example.xella.habittrackerapp.data;

import android.provider.BaseColumns;

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
