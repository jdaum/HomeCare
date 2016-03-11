package com.example.whjt2_000.homecare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by jeanette on 28.01.16.
 */
public final class DatabaseHelper extends SQLiteOpenHelper{


    private static DatabaseHelper dbHelper;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PatientInformation.db";


    public static synchronized DatabaseHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (dbHelper == null) {
            dbHelper = new DatabaseHelper(context);
        }
        return dbHelper;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * make call to static method "getInstance()" instead.
     */
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* Inner class that defines the table contents */
    public static abstract class DatabaseEntry implements BaseColumns {
        public static final String TABLE_NAME = "narrativenotes";
        public static final String COLUMN_NAME_NURSE = "nursename";
        public static final String COLUMN_NAME_BODYSYSTEM = "bodysystem";
        public static final String COLUMN_NAME_STOCKANSWER = "stockanswer";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_TIME = "time";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String DATE_TYPE = " Datetime";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DatabaseEntry.TABLE_NAME + " (" +
                    DatabaseEntry._ID + " INTEGER PRIMARY KEY," +
                    DatabaseEntry.COLUMN_NAME_NURSE + TEXT_TYPE + COMMA_SEP +
                    DatabaseEntry.COLUMN_NAME_BODYSYSTEM + TEXT_TYPE + COMMA_SEP +
                    DatabaseEntry.COLUMN_NAME_STOCKANSWER + TEXT_TYPE + COMMA_SEP +
                    DatabaseEntry.COLUMN_NAME_DATE + DATE_TYPE + COMMA_SEP +
                    DatabaseEntry.COLUMN_NAME_TIME + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DatabaseEntry.TABLE_NAME;



    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }


    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public long addPatientInformation(String name, String bodysystem, String stockanswer){
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //get the current data
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String formattedDate = df.format(c.getTime());

        //split formattedDate into a date and a time
        String[] res = formattedDate.split(" ");
        String date = res[0];
        String time = res[1];

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DatabaseEntry.COLUMN_NAME_NURSE,name);
        values.put(DatabaseEntry.COLUMN_NAME_BODYSYSTEM, bodysystem);
        values.put(DatabaseEntry.COLUMN_NAME_STOCKANSWER, stockanswer);
        values.put(DatabaseEntry.COLUMN_NAME_DATE,date);
        values.put(DatabaseEntry.COLUMN_NAME_TIME,time);

        long rowId;

        // insert values
        rowId = db.insert(DatabaseEntry.TABLE_NAME,null,values);

        // close database transaction
        db.close();

        return rowId;
    }

    public static ArrayList<String> getPatientInformationTimeBlock(String start, String end) {
        ArrayList<String> patientinfo = new ArrayList();

        // select query
        String query = "SELECT  * FROM " + DatabaseHelper.DatabaseEntry.TABLE_NAME +
                " WHERE " +  DatabaseHelper.DatabaseEntry.COLUMN_NAME_DATE + " >= \'" + start + "\' AND " +
                DatabaseHelper.DatabaseEntry.COLUMN_NAME_DATE + " <= \'" + end + "\' ;";

        // get reference of the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        String tmp = "";
        if (cursor.moveToFirst()) {
            do {
                tmp = cursor.getString(1) + " | ";
                tmp = tmp + cursor.getString(2) + " : ";
                tmp += cursor.getString(3) + " ";
                tmp += cursor.getString(4) + " ";
                tmp += cursor.getString(5);
                // Add stockanswer to list
                patientinfo.add(tmp);
            } while (cursor.moveToNext());
        }

        return patientinfo;
    }

    public static ArrayList<String> getPatientInformationForBodySystem(String bodysystem) {
        ArrayList<String> patientInfo = new ArrayList();

        // select query
        String query = "SELECT  * FROM " + DatabaseHelper.DatabaseEntry.TABLE_NAME + " WHERE " + DatabaseEntry.COLUMN_NAME_BODYSYSTEM + " = '" + bodysystem + "' ;";

        // get reference of the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        String tmp = "";
        if (cursor.moveToFirst()) {
            do {
                tmp = cursor.getString(1) + " | ";
                tmp = tmp + cursor.getString(2) + " : ";
                tmp += cursor.getString(3) + " ";
                tmp += cursor.getString(4) + " ";
                tmp += cursor.getString(5);
                patientInfo.add(tmp);
            } while (cursor.moveToNext());
        }
        return patientInfo;
    }


    public static ArrayList<String> getPatientInformation4h() {
        ArrayList<String> patientInfo = new ArrayList();

        //get the current data
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String formattedDate = df.format(c.getTime());

        //split formattedDate into a date and a time
        String[] res = formattedDate.split(" ");
        String date = res[0];
        String time = res[1];

        String[] res_time = time.split(":");
        int hour = Integer.parseInt(res_time[0]) - 4;

        String final_time = Integer.toString(hour)+":"+res_time[1];
        Log.d("CHECK NEW TIME -4", hour + " and " + final_time);


        // select query
        String query = "SELECT  * FROM " + DatabaseHelper.DatabaseEntry.TABLE_NAME +
                " WHERE " + DatabaseEntry.COLUMN_NAME_TIME + " > '" + final_time + "' AND " +DatabaseEntry.COLUMN_NAME_DATE + " = '" +date +"' ;";

        // get reference of the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        String tmp = "";
        if (cursor.moveToFirst()) {
            do {
                tmp = cursor.getString(1) + " | ";
                tmp = tmp + cursor.getString(2) + " : ";
                tmp += cursor.getString(3) + " ";
                tmp += cursor.getString(4) + " ";
                tmp += cursor.getString(5);
                patientInfo.add(tmp);
            } while (cursor.moveToNext());
        }
        return patientInfo;
    }

    public static ArrayList<String> getPatientInformationForNurseName(String name) {
        ArrayList<String> patientInfo = new ArrayList();

        // select query
        String query = "SELECT  * FROM " + DatabaseHelper.DatabaseEntry.TABLE_NAME + " WHERE " + DatabaseEntry.COLUMN_NAME_NURSE + " = '" + name + "' ;";

        // get reference of the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        String tmp = "";
        if (cursor.moveToFirst()) {
            do {
                tmp = cursor.getString(1) + " | ";
                tmp = tmp + cursor.getString(2) + " : ";
                tmp += cursor.getString(3) + " ";
                tmp += cursor.getString(4) + " ";
                tmp += cursor.getString(5);
                patientInfo.add(tmp);
            } while (cursor.moveToNext());
        }
        return patientInfo;
    }

    public static ArrayList<String> getNurseNames(){
        ArrayList<String> names = new ArrayList<String>();

        String query = "SELECT " + DatabaseEntry.COLUMN_NAME_NURSE +" FROM " + DatabaseEntry.TABLE_NAME;

        // get reference of the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        String tmp = "";
        if (cursor.moveToFirst()) {
            do {
                tmp = cursor.getString(0);
                if (!names.contains(tmp)) names.add(tmp);
            } while (cursor.moveToNext());
        }
        return names;
    }

}
