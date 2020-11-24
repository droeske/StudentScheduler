package com.example.studentscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

/**
 * Calendar (Used to choose dates. If statements decide method calls form which activity opened it)
 *
 * StudentScheduler: Designed at API level 26 using Oreo OS
 * @author David Roeske
 */

public class Calendar extends AppCompatActivity {

    private CalendarView mCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        setTitle("Calendar");
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        String packetRetrieved;
        packetRetrieved = getIntent().getExtras().getString("INCOMINGINTENT");

        //***WHAT IS ACCESSING THE CALENDAR?***
        if (packetRetrieved.equals("termStart")){
            termStart();
        }
        if (packetRetrieved.equals("termEnd")) {
            termEnd();
        }
        if (packetRetrieved.equals("termStart_Edit")){
            termStartEdit();
        }
        if (packetRetrieved.equals("termEnd_Edit")) {
            termEndEdit();
        }
        if (packetRetrieved.equals("assessmentDate")) {
            assessmentDate();
        }
        if (packetRetrieved.equals("assessmentDate_Edit")) {
            assessmentDateEdit();
        }
        if (packetRetrieved.equals("courseStart")){
            courseStart();
        }
        if (packetRetrieved.equals("courseEnd")) {
            courseEnd();
        }
        if (packetRetrieved.equals("courseStart_Edit")){
            courseStartEdit();
        }
        if (packetRetrieved.equals("courseEnd_Edit")) {
            courseEndEdit();
        }
    }

    //***TERM START METHOD***
    public void termStart() {
        final String name;
        final String endDate;

        name = getIntent().getExtras().getString("NAME");
        endDate = getIntent().getExtras().getString("END");

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                String date = (month + 1) + "/" + dayOfMonth + "/" + year;
                String nameSend = name;
                String endSend = endDate;

                Intent intent = new Intent(Calendar.this, TermAdd.class);
                intent.putExtra("dateStart", date);
                intent.putExtra("name", nameSend);
                intent.putExtra("dateEnd", endSend);
                startActivity(intent);
                finish();
            }
        });
    }

    //***TERM END METHOD***
    public void termEnd() {
        final String name;
        final String startDate;

        name = getIntent().getExtras().getString("NAME");
        startDate = getIntent().getExtras().getString("START");

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                String date = (month + 1) + "/" + dayOfMonth + "/" + year;
                String nameSend = name;
                String startSend = startDate;

                Intent intent = new Intent(Calendar.this, TermAdd.class);
                intent.putExtra("dateEnd",date);
                intent.putExtra("name",nameSend);
                intent.putExtra("dateStart",startSend);
                startActivity(intent);
                finish();
            }
        });
    }

    //***TERM START EDIT METHOD***
    public void termStartEdit() {
        final String name;
        final String endDate;
        final int id;
        id = getIntent().getExtras().getInt("ID");
        name = getIntent().getExtras().getString("NAME");
        endDate = getIntent().getExtras().getString("END");

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                String date = (month + 1) + "/" + dayOfMonth + "/" + year;
                String nameSend = name;
                String endSend = endDate;
                int termid = id;
                Intent intent = new Intent(Calendar.this, TermEdit.class);
                intent.putExtra("ID",termid);
                intent.putExtra("dateStart", date);
                intent.putExtra("name", nameSend);
                intent.putExtra("dateEnd", endSend);
                startActivity(intent);
                finish();
            }
        });
    }

    //***TERM END EDIT METHOD***
    public void termEndEdit() {
        final String name;
        final String startDate;
        final int id;
        id = getIntent().getExtras().getInt("ID");
        name = getIntent().getExtras().getString("NAME");
        startDate = getIntent().getExtras().getString("START");

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                String date = (month + 1) + "/" + dayOfMonth + "/" + year;
                String nameSend = name;
                String startSend = startDate;
                int termid = id;
                Intent intent = new Intent(Calendar.this, TermEdit.class);
                intent.putExtra("ID",termid);
                intent.putExtra("dateEnd",date);
                intent.putExtra("name",nameSend);
                intent.putExtra("dateStart",startSend);
                startActivity(intent);
                finish();
            }
        });
    }



    //***ASSESSMENT DATE METHOD***
    public void assessmentDate() {
        final String name;

        name = getIntent().getExtras().getString("NAME");

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                String date = (month + 1) + "/" + dayOfMonth + "/" + year;
                String nameSend = name;

                Intent intent = new Intent(Calendar.this, AssessmentAdd.class);
                intent.putExtra("dueDate",date);
                intent.putExtra("assessmentText",nameSend);
                startActivity(intent);
                finish();
            }
        });
    }

    //***ASSESSMENT DATE EDIT METHOD***
    public void assessmentDateEdit() {
        final String name;
        final int id;
        id = getIntent().getExtras().getInt("ID");
        name = getIntent().getExtras().getString("NAME");

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                String date = (month + 1) + "/" + dayOfMonth + "/" + year;
                String nameSend = name;
                int assessmentid = id;
                Intent intent = new Intent(Calendar.this, AssessmentEdit.class);
                intent.putExtra("ID",assessmentid);
                intent.putExtra("duedate",date);
                intent.putExtra("name",nameSend);
                startActivity(intent);
                finish();
            }
        });
    }

    //***COURSE START METHOD***
    public void courseStart() {
        final String name;
        final String endDate;
        final String note;

        name = getIntent().getExtras().getString("NAME");
        endDate = getIntent().getExtras().getString("END");
        note = getIntent().getExtras().getString("NOTE");

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                String date = (month + 1) + "/" + dayOfMonth + "/" + year;
                String nameSend = name;
                String endSend = endDate;
                String noteSend = note;

                Intent intent = new Intent(Calendar.this, CourseAdd.class);
                intent.putExtra("dateStart", date);
                intent.putExtra("courseName", nameSend);
                intent.putExtra("dateEnd", endSend);
                intent.putExtra("note", noteSend);
                startActivity(intent);
                finish();
            }
        });
    }

    //***COURSE END METHOD***
    public void courseEnd() {
        final String name;
        final String startDate;
        final String note;

        name = getIntent().getExtras().getString("NAME");
        startDate = getIntent().getExtras().getString("START");
        note = getIntent().getExtras().getString("NOTE");

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                String date = (month + 1) + "/" + dayOfMonth + "/" + year;
                String nameSend = name;
                String startSend = startDate;
                String noteSend = note;

                Intent intent = new Intent(Calendar.this, CourseAdd.class);
                intent.putExtra("dateEnd",date);
                intent.putExtra("courseName",nameSend);
                intent.putExtra("dateStart",startSend);
                intent.putExtra("note", noteSend);
                startActivity(intent);
                finish();
            }
        });
    }

    //***COURSE START EDIT METHOD***
    public void courseStartEdit() {
        final String name;
        final String endDate;
        final String note;
        final int id;
        id = getIntent().getExtras().getInt("ID");
        name = getIntent().getExtras().getString("NAME");
        endDate = getIntent().getExtras().getString("END");
        note = getIntent().getExtras().getString("NOTE");

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                String date = (month + 1) + "/" + dayOfMonth + "/" + year;
                String nameSend = name;
                String endSend = endDate;
                String noteSend = note;
                int courseid = id;
                Intent intent = new Intent(Calendar.this, CourseEdit.class);
                intent.putExtra("ID",courseid);
                intent.putExtra("dateStart", date);
                intent.putExtra("courseName", nameSend);
                intent.putExtra("dateEnd", endSend);
                intent.putExtra("note", noteSend);
                startActivity(intent);
                finish();
            }
        });
    }

    //***COURSE END EDIT METHOD***
    public void courseEndEdit() {
        final String name;
        final String startDate;
        final String note;
        final int id;
        id = getIntent().getExtras().getInt("ID");
        name = getIntent().getExtras().getString("NAME");
        startDate = getIntent().getExtras().getString("START");
        note = getIntent().getExtras().getString("NOTE");

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                String date = (month + 1) + "/" + dayOfMonth + "/" + year;
                String nameSend = name;
                String startSend = startDate;
                String noteSend = note;
                int courseid = id;
                Intent intent = new Intent(Calendar.this, CourseEdit.class);
                intent.putExtra("ID",courseid);
                intent.putExtra("dateEnd",date);
                intent.putExtra("courseName",nameSend);
                intent.putExtra("dateStart",startSend);
                intent.putExtra("note", noteSend);
                startActivity(intent);
                finish();
            }
        });
    }
}
