package com.example.whjt2_000.homecare;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText login;
    private String name;
    private User username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //find the editText and the buttons
        login = (EditText) findViewById(R.id.login);
        username = (User) getApplicationContext();

        login.setText(username.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void enterInformation(View view){
        if (checkUserName()){
            Toast.makeText(this, "Please sign in with your name!", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(this, EnterInformation.class);
            startActivity(intent);
        }
    }

    public void searchInfo(View view){
        if (checkUserName()){
            Toast.makeText(this, "Please sign in with your name!", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(this, SearchOptions.class);
            startActivity(intent);
        }
    }

    public void stockAnswerSetUp(View view){
        if (checkUserName()){
            Toast.makeText(this, "Please sign in with your name!", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(this, StockAnswerSetUp.class);
            startActivity(intent);
        }
    }

    private boolean checkUserName(){
        name = login.getText().toString();
        if (name.matches("")) {
            return true;
        }
        //save the username
        username.setName(name);
        return false;
    }

    /* Used to disable the editText field, not in use right now
    public void disableEditText() {
        if (login.getText().length() > 2){
            login.setFocusable(false);
            login.setEnabled(false);
            login.setCursorVisible(false);
            login.setKeyListener(null);
            login.setBackgroundColor(Color.TRANSPARENT);
        }
    }*/
}
