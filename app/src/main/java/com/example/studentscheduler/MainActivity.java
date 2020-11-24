package com.example.studentscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentscheduler.Classes.DatabaseHelper;

/**
 * Main Activity (Main Screen - offers both portrait and landscape)
 *
 * StudentScheduler: Designed at API level 26 using Oreo OS
 * @author David Roeske
 */

public class MainActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    private SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Student Scheduler");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabaseHelper = new DatabaseHelper(this);
        mDatabaseHelper.createTermTable("Terms");   // Create table
        mDatabaseHelper.createCourseTable("Courses");   // Create table
        mDatabaseHelper.createAssessmentTable("Assessments");   // Create table
        mDatabaseHelper.createMentorTable("Mentors");   // Create table

        refresh(); // Method to refresh count of terms/courses/assessments/mentors
        //Button method calls
        configureQuestionButton();
        configureSearchField();
        configureTermButton();
        configureCourseButton();
        configureAssessmentButton();
        configureMentorButton();
        configureReportButton();

    }


    public void refresh() {
        TextView termCount = (TextView) findViewById(R.id.termCount);
        TextView courseCount = (TextView) findViewById(R.id.courseCount);
        TextView assessmentCount = (TextView) findViewById(R.id.assessmentCount);
        TextView mentorCount = (TextView) findViewById(R.id.mentorCount);

        long termCountLong = mDatabaseHelper.getTermCount();
        long courseCountLong = mDatabaseHelper.getCourseCount();
        long assessmentCountLong = mDatabaseHelper.getAssessmentCount();
        long mentorCountLong = mDatabaseHelper.getMentorCount();

        String termCountHold = String.valueOf(termCountLong);
        String courseCountHold = String.valueOf(courseCountLong);
        String assessmentCountHold = String.valueOf(assessmentCountLong);
        String mentorCountHold = String.valueOf(mentorCountLong);

        termCount.setText(termCountHold);
        courseCount.setText(courseCountHold);
        assessmentCount.setText(assessmentCountHold);
        mentorCount.setText(mentorCountHold);
    }

    @Override
    protected void onResume() {
        refresh();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ellipsis, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.Create_sample_data:
                Toast.makeText(this, "Sample data created", Toast.LENGTH_SHORT).show();
                //Terms
                mDatabaseHelper.insertRecord("INSERT INTO Terms(termName, startDate, endDate) VALUES('Term 1', '10-03-2019', '01-12-2020')");
                mDatabaseHelper.insertRecord("INSERT INTO Terms(termName, startDate, endDate) VALUES('Term 2', '10-03-2019', '01-12-2020')");
                mDatabaseHelper.insertRecord("INSERT INTO Terms(termName, startDate, endDate) VALUES('Term 3', '01-15-2019', '05-12-2020')");
                mDatabaseHelper.insertRecord("INSERT INTO Terms(termName, startDate, endDate) VALUES('Term 4', '05-16-2019', '10-12-2020')");
                mDatabaseHelper.insertRecord("INSERT INTO Terms(termName, startDate, endDate) VALUES('Term 5', '10-26-2019', '04-12-2021')");
                //Courses
                mDatabaseHelper.insertRecord("INSERT INTO Courses(courseTitle, startDate, endDate, status, note, foreignKey) VALUES('c139', '10-05-2019', '11-12-2019', 'In Progress', 'empty note', 0)");
                mDatabaseHelper.insertRecord("INSERT INTO Courses(courseTitle, startDate, endDate, status, note, foreignKey) VALUES('c121', '12-14-2019', '02-19-2020', 'In Progress', 'empty note', 0)");
                mDatabaseHelper.insertRecord("INSERT INTO Courses(courseTitle, startDate, endDate, status, note, foreignKey) VALUES('c291', '05-03-2020', '07-12-2020', 'Dropped', 'empty note', 0)");
                mDatabaseHelper.insertRecord("INSERT INTO Courses(courseTitle, startDate, endDate, status, note, foreignKey) VALUES('c168', '11-23-2020', '12-22-2020', 'In Progress', 'empty note', 0)");
                mDatabaseHelper.insertRecord("INSERT INTO Courses(courseTitle, startDate, endDate, status, note, foreignKey) VALUES('c172', '03-11-2021', '04-12-2021', 'Plan to Take', 'empty note', 0)");
                mDatabaseHelper.insertRecord("INSERT INTO Courses(courseTitle, startDate, endDate, status, note, foreignKey) VALUES('c311', '09-15-2021', '10-01-2021', 'Plan to Take', 'empty note', 2)");
                //Assessments
                mDatabaseHelper.insertRecord("INSERT INTO Assessments(assessmentName, assessmentType, dueDate, foreignKey) VALUES('HP12', 'Objective', '12-22-2019', 0)");
                mDatabaseHelper.insertRecord("INSERT INTO Assessments(assessmentName, assessmentType, dueDate, foreignKey) VALUES('OP11', 'Performance', '01-12-2019', 0)");
                mDatabaseHelper.insertRecord("INSERT INTO Assessments(assessmentName, assessmentType, dueDate, foreignKey) VALUES('ABB1', 'Performance', '05-11-2020', 0)");
                mDatabaseHelper.insertRecord("INSERT INTO Assessments(assessmentName, assessmentType, dueDate, foreignKey) VALUES('MR76', 'Objective', '10-20-2020', 0)");
                mDatabaseHelper.insertRecord("INSERT INTO Assessments(assessmentName, assessmentType, dueDate, foreignKey) VALUES('IIS3', 'Performance', '03-09-2021', 0)");
                mDatabaseHelper.insertRecord("INSERT INTO Assessments(assessmentName, assessmentType, dueDate, foreignKey) VALUES('LOO7', 'Objective', '07-13-2022', 0)");

                //Mentors
                mDatabaseHelper.insertRecord("INSERT INTO Mentors(mentorName, phoneNumber, eMail, foreignKey) VALUES('John Helper', '344-281-2333', 'jhelper@gmail.com', 0)");
                mDatabaseHelper.insertRecord("INSERT INTO Mentors(mentorName, phoneNumber, eMail, foreignKey) VALUES('Sue Joanna', '233-544-4562', 'sjoanna@gmail.com', 0)");
                mDatabaseHelper.insertRecord("INSERT INTO Mentors(mentorName, phoneNumber, eMail, foreignKey) VALUES('Mark Milburt', '766-745-3733', 'mmilburt@gmail.com', 0)");
                mDatabaseHelper.insertRecord("INSERT INTO Mentors(mentorName, phoneNumber, eMail, foreignKey) VALUES('Jane Swanson', '133-234-6345', 'jswanson@gmail.com', 0)");
                mDatabaseHelper.insertRecord("INSERT INTO Mentors(mentorName, phoneNumber, eMail, foreignKey) VALUES('Larry Florts', '822-575-6854', 'lflorts@gmail.com', 0)");


                refresh();
                break;
            case R.id.Delete_all_data:
                Toast.makeText(this, "Database data deleted", Toast.LENGTH_SHORT).show();
                mDatabaseHelper.deleteDatabase();
                refresh();
        }
        return true;
    }

    private void configureQuestionButton() {
        ImageButton questionButton = (ImageButton) findViewById(R.id.questionButton);
        questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog questionWindowBox = AskOption();
                questionWindowBox.show();
            }
        });
    }

    private void configureSearchField() {
        search = (SearchView) findViewById(R.id.searchView);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, SearchScreen.class);
                intent.putExtra("INCOMINGINTENT", query);
                startActivity(intent);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String text) {
                return false;
            }
        });
    }

    private void configureTermButton() {
        Button termButton = (Button) findViewById(R.id.termButton);
        termButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TermList.class));
            }
        });
    }

    private void configureCourseButton() {
        Button courseButton = (Button) findViewById(R.id.courseButton);
        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String whatWasClicked = "main";
                Intent intent = new Intent(MainActivity.this, CourseList.class);
                intent.putExtra("INCOMINGINTENT", whatWasClicked);
                startActivity(intent);
            }
        });
    }

    private void configureAssessmentButton() {
        Button assessmentButton = (Button) findViewById(R.id.assessmentButton);
        assessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String whatWasClicked = "main";
                Intent intent = new Intent(MainActivity.this, AssessmentList.class);
                intent.putExtra("INCOMINGINTENT", whatWasClicked);
                startActivity(intent);
            }
        });
    }

    private void configureMentorButton() {
        Button mentorButton = (Button) findViewById(R.id.mentorButton);
        mentorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String whatWasClicked = "main";
                Intent intent = new Intent(MainActivity.this, MentorList.class);
                intent.putExtra("INCOMINGINTENT", whatWasClicked);
                startActivity(intent);
            }
        });
    }

    private void configureReportButton() {
        ImageButton reportButton = (ImageButton) findViewById(R.id.reportButton);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ReportScreen.class));
            }
        });
    }

    //***QUESTION INFO WINDOW***
    private AlertDialog AskOption()
    {
        AlertDialog questionWindow = new AlertDialog.Builder(this)
                .setTitle("Search Help\n")
                .setIcon(R.drawable.question)
                .setMessage("Search is case sensitive\n\n" +
                        "Can search database by using the following:\n\n" +
                        "   * Term name\n" +
                        "   * Course Title\n" +
                        "   * Assessment name\n\n")
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        return questionWindow;
    }
}
