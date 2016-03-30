package com.example.ivansv.softomatetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "database";
    public static final String DATABASE_TABLE = "simpletable";
    public static final String KEY_ROW_ID = "_id";
    public static final String KEY_TEXT = "text";
    public static final String KEY_LANGUAGE = "language";
    private static final String DATABASE_CREATE_TABLE =
            "create table " + DATABASE_TABLE + " ("
                    + KEY_ROW_ID + " integer primary key autoincrement, "
                    + KEY_TEXT + " string , "
                    + KEY_LANGUAGE + " string );";

    private Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }
}
