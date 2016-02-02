package com.example.whjt2_000.homecare;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class EnterInformation extends AppCompatActivity {


    private static final int SPEECH_REQUEST_CODE = 0;

    private DatabaseHelper  dbHelper;
    private String spokenText;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_information2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //handle database
        dbHelper = DatabaseHelper.getInstance(EnterInformation.this);

        //handle spinner
        spinner = (Spinner) findViewById(R.id.bodysystemspinner);


        //set listener for the voice button
        ImageButton voice_btn = (ImageButton)findViewById(R.id.voice_button);
        voice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySpeechRecognizer();
            }
        });

    }

    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
    // Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    // This callback is invoked when the Speech Recognizer returns.
    // This is where you process the intent and extract the speech text from the intent.
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            spokenText = results.get(0);
            EditText notes = (EditText) this.findViewById(R.id.infoText);
            notes.setText(notes.getText() + spokenText + " ");

            //TODO: DEBUGGING ONLY
            Log.d("EnterInfoFromVoice",spokenText);

            //get the selected body system from the spinner
            String bodysystem = spinner.getSelectedItem().toString();

            //add the patient information to the database
            // TODO: change spokenText to notes.get.text()
            long rowId = dbHelper.addPatientInformation(bodysystem, spokenText);

            //TODO: DEBUGGING ONLY
            Log.d("EnterInformation","Databaserow: " +rowId);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void enterInformation(View view){
        // get the information from the edittext field
        EditText notes = (EditText) this.findViewById(R.id.infoText);
        String message = notes.getText().toString();

        //get the selected body system from the spinner
        String bodysystem = spinner.getSelectedItem().toString();

        //add the patient information to the database
        long rowId = dbHelper.addPatientInformation(bodysystem, message);

        //TODO: DEBUGGING ONLY
        Log.d("EnterInformation","Databaserow: " +rowId);

    }
}
