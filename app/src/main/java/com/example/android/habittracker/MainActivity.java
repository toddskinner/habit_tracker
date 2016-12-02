package com.example.android.habittracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.habittracker.data.HabitDbHelper;
import com.example.android.habittracker.data.HabitContract.HabitEntry;

public class MainActivity extends AppCompatActivity {

    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new HabitDbHelper(this);
        displayDatabaseInfo();
    }

    @Override
    protected void onStart(){
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo(){

        Cursor cursor = mDbHelper.readAllHabits();

        TextView displayView = (TextView) findViewById(R.id.text_view_habit);

        try {
            displayView.setText("\n\n");
            displayView.append(HabitEntry._ID + " - " +
                    HabitEntry.COLUMN_NAME + " - " +
                    HabitEntry.COLUMN_CATEGORY + " - " +
                    HabitEntry.COLUMN_TARGET + " - " +
                    HabitEntry.COLUMN_DONE + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_NAME);
            int categoryColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_CATEGORY);
            int targetColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_TARGET);
            int doneColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_DONE);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentCategory = cursor.getString(categoryColumnIndex);
                int currentTarget = cursor.getInt(targetColumnIndex);
                int currentDone = cursor.getInt(doneColumnIndex);

                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentCategory + " - " +
                        currentTarget + " - " +
                        currentDone));
            }
        } finally {
            cursor.close();
        }
    }

    private void deleteTable() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(HabitEntry.TABLE_NAME, null, null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Add Habit" menu option
            case R.id.action_add_habit_data:
                Intent intent = new Intent(MainActivity.this, AddHabitActivity.class);
                startActivity(intent);
                return true;

            // Respond to a click on the "Delete All" menu option
            case R.id.action_delete_all_entries:
                deleteTable();
                displayDatabaseInfo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
