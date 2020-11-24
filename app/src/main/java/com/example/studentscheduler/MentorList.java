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

import com.example.studentscheduler.Classes.DatabaseHelper;
import com.example.studentscheduler.Classes.Mentor;
import com.example.studentscheduler.Classes.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * Mentor List (Used to list all mentors, FAB add button to add mentors)
 *
 * StudentScheduler: Designed at API level 26 using Oreo OS
 * @author David Roeske
 */

public class MentorList extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    FloatingActionButton addButton;

    ListView myMentorList;
    ArrayList<String> listItem;
    ArrayAdapter adapter;
    private static ArrayList<Mentor> allMentors;
    private String whereWeCameFrom;
    String packetRetrieved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_list);
        setTitle("Mentors");
        addButton = (FloatingActionButton) findViewById(R.id.addButton);
        myMentorList = findViewById(R.id.myMentorList);

        mDatabaseHelper = new DatabaseHelper(this);

        listItem = new ArrayList<>();

        packetRetrieved = getIntent().getExtras().getString("INCOMINGINTENT");
        viewMentorData();
        allMentors = mDatabaseHelper.readMentorRecords("SELECT * FROM Mentors");

        myMentorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (packetRetrieved.equals("courseinfo")) {
                    String mentorName = myMentorList.getItemAtPosition(i).toString();
                    String phoneNumber = allMentors.get(i).getPhoneNumber().toString();
                    String eMail = allMentors.get(i).geteMail().toString();
                    int mentorID = allMentors.get(i).getMentorId();
                    int keyID = allMentors.get(i).getForeignKey();
                    String mentorIdString = Integer.toString(mentorID);

                    Bundle recdData = getIntent().getExtras();
                    String courseName = recdData.getString("NAMESENT");
                    String courseStartDate = recdData.getString("STARTDATESENT");
                    String courseEndDate = recdData.getString("ENDDATESENT");
                    String courseStatus = recdData.getString("STATUSSENT");
                    String courseNote = recdData.getString("NOTESENT");
                    int courseId = recdData.getInt("ID");

                    String courseIdString = Integer.toString(courseId);

                    Intent addMentorIntent = new Intent(MentorList.this, CourseInfo.class);
                    mDatabaseHelper.addMentorToCourse(courseIdString, mentorIdString);
                    addMentorIntent.putExtra("ID", courseId);
                    addMentorIntent.putExtra("NAME", courseName);
                    addMentorIntent.putExtra("START", courseStartDate);
                    addMentorIntent.putExtra("END", courseEndDate);
                    addMentorIntent.putExtra("STATUS", courseStatus);
                    addMentorIntent.putExtra("NOTE", courseNote);
                    startActivity(addMentorIntent);
                    finish();
                }
                if (packetRetrieved.equals("main")) {
                    String mentorName = myMentorList.getItemAtPosition(i).toString();
                    String phoneNumber = allMentors.get(i).getPhoneNumber().toString();
                    String eMail = allMentors.get(i).geteMail().toString();
                    int mentorID = allMentors.get(i).getMentorId();
                    int keyID = allMentors.get(i).getForeignKey();

                    Intent mentorInfoIntent = new Intent(MentorList.this, MentorInfo.class);
                    mentorInfoIntent.putExtra("ID", mentorID);
                    mentorInfoIntent.putExtra("NAME", mentorName);
                    mentorInfoIntent.putExtra("PHONENUMBER", phoneNumber);
                    mentorInfoIntent.putExtra("EMAIL", eMail);
                    mentorInfoIntent.putExtra("KEY", keyID);
                    startActivity(mentorInfoIntent);
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
                startActivity(new Intent(MentorList.this, MentorAdd.class));
                finish();
            }
        });
    }

    //***VIEW MENTOR DATA METHOD***
    private void viewMentorData() {
        Cursor cursor = mDatabaseHelper.viewMentorData();
        while (cursor.moveToNext()) {
            listItem.add(cursor.getString(1));
        }
        adapter = new ArrayAdapter<>(this, com.example.studentscheduler.R.layout.mycustomlayout, listItem);
        myMentorList.setAdapter(adapter);
    }
}
