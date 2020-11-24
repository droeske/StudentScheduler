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
 * Mentor Add (Identical to Mentor Edit, different intent packing/unpacking)
 *
 * StudentScheduler: Designed at API level 26 using Oreo OS
 * @author David Roeske
 */

public class MentorAdd extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper; // Database
    EditText mentorText;
    EditText phoneNumberText;
    EditText eMailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_add);
        mDatabaseHelper = new DatabaseHelper(this);
        setTitle("Add Mentor");

        mentorText = (EditText) findViewById(R.id.mentorText);
        phoneNumberText = (EditText) findViewById(R.id.phoneNumberText);
        eMailText = (EditText) findViewById(R.id.eMailText);

        configureDoneButton();

    }

    //***DONE BUTTON METHOD***
    private void configureDoneButton() {
        Button doneButton = (Button) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String whatWasClicked = "main";
                Intent intent = new Intent(MentorAdd.this, MentorList.class);
                intent.putExtra("INCOMINGINTENT", whatWasClicked);
                startActivity(intent);

                String nameEntry = mentorText.getText().toString();
                String phoneNumberEntry = phoneNumberText.getText().toString();
                String eMailEntry = eMailText.getText().toString();
                int foreignKeyEntry = 0;
                long result = mDatabaseHelper.addMentorRecord("mentorName", nameEntry, "phoneNumber", phoneNumberEntry, "eMail", eMailEntry, "foreignKey", foreignKeyEntry);

                if (result == -1) {
                    Toast.makeText(MentorAdd.this, "Failed to add mentor", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MentorAdd.this, "Mentor created", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }
}
