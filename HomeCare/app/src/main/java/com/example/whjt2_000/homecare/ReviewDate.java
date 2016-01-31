package com.example.whjt2_000.homecare;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ReviewDate extends AppCompatActivity {

    private int year, month, day;
    private TextView dateText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_date);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Bundle b = getIntent().getExtras();
        year = b.getInt("year");
        month = b.getInt("month") + 1;
        day = b.getInt("day");

        dateText = (TextView) findViewById(R.id.textView);
        showDate();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void showDate(){
        dateText.setText("Reivew information on " + new StringBuilder().append(day).append("/").append(month).append("/").append(year));
    }
}