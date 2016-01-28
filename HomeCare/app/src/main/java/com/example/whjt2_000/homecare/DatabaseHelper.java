package com.example.whjt2_000.homecare;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.provider.ContactsContract;

/**
 * Created by jeanette on 28.01.16.
 */
public final class DatabaseHelper extends SQLiteOpenHelper{

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /* Inner class that defines the table contents */
    public static abstract class DatabaseEntry implements BaseColumns {
        public static final String TABLE_NAME = "narrativenotes";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_BODYSYSTEM = "bodysystem";
        public static final String COLUMN_NAME_STOCKANSWER = "stockanswer";
        public static final String COLUMN_NAME_NURSE = "nursename";
        public static final String COLUMN_NAME_DATE = "date";
    }

    private static final String TEXT_TYPE = " TEXT";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DatabaseEntry.TABLE_NAME + " (" +
                    DatabaseEntry._ID + " INTEGER PRIMARY KEY," +
                    DatabaseEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    DatabaseEntry.COLUMN_NAME_NURSE + TEXT_TYPE + COMMA_SEP +
                    DatabaseEntry.COLUMN_NAME_BODYSYSTEM + TEXT_TYPE + COMMA_SEP +
                    DatabaseEntry.COLUMN_NAME_STOCKANSWER + TEXT_TYPE + COMMA_SEP +
                    DatabaseEntry.COLUMN_NAME_DATE + TEXT_TYPE +
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


}
