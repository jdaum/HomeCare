package com.example.whjt2_000.homecare;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ReviewDate extends AppCompatActivity {

    private int startYear, startMonth, startDay;
    private int endYear, endMonth, endDay;
    private TextView dateText;
    private String[] monthString = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
    String startDate, endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_date);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
 
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

        ArrayList<String> res = new ArrayList<String>();
        res = DatabaseHelper.getPatientInformationTimeBlock(startDate, endDate);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void showDate(){
        dateText.setText("Reivew information from " + startDate + " to " + endDate);
    }

    private String dateToString(int year, int month, int day){
        String date = "";
        if(day < 10) date += "0";
        date += Integer.toString(day);
        date += "-" + monthString[month];
        date += "-" + Integer.toString(year);
        return date;
    }
}