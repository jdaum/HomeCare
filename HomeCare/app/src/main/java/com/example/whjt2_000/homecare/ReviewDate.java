package com.example.whjt2_000.homecare;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ReviewDate extends AppCompatActivity {

    private int startYear, startMonth, startDay;
    private int endYear, endMonth, endDay;
    private TextView dateText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_date);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
 
        Bundle b = getIntent().getExtras();
        startYear = b.getInt("startYear");
        startMonth = b.getInt("startMonth")+1;
        startDay = b.getInt("startDay");

        endYear = b.getInt("endYear");
        endMonth = b.getInt("endMonth")+1;
        endDay = b.getInt("endDay");


        dateText = (TextView) findViewById(R.id.textView);
        showDate();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void showDate(){
        dateText.setText("Reivew information from " +  new StringBuilder().append(startDay).append("/").append(startMonth).append("/").append(startYear) +
                            "\nto " + new StringBuilder().append(endDay).append("/").append(endMonth).append("/").append(endYear));
    }
}