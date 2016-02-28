package com.example.whjt2_000.homecare;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
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
    int startYear, startMonth, startDay;
    boolean setStartDay, setEndDay;
    int endYear, endMonth, endDay;
    private TextView startText, endText, showStartText, showEndText;
    private DatePickerDialog startDateDialog;
    private DatePickerDialog endDateDialog;

    private SimpleDateFormat dateFormatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startText = (TextView) findViewById(R.id.StartText);
        endText = (TextView) findViewById(R.id.EndText);
        showStartText = (TextView) findViewById(R.id.showStartDate);
        showEndText = (TextView) findViewById(R.id.showEndDate);

        setEndDay = setStartDay = false;


        final Button startdate = (Button) findViewById(R.id.StartButton);
        Button enddate = (Button) findViewById(R.id.EndButton);

        startdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                startYear = c.get(Calendar.YEAR);
                startMonth = c.get(Calendar.MONTH);
                startDay = c.get(Calendar.DAY_OF_MONTH);

                //launch date picker

                startDateDialog = new DatePickerDialog(ReviewInformation.this,
                        new DatePickerDialog.OnDateSetListener(){
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                showStartText.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);
                                setStartDay=true;
                                startYear = year;
                                startMonth = monthOfYear;
                                startDay = dayOfMonth;
                            }
                        }, startYear, startMonth, startDay);
                startDateDialog.show();
            }
        });

        enddate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                endYear = c.get(Calendar.YEAR);
                endMonth = c.get(Calendar.MONTH);
                endDay = c.get(Calendar.DAY_OF_MONTH);

                //launch date picker

                endDateDialog = new DatePickerDialog(ReviewInformation.this,
                        new DatePickerDialog.OnDateSetListener(){
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                showEndText.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);
                                setEndDay=true;
                                endYear = year;
                                endMonth = monthOfYear;
                                endDay = dayOfMonth;
                            }
                        }, endYear, endMonth, endDay);
                endDateDialog.show();
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

    private boolean checkDate(){
        if (endYear < startYear)
            return false;
        if (endMonth < startMonth)
            return false;
        if (endDay < startDay)
            return false;
        return true;
    }

    public void enterInformation(View view){
        if(setStartDay && setEndDay && checkDate()){
            Intent intent = new Intent(this, ReviewDate.class);
            intent.putExtra("startYear", startYear);
            intent.putExtra("startMonth", startMonth+1);
            intent.putExtra("startDay", startDay);

            intent.putExtra("endYear", endYear);
            intent.putExtra("endMonth", endMonth+1);
            intent.putExtra("endDay", endDay);


            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Please select valid start and end date.", Toast.LENGTH_SHORT).show();
        }
    }


}