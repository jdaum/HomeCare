package com.example.whjt2_000.homecare;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReviewDate extends AppCompatActivity {

    private int startYear, startMonth, startDay;
    private int endYear, endMonth, endDay;
    private TextView dateText;
    private DatabaseHelper  dbHelper;
    private String startDate, endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_date);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
 
        Bundle b = getIntent().getExtras();
        startYear = b.getInt("startYear");
        startMonth = b.getInt("startMonth");
        startDay = b.getInt("startDay");
        startDate = dateToString(startYear, startMonth, startDay);

        endYear = b.getInt("endYear");
        endMonth = b.getInt("endMonth");
        endDay = b.getInt("endDay");
        endDate = dateToString(endYear, endMonth, endDay);

        dateText = (TextView) findViewById(R.id.textView);
        showDate();

        dbHelper = DatabaseHelper.getInstance(ReviewDate.this);
        ArrayList<String> patientresults = dbHelper.getPatientInformationTimeBlock(startDate, endDate);

        String tmp =(String)dateText.getText() + "\n";
        if (patientresults.size() == 0) {
            Toast.makeText(this, "No information saved for this time.",
                    Toast.LENGTH_SHORT).show();}
        for (String s : patientresults){
           tmp += s + "\n";
        }

        dateText.setText(tmp);
    }

    private void showDate(){
        dateText.setText("Review information from " + startDate + " to " + endDate + "\n");
    }

    private String dateToString(int year, int month, int day){
        String date = "";
        if(day < 10) date += "0";
        date += Integer.toString(day);
        if (month < 10) {date += "-0" + Integer.toString(month);}
        else {date += "-"+Integer.toString(month);}
        date += "-" + Integer.toString(year);
        return date;
    }
}