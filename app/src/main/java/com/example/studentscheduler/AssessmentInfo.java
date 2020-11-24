package com.example.studentscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentscheduler.Classes.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import java.sql.Date;
import java.util.Date.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;


/**
 * Assessment Info (Displays all held information for chosen assessment)
 *
 * StudentScheduler: Designed at API level 26 using Oreo OS
 * @author David Roeske
 */

public class AssessmentInfo extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper; // Database
    private TextView assessmentName;
    private TextView typeText;
    private TextView dueDateText;
    public int assessment;
    public int assessmentKey;
    public String nameForDelete;
    String nameToSend;
    String typeToSend;
    String duedateToSend;
    int keyToSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_info);

        mDatabaseHelper = new DatabaseHelper(this);

        assessmentName = (TextView) findViewById(R.id.assessmentName);
        typeText = (TextView) findViewById(R.id.typeText);
        dueDateText = (TextView) findViewById(R.id.dueDateText);

        setTitle("Assessment Information");

        Bundle recdData = getIntent().getExtras();
        String nameVal = recdData.getString("NAME");
        String typeVal = recdData.getString("TYPE");
        String dueDateVal = recdData.getString("DUEDATE");
        int assessmentGotID = recdData.getInt("ID");
        int assessmentGotKey = recdData.getInt("KEY");

        nameForDelete = nameVal;
        assessment = assessmentGotID;
        nameToSend = nameVal;
        typeToSend = typeVal;
        duedateToSend = dueDateVal;
        keyToSend = assessmentGotKey;

        assessmentName.setText(nameVal);
        typeText.setText(typeVal);
        dueDateText.setText(dueDateVal);


        configureDeleteButton();
        configureEditButton();
        configureNotifyButton();
    }

    //***ASSESSMENT EDIT BUTTON METHOD***
    private void configureEditButton() {
        FloatingActionButton editButton = (FloatingActionButton) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AssessmentInfo.this, AssessmentEdit.class);
                intent.putExtra("ID", assessment);
                intent.putExtra("name", nameToSend);
                intent.putExtra("dueDate", duedateToSend);
                intent.putExtra("TYPE", typeToSend);
                intent.putExtra("KEY", assessmentKey);
                startActivity(intent);
                finish();
            }
        });
    }

    //***ASSESSMENT DELETE BUTTON METHOD***
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

    //***DELETE WINDOW CONFIRMATION***
    private AlertDialog AskOption()
    {
        AlertDialog deleteWindow = new AlertDialog.Builder(this)
                .setTitle("Assessment Delete")
                .setMessage("Are you sure you want to delete " + nameForDelete + "?")
                .setIcon(R.drawable.ic_delete)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(AssessmentInfo.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                        mDatabaseHelper.deleteRecord("DELETE FROM Assessments WHERE assessmentId = " + assessment);
                        String whatWasClicked = "main";
                        Intent intent = new Intent(AssessmentInfo.this, AssessmentList.class);
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

    //***ASSESSMENT NOTIFY BUTTON METHOD***
    private void configureNotifyButton() {
        FloatingActionButton notifyButton = (FloatingActionButton) findViewById(R.id.notifyButton);
        notifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog notifyWindowBox = AskNotifyOption();
                notifyWindowBox.show();
            }
        });
    }

    //***NOTIFY WINDOW CONFIRMATION***
    private AlertDialog AskNotifyOption()
    {
        AlertDialog notifyWindow = new AlertDialog.Builder(this)
                .setTitle("Assessment Notify")
                .setMessage("Would you like notifications for this assessment?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        String date = convertDateFormat(duedateToSend);

                        DateTimeFormatter df=DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate ld3 = LocalDate.parse(date, df);
                        Date date1 = java.sql.Date.valueOf(date);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date1);
                        Long mill = calendar.getTimeInMillis();

                        Intent intent1=new Intent(AssessmentInfo.this,MyAssessmentReceiver.class);
                        PendingIntent sender1= PendingIntent.getBroadcast(AssessmentInfo.this,0,intent1,0);
                        AlarmManager alarmManager1=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                        alarmManager1.set(AlarmManager.RTC_WAKEUP, mill, sender1);
                        Toast.makeText(AssessmentInfo.this, "Notification has been set", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        return notifyWindow;
    }

    //***METHOD TO CONVERT DATE FOR NOTIFICATIONS***
    private String convertDateFormat(String date) {
        StringBuilder buildDate = new StringBuilder();
        buildDate = buildDate.append(date.substring(6)).append("-").append(date.substring(0, 2)).append("-").append(date.substring(3, 5));
        return buildDate.toString();
    }

}
