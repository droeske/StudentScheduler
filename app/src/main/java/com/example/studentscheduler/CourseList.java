package com.example.studentscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.studentscheduler.Classes.Course;
import com.example.studentscheduler.Classes.DatabaseHelper;
import com.example.studentscheduler.Classes.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * Course List (Used to list all courses, FAB add button to add courses)
 *
 * StudentScheduler: Designed at API level 26 using Oreo OS
 * @author David Roeske
 */

public class CourseList extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    FloatingActionButton addButton;

    ListView myCourseList;
    ArrayList<String> listItem;
    ArrayAdapter adapter;
    private static ArrayList<Course> allCourses;
    private String whereWeCameFrom;
    String packetRetrieved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        setTitle("Courses");
        addButton = (FloatingActionButton) findViewById(R.id.addButton);
        myCourseList = findViewById(R.id.myCourseList);

        mDatabaseHelper = new DatabaseHelper(this);

        listItem = new ArrayList<>();

        allCourses = mDatabaseHelper.readCourseRecords("SELECT * FROM Courses");

        packetRetrieved = getIntent().getExtras().getString("INCOMINGINTENT");

        viewCourseData();


        myCourseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (packetRetrieved.equals("terminfo")) {
                    String courseTitle = myCourseList.getItemAtPosition(i).toString();
                    String startDate = allCourses.get(i).getStartDate().toString();
                    String endDate = allCourses.get(i).getEndDate().toString();
                    String status = allCourses.get(i).getStatus().toString();
                    String note = allCourses.get(i).getNote().toString();
                    int courseID = allCourses.get(i).getCourseId();
                    String courseIdString = Integer.toString(courseID);

                    Bundle recdData = getIntent().getExtras();
                    String termName = recdData.getString("NAMESENT");
                    String termStartDate = recdData.getString("STARTDATESENT");
                    String termEndDate = recdData.getString("ENDDATESENT");
                    int termId = recdData.getInt("ID");

                    String termIdString = Integer.toString(termId);

                    Intent termInfoIntent = new Intent(CourseList.this, TermInfo.class);
                    mDatabaseHelper.addCourseToTerm(termIdString, courseIdString);
                    termInfoIntent.putExtra("ID", termId);
                    termInfoIntent.putExtra("NAME", termName);
                    termInfoIntent.putExtra("START", termStartDate);
                    termInfoIntent.putExtra("END", termEndDate);
                    startActivity(termInfoIntent);
                    finish();
                }
                if (packetRetrieved.equals("main")) {
                    String courseTitle = myCourseList.getItemAtPosition(i).toString();
                    String startDate = allCourses.get(i).getStartDate().toString();
                    String endDate = allCourses.get(i).getEndDate().toString();
                    String status = allCourses.get(i).getStatus().toString();
                    String note = allCourses.get(i).getNote().toString();
                    int courseID = allCourses.get(i).getCourseId();

                    Intent courseInfoIntent = new Intent(CourseList.this, CourseInfo.class);
                    courseInfoIntent.putExtra("ID", courseID);
                    courseInfoIntent.putExtra("TITLE", courseTitle);
                    courseInfoIntent.putExtra("START", startDate);
                    courseInfoIntent.putExtra("END", endDate);
                    courseInfoIntent.putExtra("STATUS", status);
                    courseInfoIntent.putExtra("NOTE", note);
                    startActivity(courseInfoIntent);
                    finish();
                }
            }
        });

        configureAddButton();
    }

    //***ADD BUTTON***
    private void configureAddButton() {
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CourseList.this, CourseAdd.class));
                finish();
            }
        });
    }

    //***VIEW COURSE DATA METHOD***
    private void viewCourseData() {
        Cursor cursor = mDatabaseHelper.viewCourseData();
        while (cursor.moveToNext()) {
            listItem.add(cursor.getString(1));
        }
        adapter = new ArrayAdapter<>(this, com.example.studentscheduler.R.layout.mycustomlayout, listItem);
        myCourseList.setAdapter(adapter);
    }

}
