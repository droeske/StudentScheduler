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
 * Assessment Add (Identical to Assessment Edit, different intent packing/unpacking)
 *
 * StudentScheduler: Designed at API level 26 using Oreo OS
 * @author David Roeske
 */

public class AssessmentAdd extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    DatabaseHelper mDatabaseHelper; // Database
    EditText assessText;
    private TextView dueTextView;
    private ImageButton endDateButton;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_add);
        mDatabaseHelper = new DatabaseHelper(this);
        setTitle("Add Assessment");

        assessText = (EditText) findViewById(R.id.assessText);
        dueTextView = (TextView) findViewById(R.id.dueTextView);
        endDateButton = (ImageButton) findViewById(R.id.endDateButton);

        Spinner spinner = findViewById(R.id.spinnerType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        configureDoneButton();

        Intent incomingIntent = getIntent();
        String assessmentText = incomingIntent.getStringExtra("assessmentText");
        String dueDateText = incomingIntent.getStringExtra("dueDate");
        assessText.setText(assessmentText);
        dueTextView.setText(dueDateText);

        endDateButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String whatWasClicked = "assessmentDate";
                String nameEntry = assessText.getText().toString();

                Intent intent = new Intent(AssessmentAdd.this, Calendar.class);
                intent.putExtra("INCOMINGINTENT", whatWasClicked);
                intent.putExtra("NAME", nameEntry);
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
                Intent intent = new Intent(AssessmentAdd.this, AssessmentList.class);
                intent.putExtra("INCOMINGINTENT", whatWasClicked);
                startActivity(intent);

                String nameEntry = assessText.getText().toString();
                String typeEntry = type;
                //String typeEntry = spinner.getSelectedItem().toString();
                String dueDateEntry = dueTextView.getText().toString();
                int foreignKeyEntry = 0;

                long result = mDatabaseHelper.addAssessmentRecord("assessmentName", nameEntry, "assessmentType", typeEntry, "dueDate", dueDateEntry, "foreignKey", foreignKeyEntry);

                if (result == -1) {
                    Toast.makeText(AssessmentAdd.this, "Failed to add assessment", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AssessmentAdd.this, "Assessment created", Toast.LENGTH_SHORT).show();
                }
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
