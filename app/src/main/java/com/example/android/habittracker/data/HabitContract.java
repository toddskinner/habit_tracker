package com.example.android.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by toddskinner on 12/1/16.
 */

public class HabitContract {
    public static abstract class HabitEntry implements BaseColumns {
        public static final String TABLE_NAME = "habits";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_TARGET = "target";
        public static final String COLUMN_DONE = "done";

        /**
         * Possible values for the category of the habit.
         */
        public static final int CATEGORY_DIET = 0;
        public static final int CATEGORY_FAMILY = 1;
        public static final int CATEGORY_EXERCISE = 2;
        public static final int CATEGORY_CAREER = 3;
        public static final int CATEGORY_MISC = 4;
    }
}
