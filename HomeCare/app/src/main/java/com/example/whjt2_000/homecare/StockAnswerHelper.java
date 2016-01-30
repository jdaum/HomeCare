package com.example.whjt2_000.homecare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by jeanette on 29.01.16.
 */
public final class StockAnswerHelper extends SQLiteOpenHelper{


    private static StockAnswerHelper dbHelper;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "StockAnswer.db";


    public static synchronized StockAnswerHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (dbHelper == null) {
            dbHelper = new StockAnswerHelper(context);
        }
        return dbHelper;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * make call to static method "getInstance()" instead.
     */
    private StockAnswerHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* Inner class that defines the table contents */
    public static abstract class DatabaseEntry implements BaseColumns {
        public static final String TABLE_NAME = "stockanswers";
        //public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_BODYSYSTEM = "bodysystem";
        public static final String COLUMN_NAME_STOCKANSWER = "stockanswer";
    }

    private static final String TEXT_TYPE = " TEXT";

    private static final String COMMA_SEP = ",";

    // set ID to autoincrement, COLUMN_NAME_ENTRY_ID not needed right now
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DatabaseEntry.TABLE_NAME + " (" +
                    DatabaseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    //DatabaseEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    DatabaseEntry.COLUMN_NAME_BODYSYSTEM + TEXT_TYPE + COMMA_SEP +
                    DatabaseEntry.COLUMN_NAME_STOCKANSWER + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DatabaseEntry.TABLE_NAME;



    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }


    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    // adds a new stock answer to the database
    public long addStockAnswer(String stockanswer){
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        //values.put(DatabaseEntry.COLUMN_NAME_ENTRY_ID, new Random().nextInt() + 1);
        values.put(DatabaseEntry.COLUMN_NAME_BODYSYSTEM, "Respiratory");
        values.put(DatabaseEntry.COLUMN_NAME_STOCKANSWER, stockanswer);

        long rowId;
        // insert book
        rowId = db.insert(DatabaseEntry.TABLE_NAME,null,values);

        // close database transaction
        db.close();

        return rowId;
    }

    public ArrayList<String> getAllStockAnswers() {
        ArrayList<String> stockanswers = new ArrayList();

        // select book query
        String query = "SELECT  * FROM " + StockAnswerHelper.DatabaseEntry.TABLE_NAME;

        // get reference of the BookDB database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        String tmp = "";
        if (cursor.moveToFirst()) {
            do {

                tmp = cursor.getString(3);
                Log.d("StockAnswerDatabaseTest", "getAllStockAnswers: " + tmp);
                // Add book to books
                stockanswers.add(tmp);
            } while (cursor.moveToNext());
        }
        return stockanswers;
    }
    

}
