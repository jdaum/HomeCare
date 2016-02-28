package com.example.whjt2_000.homecare;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Review4h extends AppCompatActivity {

    private TextView textView;
    private DatabaseHelper dbHelper;
    private ArrayList<String> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review4h);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView = (TextView)findViewById(R.id.my_textview);
        dbHelper = DatabaseHelper.getInstance(Review4h.this);

        showNotes();
    }

    public void showNotes(){
        //generate list
        notes = dbHelper.getPatientInformation4h();

        String tmp =(String)textView.getText() + "\n";
        if (notes.size() == 0) {
            Toast.makeText(this, "There's no information saved about the last 4 hours.", Toast.LENGTH_SHORT).show();
        }
        for (String s : notes){
            tmp += s + "\n";
        }

        textView.setText(tmp);
    }

}
