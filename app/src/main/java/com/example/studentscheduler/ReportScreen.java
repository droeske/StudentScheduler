package com.example.studentscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.studentscheduler.Classes.DatabaseHelper;

import java.util.ArrayList;
/**
 * Report Screen
 *
 * StudentScheduler: Designed at API level 26 using Oreo OS
 * @author David Roeske
 */
public class ReportScreen extends AppCompatActivity {
    DatabaseHelper mDatabaseHelper;
    ArrayList<String> listItem;
    String titleText = "";
    String footerText = "";

    TextView reportTitleView;
    TextView reportFooterView;
    TextView reportInfoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_screen);
        setTitle("Generate Reports");
        reportTitleView = (TextView) findViewById(R.id.searchTitleView);
        reportFooterView = (TextView) findViewById(R.id.searchFooterView);
        reportInfoView = (TextView) findViewById(R.id.searchInfoView);
        mDatabaseHelper = new DatabaseHelper(this);

        configureTermButton();
        configureCourseButton();
        configureAssessmentButton();
    }

    private void configureTermButton() {
        Button termReportButton = (Button) findViewById(R.id.termReportButton);
        termReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleText = String.format("%-30s %-35s %-35s%n", "Term Name", "Start Date", "End Date");
                reportTitleView.setText(titleText);

                long termCountLong = mDatabaseHelper.getTermCount();
                footerText = " Total terms:  " + Long.toString(termCountLong);
                reportFooterView.setText(footerText);

                listItem = new ArrayList<>();
                viewTermData();
                int nextRow = 0;
                String output = "";
                for (int i = 0; i <= termCountLong -1; i++) {

                    output = output + String.format("%-35s %-33s %-30s", listItem.get(nextRow), listItem.get(nextRow+1), listItem.get(nextRow+2));
                    output = output + "\n";
                    nextRow = nextRow + 3;
                }
                reportInfoView.setText(output);
            }
        });
    }

    private void configureCourseButton() {
        Button courseReportButton = (Button) findViewById(R.id.courseReportButton);
        courseReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleText = String.format("%-25s %-20s %-20s %-20s%n", "Course Title", "Start Date", "End Date", "Status");
                reportTitleView.setText(titleText);

                long courseCountLong = mDatabaseHelper.getCourseCount();
                footerText = " Total courses:  " + Long.toString(courseCountLong);
                reportFooterView.setText(footerText);

                listItem = new ArrayList<>();
                viewCourseData();
                int nextRow = 0;
                String output = "";
                for (int i = 0; i <= courseCountLong -1; i++) {

                    output = output + String.format("%-28s %-16s %-16s %-1s", listItem.get(nextRow), listItem.get(nextRow+1),
                            listItem.get(nextRow+2), listItem.get(nextRow+3));
                    output = output + "\n";
                    nextRow = nextRow + 4;
                }
                reportInfoView.setText(output);
            }
        });
    }

    private void configureAssessmentButton() {
        Button assessmentReportButton = (Button) findViewById(R.id.assessmentReportButton);
        assessmentReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleText = String.format("%-25s %-25s %-25s%n", "Assessment", "Type", "Due Date");
                reportTitleView.setText(titleText);

                long assessmentCountLong = mDatabaseHelper.getAssessmentCount();
                footerText = " Total assessments:  " + Long.toString(assessmentCountLong);
                reportFooterView.setText(footerText);

                listItem = new ArrayList<>();
                viewAssessmentData();
                int nextRow = 0;
                String output = "";
                for (int i = 0; i <= assessmentCountLong -1; i++) {

                    output = output + String.format("%-28s %-30s %-17s", listItem.get(nextRow), listItem.get(nextRow+1), listItem.get(nextRow+2));
                    output = output + "\n";
                    nextRow = nextRow + 3;
                }
                reportInfoView.setText(output);
            }
        });
    }

    private void viewTermData() {
        Cursor cursor = mDatabaseHelper.viewTermData();
        while (cursor.moveToNext()) {
            listItem.add(cursor.getString(1));
            listItem.add(cursor.getString(2));
            listItem.add(cursor.getString(3));
        }
    }

    private void viewCourseData() {
        Cursor cursor = mDatabaseHelper.viewCourseData();
        while (cursor.moveToNext()) {
            listItem.add(cursor.getString(1));
            listItem.add(cursor.getString(2));
            listItem.add(cursor.getString(3));
            listItem.add(cursor.getString(4));
        }
    }
    private void viewAssessmentData() {
        Cursor cursor = mDatabaseHelper.viewAssessmentData();
        while (cursor.moveToNext()) {
            listItem.add(cursor.getString(1));
            listItem.add(cursor.getString(2));
            listItem.add(cursor.getString(3));
        }
    }

}


