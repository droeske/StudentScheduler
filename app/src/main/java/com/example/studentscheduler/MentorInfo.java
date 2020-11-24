package com.example.studentscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentscheduler.Classes.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Mentor Info (Displays all held information for chosen mentor)
 *
 * StudentScheduler: Designed at API level 26 using Oreo OS
 * @author David Roeske
 */

public class MentorInfo extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper; // Database
    private TextView mentorName;
    private TextView phoneNumberText;
    private TextView eMailText;
    public int mentor;
    public int mentorKey;
    public String nameForDelete;

    String nameToSend;
    String eMailToSend;
    String phoneNumberToSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_info);
        mDatabaseHelper = new DatabaseHelper(this);

        mentorName = (TextView) findViewById(R.id.mentorName);
        phoneNumberText = (TextView) findViewById(R.id.phoneNumberText);
        eMailText = (TextView) findViewById(R.id.eMailText);

        setTitle("Mentor Information");

        Bundle recdData = getIntent().getExtras();
        String nameVal = recdData.getString("NAME");
        String phonenumberVal = recdData.getString("PHONENUMBER");
        String emailVal = recdData.getString("EMAIL");
        int mentorGotID = recdData.getInt("ID");
        int mentorGotKey = recdData.getInt("KEY");

        nameToSend = nameVal;
        phoneNumberToSend = phonenumberVal;
        eMailToSend = emailVal;
        nameForDelete = nameVal;
        nameForDelete = nameVal;
        mentor = mentorGotID;
        mentorKey = mentorGotKey;
        mentorName.setText(nameVal);
        phoneNumberText.setText(phonenumberVal);
        eMailText.setText(emailVal);

        configureDeleteButton();
        configureEditButton();
    }

    //***MENTOR DELETE BUTTON METHOD***
    private void configureDeleteButton() {
        FloatingActionButton deleteButton = (FloatingActionButton) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog deleteWindowBox = AskOption();
                deleteWindowBox.show();
            }
        });
    }

    //***MENTOR EDIT BUTTON METHOD***
    private void configureEditButton() {
        FloatingActionButton editButton = (FloatingActionButton) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MentorInfo.this, MentorEdit.class);
                intent.putExtra("ID", mentor);
                intent.putExtra("NAME", nameToSend);
                intent.putExtra("PHONENUMBER", phoneNumberToSend);
                intent.putExtra("EMAIL", eMailToSend);
                intent.putExtra("KEY", mentorKey);
                startActivity(intent);
                finish();
            }
        });
    }

    //***DELETE WINDOW CONFIRMATION***
    private AlertDialog AskOption()
    {
        AlertDialog deleteWindow = new AlertDialog.Builder(this)
                .setTitle("Mentor Delete")
                .setMessage("Are you sure you want to delete " + nameForDelete + "?")
                .setIcon(R.drawable.ic_delete)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(MentorInfo.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                        mDatabaseHelper.deleteRecord("DELETE FROM Mentors WHERE mentorId = " + mentor);
                        String whatWasClicked = "main";
                        Intent intent = new Intent(MentorInfo.this, MentorList.class);
                        intent.putExtra("INCOMINGINTENT", whatWasClicked);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        return deleteWindow;
    }
}
