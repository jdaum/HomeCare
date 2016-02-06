package com.example.whjt2_000.homecare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EnterInformation extends AppCompatActivity {


    private static final int SPEECH_REQUEST_CODE = 0;

    private DatabaseHelper  dbHelper;
    private StockAnswerHelper saHelper;
    private String spokenText;
    private Spinner spinner;
    private MyCustomCheckboxAdapter dataAdapter = null;
    private String name;
    private ArrayList<String> selected = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_information2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //handle username
        name = ((User) getApplicationContext()).getName();

        //handle databases
        dbHelper = DatabaseHelper.getInstance(EnterInformation.this);
        saHelper = StockAnswerHelper.getInstance(EnterInformation.this);

        //handle spinner
        spinner = (Spinner) findViewById(R.id.bodysystemspinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                displayListView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(EnterInformation.this, "Please select a body system category!", Toast.LENGTH_SHORT).show();
            }

        });

        //set listener for the voice button
        ImageButton voice_btn = (ImageButton)findViewById(R.id.voice_button);
        voice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySpeechRecognizer();
            }
        });

        //Generate list View from ArrayList
        displayListView();
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

            /*//get the selected body system from the spinner
            String bodysystem = spinner.getSelectedItem().toString();

            //add the patient information to the database
            // TODO: change spokenText to notes.get.text()
            long rowId = dbHelper.addPatientInformation(name,bodysystem, notes.getText().toString());

            //TODO: DEBUGGING ONLY
            Log.d("EnterInformation","Databaserow: " +rowId);*/
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
        long rowId = dbHelper.addPatientInformation(name, bodysystem, message);
        Log.d("Entry", name + " " + bodysystem + " " + message);
        for (String s: selected){
            //divide the stock answer
            String split[] = s.split(":");
            String bs = split[0];
            String sa = split[1].substring(1);
            //sanity check before inserting stock answer into patient database
            if (bs.equals(bodysystem)){
                dbHelper.addPatientInformation(name, bs, sa);
            }
        }
        //TODO: DEBUGGING ONLY
        Log.d("EnterInformation", "Databaserow: " + rowId);
    }

    private void displayListView() {

        //Array list of stock answers for selected bodysystem
        ArrayList<String> stockanswers = saHelper.getStockAnswersForBodySystem(spinner.getSelectedItem().toString());


        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomCheckboxAdapter(this,
                R.layout.custom_checkboxlist, stockanswers);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                String stockanswer = (String) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + stockanswer,
                        Toast.LENGTH_LONG).show();
            }
        });

    }


    private class MyCustomCheckboxAdapter extends ArrayAdapter {

        private ArrayList<String> stockanswers;

        public MyCustomCheckboxAdapter(Context context, int textViewResourceId,
                                       ArrayList<String> stockanswers) {
            super(context, textViewResourceId, stockanswers);
            this.stockanswers = new ArrayList<String>();
            this.stockanswers.addAll(stockanswers);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            //Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.custom_checkboxlist, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        String sa = cb.getText().toString();
                        if (cb.isChecked()) {
                            selected.add(sa);
                        }
                        else {
                            selected.remove(sa);
                        }
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            String stockanswer = stockanswers.get(position);
            //TODO: currently more like a hack, check maybe later for a better implementation
            holder.code.setText("");
            holder.name.setText(stockanswer);
            return convertView;

        }
    }

}
