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
    TextView startText, endText;
    private DatePickerDialog startDateDialog;
    private DatePickerDialog endDateDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startText = (TextView) findViewById(R.id.StartText);
        endText = (TextView) findViewById(R.id.EndText);

        setStartDateDialog();
        setEndDateDialog();

        setEndDay = setStartDay = false;

        Button startButton = (Button) findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startDateDialog.show();
                startDateDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            Calendar date = Calendar.getInstance();
//                            startYear = date.get(Calendar.YEAR);
//                            startMonth = date.get(Calendar.MONTH) + 1;
//                            startDay = date.get(Calendar.DAY_OF_MONTH);
//                            setStartDay = true;
                            setStartDate();
                        }
                    }
                });
            }
        });



        Button endButton = (Button) findViewById(R.id.EndButton);
        endButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                endDateDialog.show();
                endDateDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                                Calendar date = Calendar.getInstance();
                                endYear = date.get(Calendar.YEAR);
                                endMonth = date.get(Calendar.MONTH) + 1;
                                endDay = date.get(Calendar.DAY_OF_MONTH);
                                setEndDay = true;
                            setEndDate();
                        }
                    }
                });
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

    private void setStartDateDialog(){
        Calendar c = Calendar.getInstance();
        startDateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar date = Calendar.getInstance();
                date.set(year, monthOfYear, dayOfMonth);
                startYear = view.getYear();
                startMonth = view.getMonth();
                startDay = view.getDayOfMonth();
                setStartDay = true;
//                startYear = date.get(Calendar.YEAR);
//                startMonth =  date.get(Calendar.MONTH) + 1;
//                startDay = date.get(Calendar.DAY_OF_MONTH);
//                setStartDay = true;
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
    }

    private void setEndDateDialog(){
        Calendar c = Calendar.getInstance();
        endDateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar date = Calendar.getInstance();
                date.set(year, monthOfYear, dayOfMonth);
//                endYear = date.get(Calendar.YEAR);
//                endMonth =  date.get(Calendar.MONTH) + 1;
//                endDay = date.get(Calendar.DAY_OF_MONTH);
//                setEndDay = true;
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
    }

    public void setStartDate(){

        if(setEndDay && !checkDate()){
            Toast.makeText(this, "Please select an end date before " +
                            new StringBuilder().append(endDay).append("/").append(endMonth).append("/").append(endYear).append("."),
                    Toast.LENGTH_SHORT).show();
        }
        else {
            setStartDay = true;
            Toast.makeText(this, "Selected " + new StringBuilder().append(startDay).append("/").append(startMonth).append("/").append(startYear) + " as start date.",
                    Toast.LENGTH_SHORT).show();
        }
        startText.setText(startText.getText() + "\n" + new StringBuilder().append(startDay).append("/").append(startMonth).append("/").append(startYear));
    }

    public void setEndDate(){
        if (setStartDay && !checkDate()) {
            Toast.makeText(this, "Please select an end date after " +
                            new StringBuilder().append(startDay).append("/").append(startMonth).append("/").append(startYear).append("."),
                    Toast.LENGTH_SHORT).show();
        } else {
            setEndDay = true;
            Toast.makeText(this, "Selected " + new StringBuilder().append(endDay).append("/").append(endMonth).append("/").append(endYear) + " as end date.",
                    Toast.LENGTH_SHORT).show();
        }
        endText.setText(endText.getText() + "\n" + new StringBuilder().append(endDay).append("/").append(endMonth).append("/").append(endYear));
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
            Toast.makeText(this, "Please select valid start and end date.", Toast.LENGTH_SHORT).show();
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