package com.example.studentscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentscheduler.Classes.DatabaseHelper;
import com.example.studentscheduler.Classes.Term;
import com.example.studentscheduler.Classes.Course;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * Term Info (Displays all held information for chosen term)
 *
 * StudentScheduler: Designed at API level 26 using Oreo OS
 * @author David Roeske
 */

public class TermInfo extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper; // Database
    private TextView termName;
    private TextView startDate;
    private TextView endDate;
    public int term;
    public String nameForDelete;

    ListView myCourseList;
    ArrayList<String> listItem;
    ArrayAdapter adapter;
    private static ArrayList<Course> allCourses;
    String nameToSend;
    String sDateToSend;
    String eDateToSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_info);
        mDatabaseHelper = new DatabaseHelper(this);
        setTitle("Term Information");

        myCourseList = findViewById(R.id.myCourseList);
        termName = (TextView) findViewById(R.id.termName);
        startDate = (TextView) findViewById(R.id.startDate);
        endDate = (TextView) findViewById(R.id.endDate);

        //UNPACKING
        Bundle recdData = getIntent().getExtras();
        String nameVal = recdData.getString("NAME");
        String startVal = recdData.getString("START");
        String endVal = recdData.getString("END");
        int termGotID = recdData.getInt("ID");
        nameToSend = nameVal;
        sDateToSend = startVal;
        eDateToSend = endVal;
        nameForDelete = nameVal;
        term = termGotID;
        //SETTING UNPACKED VALUES
        termName.setText(nameVal);
        startDate.setText(startVal);
        endDate.setText(endVal);

        listItem = new ArrayList<>();

        viewCourseWhereData();

        allCourses = mDatabaseHelper.readCourseRecords("SELECT * FROM Courses");

        myCourseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String courseTitle = myCourseList.getItemAtPosition(i).toString();
                String startDate = allCourses.get(i).getStartDate().toString();
                String endDate = allCourses.get(i).getEndDate().toString();
                String status = allCourses.get(i).getStatus().toString();
                String note = allCourses.get(i).getNote().toString();
                int courseID = allCourses.get(i).getCourseId();

                Intent courseInfoIntent = new Intent(TermInfo.this, CourseInfo.class);
                courseInfoIntent.putExtra("ID", courseID);
                courseInfoIntent.putExtra("TITLE", courseTitle);
                courseInfoIntent.putExtra("START", startDate);
                courseInfoIntent.putExtra("END", endDate);
                courseInfoIntent.putExtra("STATUS", status);
                courseInfoIntent.putExtra("NOTE", note);
                startActivity(courseInfoIntent);
            }

        });


        configureDeleteButton();
        configureAddButton();
        configureEditButton();
    }

    //***TERM DELETE BUTTON METHOD***
    private void configureDeleteButton() {
        FloatingActionButton deleteButton = (FloatingActionButton) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog deleteWindowBox = AskOption();
                deleteWindowBox.show();
            }
        });
    }

    //***VIEW COURSE WHERE DATA METHOD***
    private void viewCourseWhereData() {
        String key = Integer.toString(term);
        Cursor cursor = mDatabaseHelper.viewCourseWhereData(key);
            while (cursor.moveToNext()) {
                listItem.add(cursor.getString(1));
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
            myCourseList.setAdapter(adapter);
    }

    //***TERM EDIT BUTTON METHOD***
    private void configureEditButton() {
        FloatingActionButton editButton = (FloatingActionButton) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int termId = term;
                String nameEntry = nameToSend;
                String startDateEntry = sDateToSend;
                String endDateEntry = eDateToSend;

                Intent intent = new Intent(TermInfo.this, TermEdit.class);
                intent.putExtra("ID", termId);
                intent.putExtra("name", nameEntry);
                intent.putExtra("dateStart", startDateEntry);
                intent.putExtra("dateEnd", endDateEntry);
                startActivity(intent);
                finish();
            }
        });
    }



    //***ADD COURSES BUTTON METHOD***
    private void configureAddButton() {
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String whatWasClicked = "terminfo";

                Intent intent = new Intent(TermInfo.this, CourseList.class);
                intent.putExtra("NAMESENT", nameToSend);
                intent.putExtra("STARTDATESENT", sDateToSend);
                intent.putExtra("ENDDATESENT", eDateToSend);
                intent.putExtra("ID", term);
                intent.putExtra("INCOMINGINTENT", whatWasClicked);

                startActivity(intent);

                finish();
            }
        });
    }

    //***DELETE WINDOW CONFIRMATION***
    private AlertDialog AskOption()
    {
        AlertDialog deleteWindow = new AlertDialog.Builder(this)
                .setTitle("Term Delete")
                .setMessage("Are you sure you want to delete " + nameForDelete + "?")

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        Boolean validDelete = false;
                        String termKey = String.valueOf(term);
                        validDelete = mDatabaseHelper.doesTermContainCourse(termKey);
                        if (validDelete) {
                            Toast.makeText(TermInfo.this, "Delete failed: Cannot delete term with courses assigned to it.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(TermInfo.this, TermList.class));
                            finish();
                            dialog.dismiss();
                        }
                        if (!validDelete) {
                            Toast.makeText(TermInfo.this, "Delete successful", Toast.LENGTH_SHORT).show();
                            mDatabaseHelper.deleteRecord("DELETE FROM Terms WHERE termId = " + term);
                            startActivity(new Intent(TermInfo.this, TermList.class));
                            finish();
                            dialog.dismiss();
                        }
                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        return deleteWindow;
    }

}
