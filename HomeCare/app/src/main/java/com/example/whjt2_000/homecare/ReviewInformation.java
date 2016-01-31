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

import java.util.Calendar;

public class ReviewInformation extends AppCompatActivity {
    DatePicker date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button reviewButton = (Button) findViewById(R.id.button2);
        reviewButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                enterInformation(v);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void enterInformation(View view){
        date = (DatePicker) findViewById(R.id.datePicker);

        Intent intent = new Intent(this, ReviewDate.class);
        intent.putExtra("year", date.getYear());
        intent.putExtra("month", date.getMonth());
        intent.putExtra("day", date.getDayOfMonth());
        startActivity(intent);
    }


}