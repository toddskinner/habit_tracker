package com.example.android.habittracker.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.habittracker.data.HabitContract.HabitEntry;

/**
 * Created by toddskinner on 12/1/16.
 */

public class HabitDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "habits.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + HabitEntry.TABLE_NAME + " (" +
                    HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    HabitEntry.COLUMN_NAME + " TEXT," +
                    HabitEntry.COLUMN_CATEGORY + " INTEGER," +
                    HabitEntry.COLUMN_TARGET + " INTEGER," +
                    HabitEntry.COLUMN_DONE + " INTEGER" + ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + HabitEntry.TABLE_NAME;

    public HabitDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Cursor readAllHabits(){
        // Create and/or open a database to read from it
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = { HabitEntry._ID, HabitEntry.COLUMN_NAME, HabitEntry.COLUMN_CATEGORY, HabitEntry.COLUMN_TARGET, HabitEntry.COLUMN_DONE };
        return db.query(HabitEntry.TABLE_NAME, projection, null, null, null, null, null);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
