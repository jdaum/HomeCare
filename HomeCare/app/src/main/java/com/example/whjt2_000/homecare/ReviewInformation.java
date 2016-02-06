package com.example.whjt2_000.homecare;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.DateTimeKeyListener;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReviewInformation extends AppCompatActivity {
    CalendarView date;
    int startYear;
    int startMonth;
    int startDay;
    boolean setStartDay, setEndDay;
    int endYear;
    int endMonth;
    int endDay;

    String endDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setEndDay = setStartDay = false;

        Button startButton = (Button) findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setStartDate(v);
            }
        });
        Button endButton = (Button) findViewById(R.id.EndButton);
        endButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                setEndDate(v);
            }
        });

        Button reviewButton = (Button) findViewById(R.id.ReviewButton);
        reviewButton.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v){
                enterInformation(v);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setStartDate(View view){

        DatePicker d = (DatePicker) findViewById(R.id.calendarView);
        startYear = d.getYear();
        startMonth = d.getMonth();
        startDay = d.getDayOfMonth();
        setStartDay = true;
    }

    public void setEndDate(View view){
        DatePicker d = (DatePicker) findViewById(R.id.calendarView);
        endYear = d.getYear();
        endMonth = d.getMonth();
        endDay = d.getDayOfMonth();
        if (setStartDay && !checkDate()) {
            Toast.makeText(this, "Please select an end date after" +
                            new StringBuilder().append(startDay).append("/").append(startMonth).append("/").append(startYear),
                    Toast.LENGTH_SHORT).show();
        } else
            setEndDay = true;
    }

    private boolean checkDate(){
        if (endYear < startYear)
            return false;
        else if(endYear > startYear)
            return true;
        if (endMonth < startMonth) return false;
        else if(endMonth > startMonth) return true;

        if (endDay < startDay) return false;
        else return true;
    }

    public void enterInformation(View view){
        if(!setStartDay || !setEndDay)
            Toast.makeText(this, "Please select valid start and end date", Toast.LENGTH_SHORT).show();
        else {

            Intent intent = new Intent(this, ReviewDate.class);
            intent.putExtra("startYear", startYear);
            intent.putExtra("startMonth", startMonth);
            intent.putExtra("startDay", startDay);

            intent.putExtra("endYear", endYear);
            intent.putExtra("endMonth", endMonth);
            intent.putExtra("endDay", endDay);


            startActivity(intent);
        }
    }


}