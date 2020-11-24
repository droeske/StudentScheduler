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

import com.example.studentscheduler.Classes.Assessment;
import com.example.studentscheduler.Classes.DatabaseHelper;
import com.example.studentscheduler.Classes.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * Assessment List (Used to list all assessments, FAB add button to add assessments)
 *
 * StudentScheduler: Designed at API level 26 using Oreo OS
 * @author David Roeske
 */

public class AssessmentList extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    FloatingActionButton addButton;

    ListView myAssessmentList;
    ArrayList<String> listItem;
    ArrayAdapter adapter;
    private static ArrayList<Assessment> allAssessments;
    private String whereWeCameFrom;
    String packetRetrieved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
        setTitle("Assessments");
        addButton = (FloatingActionButton) findViewById(R.id.addButton);
        myAssessmentList = findViewById(R.id.myAssessmentList);

        mDatabaseHelper = new DatabaseHelper(this);

        listItem = new ArrayList<>();

        packetRetrieved = getIntent().getExtras().getString("INCOMINGINTENT");
        viewAssessmentData();
        allAssessments = mDatabaseHelper.readAssessmentRecords("SELECT * FROM Assessments");

        myAssessmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (packetRetrieved.equals("courseinfo")) {
                    String assessmentName = myAssessmentList.getItemAtPosition(i).toString();
                    String assessmentType = allAssessments.get(i).getAssessmentType().toString();
                    String dueDate = allAssessments.get(i).getDueDate().toString();
                    int assessmentID = allAssessments.get(i).getAssessmentId();
                    int keyID = allAssessments.get(i).getForeignKey();
                    String assessmentIdString = Integer.toString(assessmentID);

                    Bundle recdData = getIntent().getExtras();
                    String courseName = recdData.getString("NAMESENT");
                    String courseStartDate = recdData.getString("STARTDATESENT");
                    String courseEndDate = recdData.getString("ENDDATESENT");
                    String courseStatus = recdData.getString("STATUSSENT");
                    String courseNote = recdData.getString("NOTESENT");
                    int courseId = recdData.getInt("ID");

                    String courseIdString = Integer.toString(courseId);

                    Intent addAssessmentIntent = new Intent(AssessmentList.this, CourseInfo.class);
                    mDatabaseHelper.addAssessmentToCourse(courseIdString, assessmentIdString);
                    addAssessmentIntent.putExtra("ID", courseId);
                    addAssessmentIntent.putExtra("NAME", courseName);
                    addAssessmentIntent.putExtra("START", courseStartDate);
                    addAssessmentIntent.putExtra("END", courseEndDate);
                    addAssessmentIntent.putExtra("STATUS", courseStatus);
                    addAssessmentIntent.putExtra("NOTE", courseNote);
                    startActivity(addAssessmentIntent);
                    finish();
                }
                if (packetRetrieved.equals("main")) {
                    String assessmentName = myAssessmentList.getItemAtPosition(i).toString();
                    String assessmentType = allAssessments.get(i).getAssessmentType().toString();
                    String dueDate = allAssessments.get(i).getDueDate().toString();
                    int assessmentID = allAssessments.get(i).getAssessmentId();
                    int keyID = allAssessments.get(i).getForeignKey();

                    Intent assessmentInfoIntent = new Intent(AssessmentList.this, AssessmentInfo.class);
                    assessmentInfoIntent.putExtra("ID", assessmentID);
                    assessmentInfoIntent.putExtra("NAME", assessmentName);
                    assessmentInfoIntent.putExtra("TYPE", assessmentType);
                    assessmentInfoIntent.putExtra("DUEDATE", dueDate);
                    assessmentInfoIntent.putExtra("KEY", keyID);
                    startActivity(assessmentInfoIntent);
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
                startActivity(new Intent(AssessmentList.this, AssessmentAdd.class));
                finish();
            }
        });
    }

    //***VIEW ASSESSMENT DATA METHOD***
    private void viewAssessmentData() {
        Cursor cursor = mDatabaseHelper.viewAssessmentData();
        while (cursor.moveToNext()) {
            listItem.add(cursor.getString(1));
        }
        adapter = new ArrayAdapter<>(this, com.example.studentscheduler.R.layout.mycustomlayout, listItem);
        myAssessmentList.setAdapter(adapter);

    }
}
