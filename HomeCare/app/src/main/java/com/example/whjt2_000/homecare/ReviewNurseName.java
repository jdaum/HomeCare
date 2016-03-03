package com.example.whjt2_000.homecare;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

public class ReviewNurseName extends AppCompatActivity {

    private ArrayList<String> names;
    private Spinner name_sp;
    private Spinner spinner;
    private String name;
    private TextView textView;
    private ArrayList<String> entries;

    private DatabaseHelper  dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_nurse_name);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = DatabaseHelper.getInstance(ReviewNurseName.this);

        String curNurseName =((User) getApplicationContext()).getName();
        names = dbHelper.getNurseNames();
        if(!names.contains(curNurseName))
            names.add(curNurseName);

        name_sp = (Spinner) findViewById(R.id.name_spinner);
        ArrayAdapter<String> name_adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_row, names);
        //name_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        name_sp.setAdapter(name_adapter);
        //name_sp.setSelection(0);
        //name_sp.setOnItemSelectedListener(new select_name());

        textView = (TextView)findViewById(R.id.my_textview);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                textView.setText("");
                if(name_sp.getSelectedItem() == null){
                    Toast.makeText(ReviewNurseName.this, "There's no information.", Toast.LENGTH_SHORT).show();
                } else {
                    name = name_sp.getSelectedItem().toString();
                    showReviewInfo(v);
                }
            }
        });

    }

    private void showReviewInfo(View view){

        //generate list
        entries = dbHelper.getPatientInformationForNurseName(name);

        Log.d("ENTRIES", "showReviewInfo: " + entries.size());
        String tmp =(String)textView.getText() + "\n";
        if (entries.size() == 0) {
            Toast.makeText(this, "There's no information from " + name + ".", Toast.LENGTH_SHORT).show();
        }
        for (String s : entries){
            tmp += s + "\n";
        }

        textView.setText(tmp);
    }

}
