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
 * Term Add (Identical to Term Edit, different intent packing/unpacking)
 *
 * StudentScheduler: Designed at API level 26 using Oreo OS
 * @author David Roeske
 */

public class TermAdd extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper; // Database
    EditText termText;  // Text field for term name
    private TextView startTextView;
    private TextView endTextView;
    private ImageButton startDateButton;
    private ImageButton endDateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_add);
        mDatabaseHelper = new DatabaseHelper(this);
        setTitle("Add Term");

        termText = (EditText) findViewById(R.id.termText);
        startTextView = (TextView) findViewById(R.id.startTextView);
        endTextView = (TextView) findViewById(R.id.endTextView);
        startDateButton = (ImageButton) findViewById(R.id.startDateButton);
        endDateButton = (ImageButton) findViewById(R.id.endDateButton);

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
                String whatWasClicked = "termStart";
                String nameEntry = termText.getText().toString();
                String startDateEntry = startTextView.getText().toString();
                String endDateEntry = endTextView.getText().toString();

                Intent intent = new Intent(TermAdd.this, Calendar.class);
                intent.putExtra("INCOMINGINTENT", whatWasClicked);
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
                String whatWasClicked = "termEnd";
                String nameEntry = termText.getText().toString();
                String startDateEntry = startTextView.getText().toString();
                String endDateEntry = endTextView.getText().toString();

                Intent intent = new Intent(TermAdd.this, Calendar.class);
                intent.putExtra("INCOMINGINTENT", whatWasClicked);
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
                Intent intent = new Intent(TermAdd.this, TermList.class);
                intent.putExtra("INCOMINGINTENT", whatWasClicked);
                startActivity(intent);

                String nameEntry = termText.getText().toString();
                String startDateEntry = startTextView.getText().toString();
                String endDateEntry = endTextView.getText().toString();
                long result = mDatabaseHelper.addTermRecord("termName", nameEntry, "startDate", startDateEntry, "endDate", endDateEntry);

                if (result == -1) {
                    Toast.makeText(TermAdd.this, "Failed to add term", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TermAdd.this, "Term created", Toast.LENGTH_SHORT).show();
                }
                finish();
            }

        });

    }
}