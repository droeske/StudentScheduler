package com.example.studentscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.studentscheduler.Classes.DatabaseHelper;
import com.example.studentscheduler.Classes.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * Term List (Used to list all terms, FAB add button to add terms)
 *
 * StudentScheduler: Designed at API level 26 using Oreo OS
 * @author David Roeske
 */

public class TermList extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    FloatingActionButton addButton;

    ListView myTermList;
    ArrayList<String> listItem;
    ArrayAdapter adapter;
    private static ArrayList<Term> allTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        setTitle("Terms");
        addButton = (FloatingActionButton) findViewById(R.id.addButton);
        myTermList = findViewById(R.id.myTermList);

        mDatabaseHelper = new DatabaseHelper(this);

        listItem = new ArrayList<>();

        viewTermData();
        allTerms = mDatabaseHelper.readTermRecords("SELECT * FROM Terms");

        myTermList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String termName = myTermList.getItemAtPosition(i).toString();
                String startDate = allTerms.get(i).getStartDate().toString();
                String endDate = allTerms.get(i).getEndDate().toString();
                int termID = allTerms.get(i).getTermId();

                Intent termInfoIntent = new Intent(TermList.this, TermInfo.class);
                termInfoIntent.putExtra("ID", termID);
                termInfoIntent.putExtra("NAME", termName);
                termInfoIntent.putExtra("START", startDate);
                termInfoIntent.putExtra("END", endDate);
                startActivity(termInfoIntent);
                finish();
            }
        });



        configureAddButton();
    }

    //***ADD BUTTON***
    private void configureAddButton() {
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TermList.this, TermAdd.class));
                finish();
            }
        });
    }

    //***VIEW TERM DATA METHOD***
    private void viewTermData() {
        Cursor cursor = mDatabaseHelper.viewTermData();
        while (cursor.moveToNext()) {
            listItem.add(cursor.getString(1));
        }
        adapter = new ArrayAdapter<>(this, com.example.studentscheduler.R.layout.mycustomlayout, listItem);
        myTermList.setAdapter(adapter);
    }
}