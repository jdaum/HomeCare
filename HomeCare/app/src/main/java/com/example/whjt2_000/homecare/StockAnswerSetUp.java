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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class StockAnswerSetUp extends AppCompatActivity {

    ArrayList<String> list;
    MyCustomAdapter adapter;
    StockAnswerHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //show the back button in the tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //retrieve the database
        dbHelper = StockAnswerHelper.getInstance(StockAnswerSetUp.this);

        //generate list
        list = new ArrayList<String>();
        list = dbHelper.getAllStockAnswers();


        //read out stock answers here and add them to the list

        Button add_btn= (Button)findViewById(R.id.add_btn);

        //instantiate custom adapter
        adapter = new MyCustomAdapter(list, this);

        //handle listview and assign adapter
        ListView lView = (ListView)findViewById(R.id.my_listview);
        lView.setAdapter(adapter);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get the new stock answer
                EditText editText = (EditText) findViewById(R.id.new_stock_answer);
                String message = editText.getText().toString();

                long rowId = dbHelper.addStockAnswer(message);


                Log.d("StockAnswerSetUp", "onClick:" + rowId);

                // this line adds the data of your EditText and puts in your array
                list.add(message);

                SQLiteDatabase db2 = dbHelper.getWritableDatabase();
                dbHelper.onUpgrade(db2,1,2);
                // next thing you have to do is check if your adapter has changed
                adapter.notifyDataSetChanged();
            }
        });


    }

}
