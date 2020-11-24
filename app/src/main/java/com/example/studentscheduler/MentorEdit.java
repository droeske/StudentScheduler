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
 * Mentor Edit (Identical to Mentor Add, different intent packing/unpacking)
 *
 * StudentScheduler: Designed at API level 26 using Oreo OS
 * @author David Roeske
 */

public class MentorEdit extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper; // Database
    EditText mentorText;
    EditText phoneNumberText;
    EditText eMailText;
    public int mentor;
    public int mentorKey;
    public String mentorString;
    public String mentorKeyString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_edit);
        mDatabaseHelper = new DatabaseHelper(this);
        setTitle("Edit Mentor");

        mentorText = (EditText) findViewById(R.id.mentorText);
        phoneNumberText = (EditText) findViewById(R.id.phoneNumberText);
        eMailText = (EditText) findViewById(R.id.eMailText);


        Bundle recdData = getIntent().getExtras();
        String nameVal = recdData.getString("NAME");
        String phonenumberVal = recdData.getString("PHONENUMBER");
        String emailVal = recdData.getString("EMAIL");
        int mentorGotID = recdData.getInt("ID");
        int mentorGotKey = recdData.getInt("KEY");

        mentor = mentorGotID;
        mentorKey = mentorGotKey;
        mentorString = Integer.toString(mentor);
        mentorKeyString = Integer.toString(mentorKey);

        mentorText.setText(nameVal);
        phoneNumberText.setText(phonenumberVal);
        eMailText.setText(emailVal);

        configureDoneButton();

    }

    //***DONE BUTTON METHOD***
    private void configureDoneButton() {
        Button doneButton = (Button) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String whatWasClicked = "main";
                Intent intent = new Intent(MentorEdit.this, MentorList.class);
                intent.putExtra("INCOMINGINTENT", whatWasClicked);
                startActivity(intent);

                String nameEntry = mentorText.getText().toString();
                String phoneNumberEntry = phoneNumberText.getText().toString();
                String eMailEntry = eMailText.getText().toString();
                mDatabaseHelper.editMentorData(nameEntry, phoneNumberEntry, eMailEntry, mentorKeyString, mentorString);
                finish();
            }
        });
    }
}
