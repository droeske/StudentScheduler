package com.example.studentscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentscheduler.Classes.DatabaseHelper;

/**
 * Term Edit (Identical to Term Add, different intent packing/unpacking)
 *
 * StudentScheduler: Designed at API level 26 using Oreo OS
 * @author David Roeske
 */

public class TermEdit extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper; // Database
    EditText termText;  // Text field for term name
    private TextView startTextView;
    private TextView endTextView;
    private ImageButton startDateButton;
    private ImageButton endDateButton;
    public String termId;
    public int termtoSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_edit);
        mDatabaseHelper = new DatabaseHelper(this);
        setTitle("Edit Term");

        termText = (EditText) findViewById(R.id.termText);
        startTextView = (TextView) findViewById(R.id.startTextView);
        endTextView = (TextView) findViewById(R.id.endTextView);
        startDateButton = (ImageButton) findViewById(R.id.startDateButton);
        endDateButton = (ImageButton) findViewById(R.id.endDateButton);

        Bundle recdData = getIntent().getExtras();
        String nameVal = recdData.getString("NAME");
        String startVal = recdData.getString("START");
        String endVal = recdData.getString("END");
        int termGotID = recdData.getInt("ID");
        termtoSend = termGotID;
        termId = Integer.toString(termGotID);

        Intent incomingIntent = getIntent();
        String startDate = incomingIntent.getStringExtra("dateStart");
        String endDate = incomingIntent.getStringExtra("dateEnd");
        String termName = incomingIntent.getStringExtra("name");
        startTextView.setText(startDate);
        endTextView.setText(endDate);
        termText.setText(termName);


        startDateButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String whatWasClicked = "termStart_Edit";
                String nameEntry = termText.getText().toString();
                String startDateEntry = startTextView.getText().toString();
                String endDateEntry = endTextView.getText().toString();

                Intent intent = new Intent(TermEdit.this, Calendar.class);
                intent.putExtra("INCOMINGINTENT", whatWasClicked);
                intent.putExtra("ID", termtoSend);
                intent.putExtra("NAME", nameEntry);
                intent.putExtra("START", startDateEntry);
                intent.putExtra("END", endDateEntry);
                startActivity(intent);
                finish();

            }

        }));

        endDateButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String whatWasClicked = "termEnd_Edit";
                String nameEntry = termText.getText().toString();
                String startDateEntry = startTextView.getText().toString();
                String endDateEntry = endTextView.getText().toString();

                Intent intent = new Intent(TermEdit.this, Calendar.class);
                intent.putExtra("INCOMINGINTENT", whatWasClicked);
                intent.putExtra("ID", termtoSend);
                intent.putExtra("NAME", nameEntry);
                intent.putExtra("START", startDateEntry);
                intent.putExtra("END", endDateEntry);
                startActivity(intent);
                finish();
            }
        }));

        configureDoneButton();
    }

    //***DONE BUTTON METHOD***
    private void configureDoneButton() {
        Button doneButton = (Button) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String whatWasClicked = "main";
                Intent intent = new Intent(TermEdit.this, TermList.class);
                intent.putExtra("INCOMINGINTENT", whatWasClicked);
                startActivity(intent);

                String nameEntry = termText.getText().toString();
                String startDateEntry = startTextView.getText().toString();
                String endDateEntry = endTextView.getText().toString();
                mDatabaseHelper.editTermData(nameEntry, startDateEntry, endDateEntry, termId);
                finish();
            }

        });

    }
}