package com.example.whjt2_000.homecare;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class ReviewByBodySys extends AppCompatActivity {

    ArrayList<String> bodySysAns;
    StockAnswerHelper dbHelper;
    Spinner spinner;
    String bodySystem;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_by_body_sys);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //show the back button in the tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        spinner = (Spinner) findViewById(R.id.spinner);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bodySystem = spinner.getSelectedItem().toString();
                showReviewInfo(v);
            }
        });

    }

    private void showReviewInfo(View view){

        //generate list
        bodySysAns = new ArrayList<String>();
        bodySysAns = DatabaseHelper.getPatientInformationForBodySystem(bodySystem);

        textView = (TextView)findViewById(R.id.my_textview);

        String tmp =(String)textView.getText() + "\n";
        if (bodySysAns.size() == 0) {
            Toast.makeText(this, "There's no information about " + bodySystem, Toast.LENGTH_SHORT).show();
        }
        for (String s : bodySysAns){
            tmp += s + "\n";
        }

        textView.setText(tmp);
    }

}
