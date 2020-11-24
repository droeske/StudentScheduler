package com.example.studentscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentscheduler.Classes.Course;
import com.example.studentscheduler.Classes.Assessment;
import com.example.studentscheduler.Classes.Mentor;
import com.example.studentscheduler.Classes.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Course Info (Displays all held information for chosen course)
 *
 * StudentScheduler: Designed at API level 26 using Oreo OS
 * @author David Roeske
 */

public class CourseInfo extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper; // Database
    private TextView courseName;
    private TextView startDate;
    private TextView endDate;
    private TextView statusText;
    private TextView noteText;
    public int course;
    public String nameForDelete;

    ListView myAssessmentList;
    ArrayList<String> Assessment_ListItem;
    ArrayAdapter Assessment_Adapter;
    private static ArrayList<Assessment> allAssessments;

    ListView myMentorList;
    ArrayList<String> Mentor_ListItem;
    ArrayAdapter Mentor_Adapter;
    private static ArrayList<Mentor> allMentors;

    String titleToSend;
    String sDateToSend;
    String eDateToSend;
    String statusToSend;
    String noteToSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info);
        mDatabaseHelper = new DatabaseHelper(this);
        setTitle("Course Information");

        myAssessmentList = findViewById(R.id.assessmentList);
        myMentorList = findViewById(R.id.mentorList);
        courseName = (TextView) findViewById(R.id.courseName);
        startDate = (TextView) findViewById(R.id.startDate);
        endDate = (TextView) findViewById(R.id.endDate);
        statusText = (TextView) findViewById(R.id.statusText);
        noteText = (TextView) findViewById(R.id.noteText);
        FloatingActionButton shareButton = (FloatingActionButton) findViewById(R.id.shareButton);

        //UNPACKING
        Bundle recdData = getIntent().getExtras();
        String nameVal = recdData.getString("TITLE");
        String startVal = recdData.getString("START");
        String endVal = recdData.getString("END");
        String statusVal = recdData.getString("STATUS");
        String noteVal = recdData.getString("NOTE");
        int courseGotID = recdData.getInt("ID");
        nameForDelete = nameVal;
        course = courseGotID;
        //SETTING UNPACKED VALUES
        courseName.setText(nameVal);
        startDate.setText(startVal);
        endDate.setText(endVal);
        statusText.setText(statusVal);
        noteText.setText(noteVal);
        titleToSend = nameVal;
        sDateToSend = startVal;
        eDateToSend = endVal;
        statusToSend = statusVal;
        noteToSend = noteVal;

        Assessment_ListItem = new ArrayList<>();
        Mentor_ListItem = new ArrayList<>();

        viewAssessmentWhereData();
        viewMentorWhereData();

        allAssessments = mDatabaseHelper.readAssessmentRecords("SELECT * FROM Assessments");
        allMentors = mDatabaseHelper.readMentorRecords("SELECT * FROM Mentors");

        myAssessmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String assessmentName = myAssessmentList.getItemAtPosition(i).toString();
                String assessmentType = allAssessments.get(i).getAssessmentType().toString();
                String dueDate = allAssessments.get(i).getDueDate().toString();
                int assessmentID = allAssessments.get(i).getAssessmentId();

                Intent assessmentInfoIntent = new Intent(CourseInfo.this, AssessmentInfo.class);
                assessmentInfoIntent.putExtra("ID", assessmentID);
                assessmentInfoIntent.putExtra("NAME", assessmentName);
                assessmentInfoIntent.putExtra("TYPE", assessmentType);
                assessmentInfoIntent.putExtra("DUEDATE", dueDate);
                startActivity(assessmentInfoIntent);
            }

        });
        myMentorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String mentorName = myMentorList.getItemAtPosition(i).toString();
                String phoneNumber = allMentors.get(i).getPhoneNumber().toString();
                String eMail = allMentors.get(i).geteMail().toString();
                int mentorID = allMentors.get(i).getMentorId();

                Intent mentorInfoIntent = new Intent(CourseInfo.this, MentorInfo.class);
                mentorInfoIntent.putExtra("ID", mentorID);
                mentorInfoIntent.putExtra("NAME", mentorName);
                mentorInfoIntent.putExtra("PHONENUMBER", phoneNumber);
                mentorInfoIntent.putExtra("EMAIL", eMail);
                startActivity(mentorInfoIntent);
                startActivity(mentorInfoIntent);
            }

        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });

        configureDeleteButton();
        configureAddAssessmentsButton();
        configureAddMentorsButton();
        configureEditButton();
        configureNotifyStartButton();
        configureNotifyEndButton();
    }

    //***COURSE EDIT BUTTON METHOD***
    private void configureEditButton() {
        FloatingActionButton editButton = (FloatingActionButton) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int courseId = course;
                String nameEntry = titleToSend;
                String startDateEntry = sDateToSend;
                String endDateEntry = eDateToSend;
                String noteEntry = noteToSend;

                Intent intent = new Intent(CourseInfo.this, CourseEdit.class);
                intent.putExtra("ID", courseId);
                intent.putExtra("courseName", nameEntry);
                intent.putExtra("dateStart", startDateEntry);
                intent.putExtra("dateEnd", endDateEntry);
                intent.putExtra("note", noteEntry);
                startActivity(intent);
                finish();
            }
        });
    }

    //***ADD ASSESSMENTS BUTTON METHOD***
    private void configureAddAssessmentsButton() {
            FloatingActionButton addAssessmentsButton = (FloatingActionButton) findViewById(R.id.addAssessmentsButton);
        addAssessmentsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    String whatWasClicked = "courseinfo";

                    Intent intent = new Intent(CourseInfo.this, AssessmentList.class);
                    intent.putExtra("NAMESENT", titleToSend);
                    intent.putExtra("STARTDATESENT", sDateToSend);
                    intent.putExtra("ENDDATESENT", eDateToSend);
                    intent.putExtra("STATUSSENT", statusToSend);
                    intent.putExtra("NOTESENT", noteToSend);
                    intent.putExtra("ID", course);
                    intent.putExtra("INCOMINGINTENT", whatWasClicked);
                    startActivity(intent);

                    finish();
                }
            });
    }

    //***ADD MENTOR BUTTON METHOD***
    private void configureAddMentorsButton() {
            FloatingActionButton addMentorsButton = (FloatingActionButton) findViewById(R.id.addMentorsButton);
        addMentorsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    String whatWasClicked = "courseinfo";

                    Intent intent = new Intent(CourseInfo.this, MentorList.class);
                    intent.putExtra("NAMESENT", titleToSend);
                    intent.putExtra("STARTDATESENT", sDateToSend);
                    intent.putExtra("ENDDATESENT", eDateToSend);
                    intent.putExtra("STATUSSENT", statusToSend);
                    intent.putExtra("NOTEDENT", noteToSend);
                    intent.putExtra("ID", course);
                    intent.putExtra("INCOMINGINTENT", whatWasClicked);
                    startActivity(intent);

                    finish();
                }
            });
        }

    //***VIEW ASSESSMENT WHERE DATA METHOD***
    private void viewAssessmentWhereData() {
        String key = Integer.toString(course);
        Cursor cursor = mDatabaseHelper.viewAssessmentWhereData(key);
            while (cursor.moveToNext()) {
                Assessment_ListItem.add(cursor.getString(1));
            }
            Assessment_Adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Assessment_ListItem);
            myAssessmentList.setAdapter(Assessment_Adapter);
    }

    //***VIEW MENTOR WHERE DATA METHOD***
    private void viewMentorWhereData() {
        String key = Integer.toString(course);
        Cursor cursor = mDatabaseHelper.viewMentorWhereData(key);
            while (cursor.moveToNext()) {
                Mentor_ListItem.add(cursor.getString(1));
            }
            Mentor_Adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Mentor_ListItem);
            myMentorList.setAdapter(Mentor_Adapter);
    }

    //***SEND EMAIL METHOD***
    private void sendMail() {
        String recipient = "";
        String subject = titleToSend + " note";
        String message = noteToSend;

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipient);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose email application"));
    }

    //***COURSE DELETE BUTTON METHOD***
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
                .setTitle("Course Delete")
                .setMessage("Are you sure you want to delete " + nameForDelete + "?")
                .setIcon(R.drawable.ic_delete)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(CourseInfo.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                        mDatabaseHelper.deleteRecord("DELETE FROM Courses WHERE courseId = " + course);
                        String whatWasClicked = "main";
                        Intent intent = new Intent(CourseInfo.this, CourseList.class);
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


    //***COURSE NOTIFY START BUTTON METHOD***
    private void configureNotifyStartButton() {
        FloatingActionButton notifyStartButton = (FloatingActionButton) findViewById(R.id.notifyStartButton);
        notifyStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog notifyWindowBox = AskNotifyStartOption();
                notifyWindowBox.show();
            }
        });
    }

    //***NOTIFY START WINDOW CONFIRMATION***
    private AlertDialog AskNotifyStartOption()
    {
        AlertDialog notifyStartWindow = new AlertDialog.Builder(this)
                .setTitle("Course Notify")
                .setMessage("Would you like to be notified when this course begins?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        String date = convertDateFormat(sDateToSend);

                        DateTimeFormatter df=DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate ld3 = LocalDate.parse(date, df);
                        Date date1 = java.sql.Date.valueOf(date);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date1);
                        Long mill = calendar.getTimeInMillis();

                        Intent intent1=new Intent(CourseInfo.this,MyStartReceiver.class);
                        PendingIntent sender1= PendingIntent.getBroadcast(CourseInfo.this,0,intent1,0);
                        AlarmManager alarmManager1=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                        alarmManager1.set(AlarmManager.RTC_WAKEUP, mill, sender1);
                        Toast.makeText(CourseInfo.this, "Notification has been set", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }


                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        return notifyStartWindow;
    }


    //***COURSE NOTIFY END BUTTON METHOD***
    private void configureNotifyEndButton() {
        FloatingActionButton notifyEndButton = (FloatingActionButton) findViewById(R.id.notifyEndButton);
        notifyEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog notifyWindowBox = AskNotifyEndOption();
                notifyWindowBox.show();
            }
        });
    }
    //***NOTIFY END WINDOW CONFIRMATION***
    private AlertDialog AskNotifyEndOption()
    {
        AlertDialog notifyEndWindow = new AlertDialog.Builder(this)
                .setTitle("Course Notify")
                .setMessage("Would you like to be notified when this course ends?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        String date = convertDateFormat(eDateToSend);

                        DateTimeFormatter df=DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate ld3 = LocalDate.parse(date, df);
                        Date date1 = java.sql.Date.valueOf(date);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date1);
                        Long mill = calendar.getTimeInMillis();

                        Intent intent1=new Intent(CourseInfo.this,MyEndReceiver.class);
                        PendingIntent sender1= PendingIntent.getBroadcast(CourseInfo.this,0,intent1,0);
                        AlarmManager alarmManager1=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                        alarmManager1.set(AlarmManager.RTC_WAKEUP, mill, sender1);
                        Toast.makeText(CourseInfo.this, "Notification has been set", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        return notifyEndWindow;
    }

    //***METHOD TO CONVERT DATE FOR NOTIFICATIONS***
    private String convertDateFormat(String date) {
        StringBuilder buildDate = new StringBuilder();
        buildDate = buildDate.append(date.substring(6)).append("-").append(date.substring(0, 2)).append("-").append(date.substring(3, 5));
        return buildDate.toString();
    }

}
