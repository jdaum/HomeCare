package com.example.whjt2_000.homecare;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class SearchOptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_options);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void reviewInformation(View view){
            Intent intent = new Intent(this, ReviewInformation.class);
            startActivity(intent);
    }

    public void reviewInfoByBodySys(View view){
            Intent intent = new Intent(this, ReviewByBodySys.class);
            startActivity(intent);
    }

    public void review4h(View view){
            Intent intent = new Intent(this, Review4h.class);
            startActivity(intent);
    }

    public void reviewNurseName(View view){
        Intent intent = new Intent(this, ReviewNurseName.class);
        startActivity(intent);
    }

}
