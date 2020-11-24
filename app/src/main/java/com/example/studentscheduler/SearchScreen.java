package com.example.studentscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentscheduler.Classes.DatabaseHelper;

import java.util.ArrayList;
/**
 * Search Screen
 *
 * StudentScheduler: Designed at API level 26 using Oreo OS
 * @author David Roeske
 */
public class SearchScreen extends AppCompatActivity {
    DatabaseHelper mDatabaseHelper;
    ArrayList<String> listItem;     // List for terms
    ArrayList<String> listItem2;    // List for courses
    ArrayList<String> listItem3;    // List for assessments
    TextView searchTitleView;
    TextView searchInfoView;
    String packetRetrieved = "";
    String output = "";
    int trigger = 0;    // Used for if Term found results
    int trigger2 = 0;   // Used for if Course found results
    int trigger3 = 0;   // Used for if Assessments found results
    int count = 0;      // Used for loop when running through term listItem
    int count2 = 0;      // Used for loop when running through course listItem
    int count3 = 0;      // Used for loop when running through assessment listItem

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String title = "Searched for ";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);
        setTitle("Search Results");
        searchTitleView = (TextView) findViewById(R.id.searchTitleView);
        searchInfoView = (TextView) findViewById(R.id.searchInfoView);
        mDatabaseHelper = new DatabaseHelper(this);

        packetRetrieved = getIntent().getExtras().getString("INCOMINGINTENT");

        title = title + packetRetrieved;
        searchTitleView.setText(title);

        listItem = new ArrayList<>();
        listItem2 = new ArrayList<>();
        listItem3 = new ArrayList<>();

        viewTermData();
        viewCourseData();
        viewAssessmentData();

        if (trigger == 1) {
            int nextRow = 0;
            output = output + String.format("%-11s %-15s %-20s %10s", "\nType", "Name", "Start Date", "End Date\n");
            for (int i = 0; i <= count - 1; i++) {
                output = output + "TERM      ";
                output = output + String.format("%-15s %-20s %-20s", listItem.get(nextRow), listItem.get(nextRow + 1), listItem.get(nextRow + 2));
                output = output + "\n";
                nextRow = nextRow + 3;
            }
        }
        if (trigger2 == 1) {
            int nextRow = 0;
            output = output + String.format("%-14s %-12s %-12s %-15s %10s", "\nType", "Title", "Start Date", "End Date", "Status\n");
            for (int i = 0; i <= count2 - 1; i++) {
                output = output + "COURSE      ";
                output = output + String.format("%-10s %-12s %-12s %-10s", listItem2.get(nextRow), listItem2.get(nextRow + 1), listItem2.get(nextRow + 2), listItem2.get(nextRow + 3));
                output = output + "\n";
                nextRow = nextRow + 4;
            }
        }
        if (trigger3 == 1) {
            int nextRow = 0;
            output = output + String.format("%-11s %-10s %-10s %10s", "\nType", "Name", "Form", "Due Date\n");
            for (int i = 0; i <= count3 - 1; i++) {
                output = output + "ASSESS    ";
                output = output + String.format("%-10s %-15s %-10s", listItem3.get(nextRow), listItem3.get(nextRow + 1), listItem3.get(nextRow + 2));
                output = output + "\n";
                nextRow = nextRow + 3;
            }
        } else if (trigger == 0 && trigger2 == 0 && trigger3 == 0){
            output = "Nothing found";
        }
        searchInfoView.setText(output);

    }

    private void viewTermData() {
        Cursor cursor = mDatabaseHelper.viewTermData();
        while (cursor.moveToNext()) {
            if (cursor.getString(1).equals(packetRetrieved)) {
                trigger = 1;
                count = count + 1;
                listItem.add(cursor.getString(1));
                listItem.add(cursor.getString(2));
                listItem.add(cursor.getString(3));
            } else {
            }
        }
    }

    private void viewCourseData() {
        Cursor cursor = mDatabaseHelper.viewCourseData();
        while (cursor.moveToNext()) {
            if (cursor.getString(1).equals(packetRetrieved)) {
                trigger2 = 1;
                count2 = count2 + 1;
                listItem2.add(cursor.getString(1));
                listItem2.add(cursor.getString(2));
                listItem2.add(cursor.getString(3));
                listItem2.add(cursor.getString(4));
            } else {
            }
        }
    }

    private void viewAssessmentData() {
        Cursor cursor = mDatabaseHelper.viewAssessmentData();
        while (cursor.moveToNext()) {
            if (cursor.getString(1).equals(packetRetrieved)) {
                trigger3 = 1;
                count3 = count3 + 1;
                listItem3.add(cursor.getString(1));
                listItem3.add(cursor.getString(2));
                listItem3.add(cursor.getString(3));
            } else {
            }
        }
    }

}
