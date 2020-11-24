package com.example.studentscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentscheduler.Classes.DatabaseHelper;

/**
 * Course Add (Identical to Course Edit, different intent packing/unpacking)
 *
 * StudentScheduler: Designed at API level 26 using Oreo OS
 * @author David Roeske
 */

public class CourseAdd extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    DatabaseHelper mDatabaseHelper; // Database
    EditText courseText;
    EditText noteText;
    private TextView startTextView;
    private TextView endTextView;
    private ImageButton startDateButton;
    private ImageButton endDateButton;

    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add);
        mDatabaseHelper = new DatabaseHelper(this);
        setTitle("Add Course");

        courseText = (EditText) findViewById(R.id.courseText);
        startTextView = (TextView) findViewById(R.id.startTextView);
        endTextView = (TextView) findViewById(R.id.endTextView);
        startDateButton = (ImageButton) findViewById(R.id.startDateButton);
        endDateButton = (ImageButton) findViewById(R.id.endDateButton);
        noteText = (EditText) findViewById(R.id.noteText);

        Spinner spinner = findViewById(R.id.spinnerStatus);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        configureDoneButton();

        Intent incomingIntent = getIntent();
        String courseName = incomingIntent.getStringExtra("courseName");
        String startDate = incomingIntent.getStringExtra("dateStart");
        String endDate = incomingIntent.getStringExtra("dateEnd");
        String note = incomingIntent.getStringExtra("note");
        courseText.setText(courseName);
        startTextView.setText(startDate);
        endTextView.setText(endDate);
        noteText.setText(note);

        startDateButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String whatWasClicked = "courseStart";
                String nameEntry = courseText.getText().toString();
                String startDateEntry = startTextView.getText().toString();
                String endDateEntry = endTextView.getText().toString();
                String noteEntry = noteText.getText().toString();

                Intent intent = new Intent(CourseAdd.this, Calendar.class);
                intent.putExtra("INCOMINGINTENT", whatWasClicked);
                intent.putExtra("NAME", nameEntry);
                intent.putExtra("START", startDateEntry);
                intent.putExtra("END", endDateEntry);
                intent.putExtra("NOTE", noteEntry);
                startActivity(intent);
                finish();

            }

        }));

        endDateButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String whatWasClicked = "courseEnd";
                String nameEntry = courseText.getText().toString();
                String startDateEntry = startTextView.getText().toString();
                String endDateEntry = endTextView.getText().toString();
                String noteEntry = noteText.getText().toString();

                Intent intent = new Intent(CourseAdd.this, Calendar.class);
                intent.putExtra("INCOMINGINTENT", whatWasClicked);
                intent.putExtra("NAME", nameEntry);
                intent.putExtra("START", startDateEntry);
                intent.putExtra("END", endDateEntry);
                intent.putExtra("NOTE", noteEntry);
                startActivity(intent);
                finish();
            }
        }));
    }

    //***SPINNER SELECTION METHOD***
    @Override
    public void onItemSelected(AdapterView<?> CourseAdd, View view, int position, long id) {
        status = CourseAdd.getItemAtPosition(position).toString();
    }

    //***SPINNER NOTHING SELECTED METHOD***
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //***DONE BUTTON METHOD***
    private void configureDoneButton() {
        Button doneButton = (Button) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String whatWasClicked = "main";
                Intent intent = new Intent(CourseAdd.this, CourseList.class);
                intent.putExtra("INCOMINGINTENT", whatWasClicked);
                startActivity(intent);

                String nameEntry = courseText.getText().toString();
                String statusEntry = status;
                String startDateEntry = startTextView.getText().toString();
                String endDateEntry = endTextView.getText().toString();
                String noteEntry = noteText.getText().toString();
                int foreignKeyEntry = 0;

                long result = mDatabaseHelper.addCourseRecord("courseTitle", nameEntry, "startDate", startDateEntry, "endDate", endDateEntry, "status", statusEntry, "note", noteEntry, "foreignKey", foreignKeyEntry);
                if (result == -1) {
                    Toast.makeText(CourseAdd.this, "Failed to add course", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CourseAdd.this, "Mentor course", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }
}
