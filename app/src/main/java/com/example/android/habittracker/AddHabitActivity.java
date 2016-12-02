package com.example.android.habittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.android.habittracker.data.HabitContract;
import com.example.android.habittracker.data.HabitContract.HabitEntry;
import com.example.android.habittracker.data.HabitDbHelper;

import static java.lang.Integer.parseInt;

/**
 * Created by toddskinner on 12/1/16.
 */

public class AddHabitActivity extends AppCompatActivity {
    /** EditText field to enter the pet's name */
    private EditText mNameEditText;

    /** EditText field to enter the pet's gender */
    private Spinner mCategorySpinner;

    /** EditText field to enter the pet's breed */
    private EditText mTargetPerWeekEditText;

    /** EditText field to enter the pet's weight */
    private EditText mTimesDonePerWeekEditText;

    private HabitDbHelper mDbHelper;

    private int mCategory = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addHabit);

        mNameEditText = (EditText) findViewById(R.id.edit_name);
        mCategorySpinner = (Spinner) findViewById(R.id.spinner_category);
        mTargetPerWeekEditText = (EditText) findViewById(R.id.edit_target);
        mTimesDonePerWeekEditText = (EditText) findViewById(R.id.edit_done);

        mDbHelper = new HabitDbHelper(this);
        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the category that the habit belongs to.
     */
    private void setupSpinner() {
        ArrayAdapter categorySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_category_options, android.R.layout.simple_spinner_item);
        // Specify dropdown layout style - simple list view with 1 item per line
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Apply the adapter to the spinner
        mCategorySpinner.setAdapter(categorySpinnerAdapter);
        // Set the integer mSelected to the constant values
        mCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = (String) adapterView.getItemAtPosition(i);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.category_diet))) {
                        mCategory = HabitEntry.CATEGORY_DIET;
                    } else if (selection.equals(getString(R.string.category_family))) {
                        mCategory = HabitEntry.CATEGORY_FAMILY;
                    } else if (selection.equals(getString(R.string.category_exercise))) {
                        mCategory = HabitEntry.CATEGORY_EXERCISE;
                    } else if (selection.equals(getString(R.string.category_career))) {
                        mCategory = HabitEntry.CATEGORY_CAREER;
                    } else {
                        mCategory = HabitEntry.CATEGORY_MISC;
                    }
                }
            }
            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCategory = 0;
            }
        });
    }

    private void insertHabit(){
        String nameString = mNameEditText.getText().toString().trim();
        int targetInteger = parseInt(mTargetPerWeekEditText.getText().toString().trim());
        int doneInteger = parseInt(mTimesDonePerWeekEditText.getText().toString().trim());

        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_NAME, nameString);
        values.put(HabitEntry.COLUMN_CATEGORY, mCategory);
        values.put(HabitEntry.COLUMN_TARGET, targetInteger);
        values.put(HabitEntry.COLUMN_DONE, doneInteger);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);

        Log.v("EditorActivity", "New row ID " + newRowId);

        if(newRowId != -1){
            Toast.makeText(this, "Habit saved with id: " + newRowId, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error with saving habit", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                //save pet to database
                insertHabit();
                //exit activity
                finish();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
