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
 * Assessment Edit (Identical to Assessment Add, different intent packing/unpacking)
 *
 * StudentScheduler: Designed at API level 26 using Oreo OS
 * @author David Roeske
 */

public class AssessmentEdit extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    DatabaseHelper mDatabaseHelper; // Database
    EditText assessText;
    private TextView dueTextView;
    private ImageButton endDateButton;
    String type;
    public int assessment;
    public int assessmentKey;
    public String assessmentString;
    public String assessmentKeyString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_edit);
        mDatabaseHelper = new DatabaseHelper(this);
        setTitle("Edit Assessment");

        assessText = (EditText) findViewById(R.id.assessText);
        dueTextView = (TextView) findViewById(R.id.dueTextView);
        endDateButton = (ImageButton) findViewById(R.id.endDateButton);

        Bundle recdData = getIntent().getExtras();
        String nameVal = recdData.getString("NAME");
        String startVal = recdData.getString("START");
        String endVal = recdData.getString("END");
        int assessmentGotID = recdData.getInt("ID");
        int assessmentGotKey = recdData.getInt("KEY");

        assessment = assessmentGotID;
        assessmentKey = assessmentGotKey;
        assessmentString = Integer.toString(assessment);
        assessmentKeyString = Integer.toString(assessmentKey);

        Intent incomingIntent = getIntent();
        String name = incomingIntent.getStringExtra("name");
        String dueDateText = incomingIntent.getStringExtra("duedate");
        assessText.setText(name);
        dueTextView.setText(dueDateText);

        Spinner spinner = findViewById(R.id.spinnerType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        configureDoneButton();

        endDateButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String whatWasClicked = "assessmentDate_Edit";
                String nameEntry = assessText.getText().toString();
                String dueDate = dueTextView.getText().toString();
                Intent intent = new Intent(AssessmentEdit.this, Calendar.class);
                intent.putExtra("ID", assessment);
                intent.putExtra("INCOMINGINTENT", whatWasClicked);
                intent.putExtra("NAME", nameEntry);
                intent.putExtra("DUEDATE", dueDate);
                startActivity(intent);
                finish();

            }

        }));
    }

    //***DONE BUTTON METHOD***
    private void configureDoneButton() {
        Button doneButton = (Button) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String whatWasClicked = "main";
                Intent intent = new Intent(AssessmentEdit.this, AssessmentList.class);
                intent.putExtra("INCOMINGINTENT", whatWasClicked);
                startActivity(intent);

                String nameEntry = assessText.getText().toString();
                String typeEntry = type;
                String dueDateEntry = dueTextView.getText().toString();

                mDatabaseHelper.editAssessmentData(nameEntry, typeEntry, dueDateEntry, assessmentKeyString, assessmentString);
                finish();
            }
        });
    }

    //***SPINNER SELECTION METHOD***
    @Override
    public void onItemSelected(AdapterView<?> AssessmentAdd, View view, int position, long id) {
        type = AssessmentAdd.getItemAtPosition(position).toString();
    }

    //***SPINNER NOTHING SELECTED METHOD***
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
